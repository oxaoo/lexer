package com.github.oxaoo.codegen;

import com.github.oxaoo.parser.CSyntaxTree;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class CCodeGen
{
    /*
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
        CTetrad tetrad = null;
        switch (numRul) {
            case 0:
                tetrad = makeTetradGr5R0(node);
                node.setTetrad(tetrad);
                break;
            case 1:
                tetrad = makeTetradGr5R1(node);
                node.setTetrad(tetrad);
                break;
            case 2:
                tetrad = makeTetradGr5R2(node);
                node.setTetrad(tetrad);
                break;
            case 3:
                tetrad = makeTetradGr5R3(node);
                node.setTetrad(tetrad);
                break;
            case 4:
                tetrad = makeTetradGr5R4(node);
                node.setTetrad(tetrad);
                break;
            case 5:
                tetrad = makeTetradGr5R5(node);
                node.setTetrad(tetrad);
                break;
            case 6:
                tetrad = makeTetradGr5R6(node);
                node.setTetrad(tetrad);
                break;
            case 7:
                tetrad = makeTetradGr5R7(node);
                node.setTetrad(tetrad);
                break;
            case 8:
                tetrad = makeTetradGr5R8(node);
                node.setTetrad(tetrad);
                break;
            case 9:
                tetrad = makeTetradGr5R9(node);
                node.setTetrad(tetrad);
                break;
            case 10:
                tetrad = makeTetradGr5R10(node);
                node.setTetrad(tetrad);
                break;

            case 11:
                tetrad = makeTetradGr5R11(node);
                node.setTetrad(tetrad);
                break;
            case 12:
                tetrad = makeTetradGr5R12(node);
                node.setTetrad(tetrad);
                break;
            case 13:
                tetrad = makeTetradGr5R13(node);
                node.setTetrad(tetrad);
                break;
            case 14:
                tetrad = makeTetradGr5R14(node);
                node.setTetrad(tetrad);
                break;
        }
    }

    private void makeTetradWithGr5(int numRul, CSyntaxTreeNode node) {

        //0-21.
        CTetrad tetrad = null;
        switch (numRul) {
            case 0:
                tetrad = makeTetradGr6R0(node);
                node.setTetrad(tetrad);
                break;
            case 1:
                tetrad = makeTetradGr6R1(node);
                node.setTetrad(tetrad);
                break;
            /*
            case 2:
                tetrad = makeTetradGr6R2(node);
                node.setTetrad(tetrad);
                break;*/
            case 3:
                tetrad = makeTetradGr6R3(node);
                node.setTetrad(tetrad);
                break;
            case 4:
                tetrad = makeTetradGr6R4(node);
                node.setTetrad(tetrad);
                break;
            case 5:
                tetrad = makeTetradGr6R5(node);
                node.setTetrad(tetrad);
                break;
            case 6:
                tetrad = makeTetradGr6R6(node);
                node.setTetrad(tetrad);
                break;
            case 7:
                tetrad = makeTetradGr6R7(node);
                node.setTetrad(tetrad);
                break;
            case 8:
                tetrad = makeTetradGr6R8(node);
                node.setTetrad(tetrad);
                break;
            case 9:
                tetrad = makeTetradGr6R9(node);
                node.setTetrad(tetrad);
                break;
            case 10:
                tetrad = makeTetradGr6R10(node);
                node.setTetrad(tetrad);
                break;

            case 11:
                tetrad = makeTetradGr6R11(node);
                node.setTetrad(tetrad);
                break;
            case 12:
                tetrad = makeTetradGr6R12(node);
                node.setTetrad(tetrad);
                break;
            case 13:
                tetrad = makeTetradGr6R13(node);
                node.setTetrad(tetrad);
                break;
        }

        if (tetrad != null) node.setTetrad(tetrad);
        else
            System.err.println("Tetrad for GR6 is null");
    }

    /*
    private CTetrad makeTetradGr6R14(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 7) {
            System.err.println("Not appropriate number of arguments for the rule #6.14");
            return null;
        }

        CTetrad labelTetrad = new CTetrad("Lbegin");
        CTetrad labelTetrad2 = new CTetrad("Lend");
        CTetrad t1 = new CTetrad(EOpcode.DEFL, labelTetrad);
        CTetrad t3 = subnodes.get(5).getTetrad();
        CTetrad t4 = new CTetrad(EOpcode.BF, t3, labelTetrad2);
        CTetrad t5 = new CTetrad(EOpcode.BRL, labelTetrad);
        CTetrad t6 = new CTetrad(EOpcode.DEFL, labelTetrad2);

        CTetrad tetrad = new CTetrad(Arrays.asList(t1, t2, t3, t4, t5, t6));
        return  tetrad;
    }*/

    private CTetrad makeTetradGr6R13(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 8) {
            System.err.println("Not appropriate number of arguments for the rule #6.13");
            return null;
        }

        CTetrad labelTetrad = new CTetrad("Lbegin");
        CTetrad labelTetrad2 = new CTetrad("Lend");
        CTetrad t1 = new CTetrad(EOpcode.DEFL, labelTetrad);
        CTetrad t2 = subnodes.get(2).getTetrad();
        CTetrad t3 = subnodes.get(6).getTetrad();
        CTetrad t4 = new CTetrad(EOpcode.BF, t3, labelTetrad2);
        CTetrad t5 = new CTetrad(EOpcode.BRL, labelTetrad);
        CTetrad t6 = new CTetrad(EOpcode.DEFL, labelTetrad2);

        CTetrad tetrad = new CTetrad(Arrays.asList(t1, t2, t3, t4, t5, t6));
        return  tetrad;
    }

    private CTetrad makeTetradGr6R12(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #6.12");
            return null;
        }

        CTetrad whi = subnodes.get(0).getTetrad();
        CTetrad oper = subnodes.get(2).getTetrad();

        CTetrad tetrad = new CTetrad(Arrays.asList(whi, oper));
        return tetrad;
    }

    private CTetrad makeTetradGr6R11(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #6.11");
            return null;
        }

        CTetrad exp = subnodes.get(0).getTetrad();
        CTetrad oper = subnodes.get(2).getTetrad();

        CTetrad tetrad = new CTetrad(Arrays.asList(exp, oper));
        return tetrad;
    }

    private CTetrad makeTetradGr6R10(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #6.10");
            return null;
        }

        CTetrad varderbody = subnodes.get(0).getTetrad();
        CTetrad oper = subnodes.get(2).getTetrad();

        CTetrad tetrad = new CTetrad(Arrays.asList(varderbody, oper));
        return tetrad;
    }

    private CTetrad makeTetradGr6R9(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 4) {
            System.err.println("Not appropriate number of arguments for the rule #6.9");
            return null;
        }

        CTetrad varderbody = subnodes.get(1).getTetrad();
        CTetrad oper = subnodes.get(3).getTetrad();

        CTetrad tetrad = new CTetrad(Arrays.asList(varderbody, oper));
        return tetrad;
    }

    private CTetrad makeTetradGr6R8(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) {
            System.err.println("Not appropriate number of arguments for the rule #6.8");
            return null;
        }

        //CTetrad id = new CTetrad(subnodes.get(1).s.id);
        //TODO: проверить на соотвтетсвие другие MOV'ы.
        CTetrad tetrad = new CTetrad(EOpcode.MOV, new CTetrad(0), subnodes.get(1).s.id);

        return tetrad;
    }

    private CTetrad makeTetradGr6R7(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #6.7");
            return null;
        }

        //CTetrad id = new CTetrad(subnodes.get(1).s.id);
        CTetrad tetrad = new CTetrad(EOpcode.MOV, subnodes.get(2).getTetrad(), subnodes.get(1).s.id);

        return tetrad;
    }

    private CTetrad makeTetradGr6R6(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) {
            System.err.println("Not appropriate number of arguments for the rule #6.6");
            return null;
        }

        //(6) OPERATOR -> WHILE scolon
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr6R5(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) {
            System.err.println("Not appropriate number of arguments for the rule #6.5");
            return null;
        }

        //(5) OPERATOR -> expression scolon
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr6R4(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) {
            System.err.println("Not appropriate number of arguments for the rule #6.4");
            return null;
        }

        //(4) OPERATOR -> VARDECBODY scolon
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr6R3(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #6.3");
            return null;
        }

        //(3) OPERATOR -> midifer VARDECBODY scolon
        return subnodes.get(1).getTetrad();
    }

    private CTetrad makeTetradGr6R1(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #6.1");
            return null;
        }

        //(1) METHODBODY -> OPERATOR return scolon
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr6R0(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #6.0");
            return null;
        }

        //(0) METHODBODY -> OPERATOR
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R21(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.21");
            return null;
        }

        //(21) MULTEXP -> PRIMARY
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R20(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.20");
            return null;
        }

        //(20) ADDEXPRES -> MULTEXP
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R19(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.19");
            return null;
        }

        //(19)  EQUEXP -> ADDEXPRES
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R18(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.18");
            return null;
        }

        //(18) LOGICEXP ->  EQUEXP
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R17(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.17");
            return null;
        }

        //(17) TERNARYEXP -> LOGICEXP
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R16(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 5) {
            System.err.println("Not appropriate number of arguments for the rule #5.16");
            return null;
        }

        CTetrad e1 = subnodes.get(0).getTetrad();
        CTetrad e2 = subnodes.get(2).getTetrad();
        CTetrad e3 = subnodes.get(4).getTetrad();

        CTetrad labelTetrad = new CTetrad("Lelse");
        CTetrad labelTetrad2 = new CTetrad("Lend");
        CTetrad t1 = new CTetrad(EOpcode.BF, labelTetrad, e1, null);
        CTetrad t2 = new CTetrad(EOpcode.MOV, e2);
        CTetrad t3 = new CTetrad(EOpcode.BRL, labelTetrad2);
        CTetrad t4 = new CTetrad(EOpcode.DEFL, labelTetrad);
        CTetrad t5 = new CTetrad(EOpcode.MOV, e3);
        CTetrad t6 = new CTetrad(EOpcode.DEFL, labelTetrad2);

        CTetrad tetrad = new CTetrad(Arrays.asList(t1, t2, t3, t4, t5, t6));

        return tetrad;
    }

    private CTetrad makeTetradGr5R15(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.15");
            return null;
        }

        //(15) EXPRESSION -> TERNARYEXP
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R14(CSyntaxTreeNode node) {
        return null;
    }

    private CTetrad makeTetradGr5R13(CSyntaxTreeNode node) {
        return null;
    }

    private CTetrad makeTetradGr5R12(CSyntaxTreeNode node) {
        return null;
    }

    private CTetrad makeTetradGr5R11(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.11");
            return null;
        }

        //(11) PRIMARY -> MATROPER
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R10(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #5.2");
            return null;
        }

        String logic = subnodes.get(1).s.id.toLowerCase();

        EOpcode opcode = EOpcode.DEFAULT;

        switch (logic) {
            case "logic0":
                opcode = EOpcode.AND;
                break;
            case "logic1":
                opcode = EOpcode.OR;
                break;
        }

        //(10) LOGICEXP ->  EQUEXP LOGIC EQUEXP
        if (opcode != EOpcode.DEFAULT) {
            CTetrad tetrad = new CTetrad(opcode, subnodes.get(0).getTetrad(), subnodes.get(2).getTetrad());
            return tetrad;
        } else return null;
    }

    private CTetrad makeTetradGr5R9(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.9");
            return null;
        }

        //(9) VAR -> ID
        CTetrad tetrad = new CTetrad(subnodes.get(0).s.id);
        return tetrad;
    }

    private CTetrad makeTetradGr5R8(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.8");
            return null;
        }

        //(8) NUMBER -> BOOL
        CTetrad tetrad = new CTetrad(subnodes.get(0).s.id);
        return tetrad;
    }

    private CTetrad makeTetradGr5R7(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.7");
            return null;
        }

        //(7) NUMBER -> REAL
        CTetrad tetrad = new CTetrad(subnodes.get(0).s.id);
        return tetrad;
    }

    private CTetrad makeTetradGr5R6(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.6");
            return null;
        }

        //(5) PRIMARY -> VAR
        CTetrad tetrad = new CTetrad(subnodes.get(0).s.id);
        return tetrad;
    }

    private CTetrad makeTetradGr5R5(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.5");
            return null;
        }

        //(5) PRIMARY -> VAR
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R4(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) {
            System.err.println("Not appropriate number of arguments for the rule #5.4");
            return null;
        }

        //(4) PRIMARY -> NUMBER
        return subnodes.get(0).getTetrad();
    }

    private CTetrad makeTetradGr5R3(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #5.3");
            return null;
        }

        //(3) PRIMARY -> OP EXPRESSION CP
        return subnodes.get(1).getTetrad();
    }

    private CTetrad makeTetradGr5R2(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #5.2");
            return null;
        }

        String mult = subnodes.get(1).s.id.toLowerCase();

        EOpcode opcode = EOpcode.DEFAULT;

        switch (mult) {
            case "mult0":
                opcode = EOpcode.MUL;
                break;
            case "mult1":
                opcode = EOpcode.DIV;
                break;
        }

        //(2) MULTEXP -> PRIMARY MULT PRIMARY
        if (opcode != EOpcode.DEFAULT) {
            CTetrad tetrad = new CTetrad(opcode, subnodes.get(0).getTetrad(), subnodes.get(2).getTetrad());
            return tetrad;
        } else return null;
    }

    private CTetrad makeTetradGr5R1(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #5.1");
            return null;
        }

        String addit = subnodes.get(1).s.id.toLowerCase();

        EOpcode opcode = EOpcode.DEFAULT;

        switch (addit) {
            case "addit0":
                opcode = EOpcode.ADD;
                break;
            case "addit1":
                opcode = EOpcode.SUB;
                break;
        }

        //(1) ADDEXPRES -> MULTEXP ADDIT MULTEXP
        if (opcode != EOpcode.DEFAULT) {
            CTetrad tetrad = new CTetrad(opcode, subnodes.get(0).getTetrad(), subnodes.get(2).getTetrad());
            return tetrad;
        } else return null;
    }

    private CTetrad makeTetradGr5R0(CSyntaxTreeNode node) {
        List<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) {
            System.err.println("Not appropriate number of arguments for the rule #5.0");
            return null;
        }

        String compare = subnodes.get(1).s.id.toLowerCase();

        EOpcode opcode = EOpcode.DEFAULT;

        switch (compare) {
            case "compare0":
                opcode = EOpcode.GTS;
                break;
            case "compare1":
                opcode = EOpcode.LTS;
                break;
            case "compare2":
                opcode = EOpcode.GETS;
                break;
            case "compare3":
                opcode = EOpcode.LETS;
                break;
            case "compare4":
                opcode = EOpcode.ES;
                break;
            case "compare5":
                opcode = EOpcode.NES;
                break;
        }

        //(0)  EQUEXP -> ADDEXPRES COMPARE ADDEXPRES
        if (opcode != EOpcode.DEFAULT) {
            CTetrad tetrad = new CTetrad(opcode, subnodes.get(0).getTetrad(), subnodes.get(2).getTetrad());
            return tetrad;
        } else return null;

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
