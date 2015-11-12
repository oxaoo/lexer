package com.github.oxaoo.lexer.syntax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dydus on 13/11/2015.
 */
class GrammarParser {
    public static final String TER_PATH = "terminals";
    public static final String NOTTER_PATH = "notterminals";
    public static final String RULES_PATH = "rules";
    public static final String CONV_PATH = "convolution";
    public static final String TRANS_PATH = "transfer";

    public Grammar parse(String path) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        String line;

        Grammar result = new Grammar();

        try {
            // Terminals
            br = new BufferedReader(new FileReader(path + TER_PATH));
            String[] terms = br.readLine().split(",");
            for (String t : terms) {
                result.terminals.add(new Terminal(t));
            }
            br.close();


            // NotTerminals
            br = new BufferedReader(new FileReader(path + NOTTER_PATH));
            String[] notTerms = br.readLine().split(",");
            for (String t : notTerms) {
                result.notTerminals.add(new NotTerminal(t));
            }
            result.S = new NotTerminal(br.readLine());
            br.close();


            // Rules
            br = new BufferedReader(new FileReader(path + RULES_PATH));
            while ((line = br.readLine()) != null) {
                String[] partsOfRule = line.split("->");
                Rule rule = new Rule();
                rule.left = new NotTerminal(partsOfRule[0].trim());
                String[] right = partsOfRule[1].split(" ");
                for (String t : right) {
                    t = t.trim();
                    if (t != "") {
                        rule.right.add(new Symbol(t));
                    }
                }
                result.rules.add(rule);
            }


            // Convolution
            br = new BufferedReader(new FileReader(path + CONV_PATH));
            while ((line = br.readLine()) != null) {
                String[] partsOfConv = line.split("|");
                if (partsOfConv.length != 2) {
                    continue;
                }

                Convolution key = new Convolution();
                int value = 0;
                result.convolutionTable.put(key, value);
            }


            // Transfer
            br = new BufferedReader(new FileReader(path + CONV_PATH));
            List<String> column = new ArrayList<String>(Arrays.asList(br.readLine().split("|")));
            column.remove(0);
            for (int i = 0; i < column.size(); i++) {
                column.add(i, column.get(i).trim());
            }

            while ((line = br.readLine()) != null) {
                String[] partsOfTrans = line.split("|");
                if (partsOfTrans.length <= 2) {
                    continue;
                }

                List<Symbol> symbols = new ArrayList<Symbol>();
                List<String> row = new ArrayList<String>(Arrays.asList(br.readLine().split("|")));
                Pattern pattern = Pattern.compile("[(.*?)]");
                Matcher matcher = pattern.matcher(row.get(0).trim());
                String[] strRows = matcher.group(1).split(",");
                row.remove(0);
                for (String strRow : strRows) {
                    symbols.add(new Symbol(strRow));
                }

                for (int i = 0; i < column.size(); i++) {
                    Transfer key = new Transfer();
                    TransferType value = TransferType.parse(row.get(i));

                    key.row = symbols;
                    key.column = new Terminal(column.get(i));

                    result.transferTable.put(key, value);
                }
            }
        } catch(FileNotFoundException e){}
        catch (Exception e){
            System.err.println("Exception while reading file: [" + e.toString() + "]");
        }

        return result;
    }
}
