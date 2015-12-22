package com.github.oxaoo.codegen;

import com.github.oxaoo.parser.CSyntaxTree;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import java.util.Enumeration;

public class CCodeGen
{
    public void convert(CSyntaxTreeNode root) {
        //printThree(root, 0);
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


}
