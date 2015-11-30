package com.github.oxaoo.lexer;

import com.github.oxaoo.parser.CSyntaxTree;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Alexander on 01.11.2015.
 *
 * Программа: Лексический анализатор
 * Предметр: Разработка языковых процессов
 * Семестр: 9
 */

public class Main
{
    public static void main(String[] args)
    {
        //startLexer(args);
        exampleSyntaxTree();
    }

    private static void exampleSyntaxTree()
    {

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


        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new CSyntaxTree(node1);
            }
        });
    }

    public static void startLexer(String[] args)
    {
        System.out.println("*** Start lexer ***");
        String filename = "res/code3.java";
        if (args.length > 0)
            filename = args[0];

        char[] code = CIO.readFile(filename);

        CLexer lexer = new CLexer(code);
        lexer.toScan();
        lexer.toEstimate();
        lexer.result(filename.split("\\.")[0]);
    }
}
