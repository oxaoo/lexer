package com.github.oxaoo.lexer;

import com.github.oxaoo.codegen.CCodeGen;
import com.github.oxaoo.codegen.CTetrad;
import com.github.oxaoo.codegen.CTetradSimple;
import com.github.oxaoo.lexer.syntax.Grammar;
import com.github.oxaoo.lexer.syntax.SyntaxAnalizer;
import com.github.oxaoo.lexer.syntax.Terminal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.oxaoo.parser.CSyntaxTree;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import javax.swing.*;


public class Main {

    public static final String mResCode = "res/code2";

    public static void main(String[] args) throws FileNotFoundException {
        startLexer(args);
        CSyntaxTreeNode root = startParser();
        CTetrad tetrad = startCodeGen(root);

        //tetradSimple();
    }

    private static void tetradSimple() {
        CTetradSimple ts = new CTetradSimple();
        List<CTetrad> tetrads = ts.getTetrads();
        System.out.println("TETRADS SIMPLE: " + tetrads.toString());
    }

    private static CTetrad startCodeGen(CSyntaxTreeNode root) {
        System.out.println("*** Start codegen ***");
        CCodeGen cg = new CCodeGen();
        CTetrad tetrad = cg.convert(root);

        return tetrad;
        //cg.getTetrad();
    }

    private static CSyntaxTreeNode startParser() {
        System.out.println("*** Start parser ***");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(mResCode + ".t"));

            String line;
            List<Terminal> tokens = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                tokens.add(new Terminal(line.toLowerCase()));
            }
            tokens.add(Grammar.emptyExpessionSymbol);

            SyntaxAnalizer sa = new SyntaxAnalizer();
            sa.parseGrammars();
            sa.parseTokens(tokens);
            return sa.gerRoot();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


    public static void startLexer(String[] args) {
        System.out.println("*** Start lexer ***");
        /*String filename = "res/code3.java";
        if (args.length > 0)
            filename = args[0];*/

        char[] code = CIO.readFile(mResCode + ".java");

        CLexer lexer = new CLexer(code);
        lexer.toScan();
        lexer.toEstimate();
        lexer.result((mResCode + ".java").split("\\.")[0]);
    }
}
