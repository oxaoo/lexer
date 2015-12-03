package com.github.oxaoo.lexer.syntax;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
                result.terminals.add(new Terminal(t.trim()));
            }
            while ((line = br.readLine()) != null) {
                String[] nextGrammar = line.split(",");
                try {
                    result.nextGrammars.put(new Symbol(nextGrammar[0].trim()), Integer.parseInt(nextGrammar[1].trim()));
                } catch (Exception e) {
                    System.err.print("Error during grammar parsing");
                }
            }
            br.close();


            // NotTerminals
            br = new BufferedReader(new FileReader(path + NOTTER_PATH));
            String[] notTerms = br.readLine().split(",");
            for (String t : notTerms) {
                result.notTerminals.add(new NotTerminal(t.trim()));
            }
            result.S = new NotTerminal(br.readLine().trim());
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
            br.close();

            // Convolution
            br = new BufferedReader(new FileReader(path + CONV_PATH));
            while ((line = br.readLine()) != null) {
                String[] partsOfConv = line.split("\\|");
                if (partsOfConv.length < 2) {
                    continue;
                }

                Convolution key = new Convolution();
                String left = partsOfConv[0].trim();

                String prefix = left.split(" ")[0];
                key.prefix = new Symbol(prefix.trim());

                String[] strRows = left.replaceFirst(prefix, "").replaceAll("\\[", "").replaceAll("\\]", "").trim().split(",");
                for (String strRow : strRows) {
                    key.convolution.add(new Symbol(strRow.trim()));
                }

                int value = Integer.parseInt(partsOfConv[1].split(",")[0].trim());
                result.sortedConvolution.add(key);
                result.convolutionValue.add(value);
            }
            br.close();

            // Transfer
            br = new BufferedReader(new FileReader(path + TRANS_PATH));
            List<String> column = new ArrayList<String>(Arrays.asList(br.readLine().split("\\|")));
            column.remove(0);
            for (int i = 0; i < column.size(); i++) {
                column.set(i, column.get(i).trim());
            }

            while ((line = br.readLine()) != null) {
                String[] partsOfTrans = line.split("\\|");
                if (partsOfTrans.length <= 2) {
                    continue;
                }
                boolean isSkipNonterm = false;
                List<Symbol> symbols = new ArrayList<Symbol>();
                List<String> row = new ArrayList<String>(Arrays.asList(partsOfTrans));
                String[] strRows = row.get(0).replaceAll("\\[", "").replaceAll("\\]", "").trim().split(",");
                row.remove(0);
                for (String strRow : strRows) {
                    strRow = strRow.trim();
                    if (!strRow.equals("C"))
                        symbols.add(new Symbol(strRow));
                    else {
                        isSkipNonterm |= true;
                    }
                }

                for (int i = 0; i < column.size(); i++) {
                    Transfer key = new Transfer();
                    TransferType value = TransferType.parse(row.get(i).trim());

                    key.isSkipNonterm = isSkipNonterm;
                    key.row = symbols;
                    key.column = new Terminal(column.get(i));
                    result.sortedTransfer.add(key);
                    result.transferValue.add(value);
                }
            }
            br.close();
        } catch(FileNotFoundException e){
            System.err.println("Exception while reading file: [" + e.toString() + "]");
        } catch (Exception e){
            e.printStackTrace();
            System.err.println("Exception while reading file: [" + e.toString() + "]");
        }

        return result;
    }
}
