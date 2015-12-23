package com.github.oxaoo.codegen;

import com.github.oxaoo.parser.CSyntaxTree;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import java.util.Enumeration;

public class CCodeGen
{
    public void convert(CSyntaxTreeNode root) {
        printThree(root, 0);
        traversal(root, "/");

        CGrammar grammar = new CGrammar();
        grammar.loadGrammar();
    }

    private void printThree(CSyntaxTreeNode node, int depth) {
        //System.out.println("DEPTH: " + depth);
        Enumeration nodes = node.children();
        while (nodes.hasMoreElements()) {
            CSyntaxTreeNode child = (CSyntaxTreeNode) nodes.nextElement();
            for (int i = 0; i < depth; i++)
                System.out.print('\t');
            System.out.println(child.toString());
            printThree(child, depth + 1);
        }
    }

    private void traversal(CSyntaxTreeNode node, String path) {

        Enumeration nodes = node.children();
        if (nodes.hasMoreElements())
            while (nodes.hasMoreElements()) {
                CSyntaxTreeNode child = (CSyntaxTreeNode) nodes.nextElement();
                String fullpath = path + "/" + child.toString();
                traversal(child, fullpath);
            }
        else
            System.out.println("Full path: " + path);

    }


}
