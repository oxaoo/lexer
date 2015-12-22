package com.github.oxaoo.lexer;

import com.github.oxaoo.codegen.CCodeGen;
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
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        startLexer(args);
        CSyntaxTreeNode root = startParser();
        startCodeGen(root);
    }

    private static void startCodeGen(CSyntaxTreeNode root) {
        System.out.println("*** Start codegen ***");
        CCodeGen cg = new CCodeGen();
        cg.convert(root);
    }

    private static CSyntaxTreeNode startParser() {
        System.out.println("*** Start parser ***");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("res/code4.t"));

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

    private static void exampleSyntaxTree() {

        CSyntaxTreeNode node1 = new CSyntaxTreeNode("Node 1");
        CSyntaxTreeNode node2 = new CSyntaxTreeNode("Node 1-1", node1);
        CSyntaxTreeNode node3 = new CSyntaxTreeNode("Node 1-2", node1);
        CSyntaxTreeNode node4 = new CSyntaxTreeNode("Node 1-3", node1);
        CSyntaxTreeNode node5 = new CSyntaxTreeNode("Node 1-2-1", node3);

        for (int i = 1; i < 10; i++)
            node4.add(new CSyntaxTreeNode("Node 1-3-" + i));

        ArrayList<CSyntaxTreeNode> nodes = new ArrayList<>();
        for (int i = 1; i < 10; i++)
            nodes.add(new CSyntaxTreeNode("Node " + (int) (Math.random() * 100)));

        node5.setChildren(nodes);

        CSyntaxTreeNode node6 = new CSyntaxTreeNode();
        node1.add(node6);


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CSyntaxTree(node1);
            }
        });
    }

    public static void startLexer(String[] args) {
        System.out.println("*** Start lexer ***");
        String filename = "res/code4.java";
        if (args.length > 0)
            filename = args[0];

        char[] code = CIO.readFile(filename);

        CLexer lexer = new CLexer(code);
        lexer.toScan();
        lexer.toEstimate();
        lexer.result(filename.split("\\.")[0]);
    }
}
