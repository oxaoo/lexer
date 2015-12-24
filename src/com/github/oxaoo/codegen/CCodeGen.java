package com.github.oxaoo.codegen;

import com.github.oxaoo.parser.CSyntaxTree;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class CCodeGen
{
    /*
    TODO: Дописать все правила подграмматик;
    TODO: Подтвердить PULLREQ;
    TODO: Начать обход с листов;
     */
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
        //=> 0.
        CTetrad tetrad = null;
        switch (numRul) {
            case 0:
                tetrad = makeTetradGr4R0(node);
                node.setTetrad(tetrad);
                break;
            /*
            case 1:
                tetrad = makeTetradGr4R1(node);
                node.setTetrad(tetrad);
                break;
            case 2:
                tetrad = makeTetradGr4R2(node);
                node.setTetrad(tetrad);
                break;
            case 3:
                tetrad = makeTetradGr4R3(node);
                node.setTetrad(tetrad);
                break;
            case 4:
                tetrad = makeTetradGr4R4(node);
                node.setTetrad(tetrad);
                break;
            case 5:
                tetrad = makeTetradGr4R5(node);
                node.setTetrad(tetrad);
                break;
            case 6:
                tetrad = makeTetradGr4R6(node);
                node.setTetrad(tetrad);
                break;
            case 7:
                tetrad = makeTetradGr4R7(node);
                node.setTetrad(tetrad);
                break;
            case 8:
                tetrad = makeTetradGr4R8(node);
                node.setTetrad(tetrad);
                break;
            case 9:
                tetrad = makeTetradGr4R9(node);
                node.setTetrad(tetrad);
                break;
            case 10:
                tetrad = makeTetradGr4R10(node);
                node.setTetrad(tetrad);
                break;
            case 11:
                tetrad = makeTetradGr4R11(node);
                node.setTetrad(tetrad);
                break;
            */
        }

        if (tetrad != null) node.setTetrad(tetrad);
        else
            System.err.println("Tetrad for GR4 is null");
    }

    private CTetrad makeTetradGr4R0(CSyntaxTreeNode node) {

        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) {
            System.err.println("Not appropriate number of arguments for the rule #4.0");
            return null;
        }

        //(0) INIT -> ASSIGN EXRESSION
        CTetrad tetrad = new CTetrad(EOpcode.MOV, subnodes.get(1).getTetrad());
        return tetrad;
    }

    private void makeTetradWithGr3(int numRul, CSyntaxTreeNode node) {

        //0-11.
        // =>.
    }

    private void makeTetradWithGr2(int numRul, CSyntaxTreeNode node) {

        //2,3,5,7,10,11,13,15.
        // => 2,3,5.
        CTetrad tetrad = null;
        switch (numRul) {
            case 2:
                tetrad = makeTetradGr2R2(node);
                node.setTetrad(tetrad);
                break;
            case 3:
                tetrad = makeTetradGr2R3(node);
                node.setTetrad(tetrad);
                break;
            case 5:
                tetrad = makeTetradGr2R5(node);
                node.setTetrad(tetrad);
                break;
            case 7:
                tetrad = makeTetradGr2R7(node);
                node.setTetrad(tetrad);
                break;
            case 10:
                tetrad = makeTetradGr2R10(node);
                node.setTetrad(tetrad);
                break;
            case 11:
                tetrad = makeTetradGr2R11(node);
                node.setTetrad(tetrad);
                break;
            case 13:
                tetrad = makeTetradGr2R13(node);
                node.setTetrad(tetrad);
                break;
            case 15:
                tetrad = makeTetradGr2R15(node);
                node.setTetrad(tetrad);
                break;
        }

        if (tetrad != null) node.setTetrad(tetrad);
        else
            System.err.println("Tetrad for GR2 is null");
    }

    private CTetrad makeTetradGr2R15(CSyntaxTreeNode node) {
        return null;
    }

    private CTetrad makeTetradGr2R13(CSyntaxTreeNode node) {
        return null;
    }

    private CTetrad makeTetradGr2R11(CSyntaxTreeNode node) {
        return null;
    }

    private CTetrad makeTetradGr2R10(CSyntaxTreeNode node) {
        return null;
    }

    private CTetrad makeTetradGr2R7(CSyntaxTreeNode node) {
        return null;
    }

    private CTetrad makeTetradGr2R5(CSyntaxTreeNode node) {

        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 8) {
            System.err.println("Not appropriate number of arguments for the rule #2.5");
            return null;
        }

        //(5)	$CLASSBODY ? MODIFIER TYPE ID OP CP OB METHODBODY  CB
        CTetrad tetrad = new CTetrad(EOpcode.MAIN, subnodes.get(6).getTetrad(), subnodes.get(2).s.id);
        return tetrad;
    }

    private CTetrad makeTetradGr2R3(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 4) {
            System.err.println("Not appropriate number of arguments for the rule #2.3");
            return null;
        }

        //(3)	$CLASSBODY ? MODIFIER TYPE ID SCOLON
        CTetrad tetrad = new CTetrad(EOpcode.MOV, new CTetrad(0), subnodes.get(2).s.id);
        return tetrad;
    }

    private CTetrad makeTetradGr2R2(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 5) {
            System.err.println("Not appropriate number of arguments for the rule #2.2");
            return null;
        }

        //(2)	$CLASSBODY ? MODIFIER TYPE ID INIT  SCOLON
        CTetrad tetrad = new CTetrad(EOpcode.MOV, subnodes.get(3).getTetrad(), subnodes.get(2).s.id);
        return tetrad;
    }

    private void makeTetradWithGr1(int numRul, CSyntaxTreeNode node) {

    }

    private void makeTetradWithGr0(int numRul, CSyntaxTreeNode node) {

    }


}
