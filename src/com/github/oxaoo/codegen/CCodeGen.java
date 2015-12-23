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

    private void makeTetrad(int numGr, int numRul, CSyntaxTreeNode node) {

        switch (numGr) {
            /*case 0:
                makeTetradWithGr0(numRul, node);
                break;
            case 1:
                makeTetradWithGr1(numRul, node);
                break;*/
            case 2:
                makeTetradWithGr2(numRul, node);
                break;
            case 3:
                makeTetradWithGr3(numRul, node);
                break;
            case 4:
                makeTetradWithGr4(numRul, node);
                break;
            case 5:
                makeTetradWithGr5(numRul, node);
                break;
            case 6:
                makeTetradWithGr6(numRul, node);
                break;
        }
    }

    private void makeTetradWithGr6(int numRul, CSyntaxTreeNode node) {

        //0-1, 3-14.
    }

    private void makeTetradWithGr5(int numRul, CSyntaxTreeNode node) {

        //0-14.
    }

    private void makeTetradWithGr4(int numRul, CSyntaxTreeNode node) {

        //0-11.
    }

    private void makeTetradWithGr3(int numRul, CSyntaxTreeNode node) {

        //0-11.
    }

    private void makeTetradWithGr2(int numRul, CSyntaxTreeNode node) {

        //2,3,5,7,10,11,13,15.
    }

    private void makeTetradWithGr1(int numRul, CSyntaxTreeNode node) {

    }

    private void makeTetradWithGr0(int numRul, CSyntaxTreeNode node) {

    }


}
