package com.github.oxaoo.codegen;

import com.github.oxaoo.lexer.CIO;
import com.github.oxaoo.lexer.Main;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import java.util.*;

public class CCodeGen {

    private boolean inner = true;
    private CTetrad headTetrad;

    public CTetrad convert(CSyntaxTreeNode root) {
        //printThree(root, 0);
        //traversal(root, "/");
        //printTetrad(root);

        //CGrammar grammar = new CGrammar();
        //grammar.loadGrammar();

        //System.out.println("Root ID: " + root.s.id);
        genTetrad(root);
        System.out.println("Generated tetrad: " + root.getTetrad().toString());
        //System.out.println("Generated tetrad (json): " + root.getTetrad().toJson());

        String str = CTetrad.toStringFormat(root.getTetrad(), 0);
        System.out.println("String format: " + str);

        CIO.write(Main.mResCode, str);

        return (CTetrad) root.getTetrad();
    }

    private String getConFromTab(String conId) {

        String path = Main.mResCode + "_conTab.csv";
        Map<String, String> conTab = CIO.read(path);
        //System.out.println("CONTAB: " + conTab.toString());
        return conTab.get(conId);
    }


    public boolean genTetrad(CSyntaxTreeNode node) {

        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());
        if (subnodes.size() > 0) {
            for(CSyntaxTreeNode subnode: subnodes) {
                boolean b = genTetrad(subnode);
            }
        }

        /*
        Enumeration subnodes = node.children();
        if (subnodes.hasMoreElements()) {
            while (subnodes.hasMoreElements()) {
                CSyntaxTreeNode subnode = (CSyntaxTreeNode) subnodes.nextElement();
                boolean b = genTetrad(subnode);
                //if (!b) System.err.println("*** RETURN FALSE FROM GENTETRAD ***");
            }
            //makeTetrad(node.indexOfGrammar, node.indexOfRule, node);
        }*/

        boolean m = makeTetrad(node);

        //System.out.println("Node: " + node.s.id + ", size(" + subnodes.size() + "), Tetrad: " + node.getTetrad().toString());
        //if (!m) System.err.println("*** RETURN FALSE FROM MAKETETRAD ***");

        /*
        if (node.s.id.equals("TRANSLATED")) {
            System.out.println("Subtetrad of TRANSLATED: ");
            for(CSyntaxTreeNode n: subnodes)
                System.out.println("\tSubnode: " + n.s.id + ", tetrad: " + n.getTetrad().toString());
        }

        if (node.s.id.equals("TRANSLATEDDEC")) {
            System.out.println("Subtetrad of TRANSLATEDDEC: ");
            for(CSyntaxTreeNode n: subnodes)
                System.out.println("\tSubnode: " + n.s.id + ", tetrad: " + n.getTetrad().toString());
        }

        if (node.s.id.equals("CLASSBODY")) {
            System.out.println("Subtetrad of CLASSBODY: ");
            for(CSyntaxTreeNode n: subnodes)
                System.out.println("\tSubnode: " + n.s.id + ", tetrad: " + n.getTetrad().toString());
        }*/

        return m;
    }

    private boolean makeTetrad(CSyntaxTreeNode node) {

        switch (node.indexOfGrammar) {

            case 0:
                return makeTetradG0(node);
            case 1:
                return makeTetradG1(node);
            case 2:
                return makeTetradG2(node);
            case 3:
                return makeTetradG3(node);
            case 4:
                return makeTetradG4(node);
            case 5:
                return makeTetradG5(node);
            case 6:
                return makeTetradG6(node);

            case -1:
                //System.err.println("Id of node: " + node.s.id);
                return true;

            default:
                System.err.println("Unexpected index of grammar #" + node.indexOfGrammar);
                return false;
        }
    }

    private boolean makeTetradG6(CSyntaxTreeNode node) {

        switch (node.indexOfRule) {

            case 0:
                return makeTetradG6R0(node);
            case 1:
                return makeTetradG6R1(node);
            case 2:
                return makeTetradG6R2(node);
            case 3:
                return makeTetradG6R3(node);
            case 4:
            case 5:
            case 6:
                return makeTetradG6R4_6(node);
            case 7:
                return makeTetradG6R7(node);
            case 8:
                return makeTetradG6R8(node);
            case 9:
                return makeTetradG6R9(node);
            case 10:
            case 11:
            case 12:
                return makeTetradG6R10_12(node);
            case 13:
                return makeTetradG6R13(node);
            case 14:
                return makeTetradG6R14(node);
            case 15:
                return makeTetradG6R15(node);

            default:
                System.err.println("Unexpected index of rule (" + node.indexOfRule + ") for grammar #" + node.indexOfGrammar);
                return false;
        }
    }

    private boolean makeTetradG6R15(CSyntaxTreeNode node) {

        //(15) VARDECBODY -> id INIT
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) return false;
        CTetradObject op1 = subnodes.get(0).getTetrad();
        String var = subnodes.get(1).s.id;
        CTetradObject ruleTetrad = new CTetrad(EOpcode.MOV, op1, var);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R14(CSyntaxTreeNode node) {

        //(14) WHILE -> do ob cb while op expression cp
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 7) return false;

        String lBegin = CTetrad.getNewLabel("@Lbegin");
        CTetradObject opLBegin = new CTetrad(lBegin);
        CTetradObject tetrad0 = new CTetrad(EOpcode.DEFL, opLBegin);

        CTetradObject mb = new CTetradObject();

        CTetradObject le = subnodes.get(1).getTetrad();

        String lEnd = CTetrad.getNewLabel("@Lend");
        CTetradObject opLEnd = new CTetrad(lEnd);
        CTetradObject tetrad1 = new CTetrad(EOpcode.BF, le, opLEnd);

        CTetradObject tetrad2 = new CTetrad(EOpcode.BRL, opLBegin);

        CTetradObject tetrad3 = new CTetrad(EOpcode.DEFL, opLEnd);

        CTetradObject ruleTetrad = new CTetrad(Arrays.asList(tetrad0, tetrad1, tetrad2, tetrad3));
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R13(CSyntaxTreeNode node) {

        //(13) WHILE -> do ob METHODBODY cb while op expression cp
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 8) return false;

        String lBegin = CTetrad.getNewLabel("@Lbegin");
        CTetradObject opLBegin = new CTetrad(lBegin);
        CTetradObject tetrad0 = new CTetrad(EOpcode.DEFL, opLBegin);

        CTetradObject mb = subnodes.get(5).getTetrad();

        CTetradObject le = subnodes.get(1).getTetrad();

        String lEnd = CTetrad.getNewLabel("@Lend");
        CTetradObject opLEnd = new CTetrad(lEnd);
        CTetradObject tetrad1 = new CTetrad(EOpcode.BF, le, opLEnd);

        CTetradObject tetrad2 = new CTetrad(EOpcode.BRL, opLBegin);

        CTetradObject tetrad3 = new CTetrad(EOpcode.DEFL, opLEnd);

        CTetradObject ruleTetrad = new CTetrad(Arrays.asList(tetrad0, mb, tetrad1, tetrad2, tetrad3));
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R10_12(CSyntaxTreeNode node) {

        //(10) OPERATOR -> VARDECBODY scolon OPERATOR
        //(11) OPERATOR -> expression scolon OPERATOR
        //(12) OPERATOR -> WHILE scolon OPERATOR
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        CTetradObject tetrad1 = subnodes.get(2).getTetrad();
        CTetradObject tetrad2 = subnodes.get(0).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(Arrays.asList(tetrad1, tetrad2));
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R9(CSyntaxTreeNode node) {

        //(9) OPERATOR -> midifer VARDECBODY scolon OPERATOR
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 4) return false;

        CTetradObject tetrad1 = subnodes.get(2).getTetrad();
        CTetradObject tetrad2 = subnodes.get(0).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(Arrays.asList(tetrad1, tetrad2));
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R8(CSyntaxTreeNode node) {

        //(8) VARDECBODY -> type id
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) return false;

        CTetradObject defOp1 = new CTetrad();
        String var = subnodes.get(0).s.id;

        CTetradObject ruleTetrad = new CTetrad(EOpcode.MOV, defOp1, var);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R7(CSyntaxTreeNode node) {

        //(7) VARDECBODY -> type id init
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;
        CTetradObject op1 = subnodes.get(0).getTetrad();
        String var = subnodes.get(1).s.id;
        CTetradObject ruleTetrad = new CTetrad(EOpcode.MOV, op1, var);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R4_6(CSyntaxTreeNode node) {

        //(4) OPERATOR -> VARDECBODY scolon
        //(5) OPERATOR -> expression scolon
        //(6) OPERATOR -> WHILE scolon
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) return false;

        CTetradObject ruleTetrad = subnodes.get(1).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R3(CSyntaxTreeNode node) {

        //(3) OPERATOR -> modifer VARDECBODY scolon
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        CTetradObject ruleTetrad = subnodes.get(1).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R2(CSyntaxTreeNode node) {

        //(2) METHODBODY -> OPERATOR return expression scolon
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 4) return false;

        CTetradObject tetrad1 = subnodes.get(3).getTetrad();
        CTetradObject tetrad2 = subnodes.get(1).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(Arrays.asList(tetrad1, tetrad2));
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R1(CSyntaxTreeNode node) {

        //(1) METHODBODY -> OPERATOR return scolon
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        CTetradObject ruleTetrad = subnodes.get(2).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG6R0(CSyntaxTreeNode node) {

        //(0) METHODBODY -> OPERATOR
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        CTetradObject ruleTetrad = subnodes.get(0).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5(CSyntaxTreeNode node) {

        switch (node.indexOfRule) {

            case 0:
                return makeTetradG5R0(node);
            case 1:
                return makeTetradG5R1(node);
            case 2:
                return makeTetradG5R2(node);
            case 3:
                return makeTetradG5R3(node);
            case 4:
            case 5:
                return makeTetradG5R4_5(node);
            case 6:
            case 7:
            case 8:
                return makeTetradG5R6_8(node);
            case 9:
                return makeTetradG5R9(node);
            case 10:
                return makeTetradG5R10(node);
            case 11:
                return makeTetradG5R11(node);
            case 12:
            case 13:
            case 14:
                return makeTetradG5R12_14(node);
            case 15:
                return makeTetradG5R15(node);
            case 16:
                return makeTetradG5R16(node);
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return makeTetradG5R17_21(node);

            default:
                System.err.println("Unexpected index of rule (" + node.indexOfRule + ") for grammar #" + node.indexOfGrammar);
                return false;
        }
    }

    private boolean makeTetradG5R17_21(CSyntaxTreeNode node) {

        //(17) TERNARYEXP -> LOGICEXP
        //(18) LOGICEXP ->  EQUEXP
        //(19)  EQUEXP -> ADDEXPRES
        //(20) ADDEXPRES -> MULTEXP
        //(21) MULTEXP -> PRIMARY
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        CTetradObject ruleTetrad = subnodes.get(0).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R16(CSyntaxTreeNode node) {

        //(16) TERNARYEXP -> LOGICEXP QTO LOGICEXP CTO LOGICEXP
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 5) return false;

        CTetradObject t = subnodes.get(4).getTetrad();
        String lElse = CTetrad.getNewLabel("@Lelse");
        CTetradObject opLElse = new CTetrad(lElse);
        CTetradObject tetrad1 = new CTetrad(EOpcode.BF, opLElse, t);

        CTetradObject le1 = subnodes.get(2).getTetrad();
        CTetradObject tetrad2 = new CTetrad(EOpcode.MOV, le1);

        String lEnd = CTetrad.getNewLabel("@LEnd");
        CTetradObject opLEnd = new CTetrad(lEnd);
        CTetradObject tetrad3 = new CTetrad(EOpcode.BRL, opLEnd);

        CTetradObject tetrad4 = new CTetrad(EOpcode.DEFL, opLElse);

        CTetradObject le2 = subnodes.get(0).getTetrad();
        CTetradObject tetrad5 = new CTetrad(EOpcode.MOV, le2);

        CTetradObject tetrad6 = new CTetrad(EOpcode.DEFL, opLEnd);

        CTetradObject ruleTetrad = new CTetrad(Arrays.asList(
                tetrad1, tetrad2, tetrad3, tetrad4, tetrad5, tetrad6));
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R15(CSyntaxTreeNode node) {

        //(15) EXPRESSION -> TERNARYEXP
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        CTetradObject ruleTetrad = subnodes.get(0).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R12_14(CSyntaxTreeNode node) {

        //(12) MATROPER -> HEIGHT OP VAR CP
        //(13) MATROPER -> WEIGHT OP VAR CP
        //(14) MATROPER -> transpose OP VAR CP
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 4) return false;

        String idFun = subnodes.get(3).s.id;
        String idVar = subnodes.get(1).s.id;
        CTetradObject op1 = new CTetrad(idFun);
        //TODO: write +id or tetrad(id) ???
        CTetradObject op2 = new CTetrad(idVar);
        CTetradObject ruleTetrad = new CTetrad(EOpcode.CALL, op1, op2);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R11(CSyntaxTreeNode node) {

        //(11) PRIMARY -> MATROPER
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        CTetradObject ruleTetrad = subnodes.get(0).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R10(CSyntaxTreeNode node) {

        //(10) LOGICEXP ->  EQUEXP LOGIC EQUEXP
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        String logic = subnodes.get(1).s.id;
        int isLogic = Integer.valueOf(logic.substring(logic.length() - 1));

        EOpcode opcode = EOpcode.DEFAULT;
        switch (isLogic) {
            case 0: // &&
                opcode = EOpcode.AND;
                break;
            case 1: // ||
                opcode = EOpcode.OR;
                break;

            default:
                System.err.println("Undefined operation of logic");
                return false;
        }

        CTetradObject op1 = subnodes.get(2).getTetrad();
        CTetradObject op2 = subnodes.get(0).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(opcode, op1, op2);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R9(CSyntaxTreeNode node) {

        //(9) VAR -> ID
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        String id = subnodes.get(0).s.id;
        CTetradObject idTetrad = new CTetrad("#" + id);
        /*
        CTetradObject idTetrad = CTetrad.getTetrad(id);
        if (idTetrad == null) {
            System.err.println("Not found tetrad by id #" + id);
            return false;
        }*/

        //TODO: write +id or tetrad(id) ???
        node.setTetrad(idTetrad);
        //CTetradObject ruleTetrad = new CTetrad(EOpcode.MOV, idTetrad);
        //node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R6_8(CSyntaxTreeNode node) {

        //(6) NUMBER -> INT
        //(7) NUMBER -> REAL
        //(8) NUMBER -> BOOL
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        String conId = subnodes.get(0).s.id;
        conId = conId.toUpperCase();
        String conValue = getConFromTab(conId);

        if (conValue == null) {
            System.err.println("Not found constant id #" + conId);
            return false;
        }

        CTetradObject ruleTetrad = new CTetrad(conValue);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R4_5(CSyntaxTreeNode node) {

        //(4) PRIMARY -> NUMBER
        //(5) PRIMARY -> VAR
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        CTetradObject ruleTetrad = subnodes.get(0).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R3(CSyntaxTreeNode node) {

        //(3) PRIMARY -> OP EXPRESSION CP
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        CTetradObject ruleTetrad = subnodes.get(1).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R2(CSyntaxTreeNode node) {

        //(2) MULTEXP -> PRIMARY MULT PRIMARY
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        String addit = subnodes.get(1).s.id;
        int isAddit = Integer.valueOf(addit.substring(addit.length() - 1));

        EOpcode opcode = EOpcode.DEFAULT;
        switch (isAddit) {
            case 0: // *
                opcode = EOpcode.MUL;
                break;
            case 1: // /
                opcode = EOpcode.DIV;
                break;

            default:
                System.err.println("Undefined operation of mult");
                return false;
        }

        CTetradObject op1 = subnodes.get(2).getTetrad();
        CTetradObject op2 = subnodes.get(0).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(opcode, op1, op2);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R1(CSyntaxTreeNode node) {

        //(1) ADDEXPRES -> MULTEXP ADDIT MULTEXP
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        String addit = subnodes.get(1).s.id;
        int isAddit = Integer.valueOf(addit.substring(addit.length() - 1));

        EOpcode opcode = EOpcode.DEFAULT;
        switch (isAddit) {
            case 0: // +
                opcode = EOpcode.ADD;
                break;
            case 1: // -
                opcode = EOpcode.SUB;
                break;

            default:
                System.err.println("Undefined operation of addition");
                return false;
        }

        CTetradObject op1 = subnodes.get(2).getTetrad();
        CTetradObject op2 = subnodes.get(0).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(opcode, op1, op2);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG5R0(CSyntaxTreeNode node) {

        //(0)  EQUEXP -> ADDEXPRES COMPARE ADDEXPRES
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        String compare = subnodes.get(1).s.id;
        int isCompare = Integer.valueOf(compare.substring(compare.length() - 1));

        EOpcode opcode = EOpcode.DEFAULT;
        switch (isCompare) {
            case 0: // >
                opcode = EOpcode.GTS;
                break;
            case 1: // <
                opcode = EOpcode.LTS;
                break;
            case 2: // >=
                opcode = EOpcode.GETS;
                break;
            case 3: // <=
                opcode = EOpcode.LETS;
                break;
            case 4: // ==
                opcode = EOpcode.ES;
                break;
            case 5: // !=
                opcode = EOpcode.NES;
                break;

            default:
                System.err.println("Undefined operation of compare");
                return false;
        }

        CTetradObject op1 = subnodes.get(2).getTetrad();
        CTetradObject op2 = subnodes.get(0).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(opcode, op1, op2);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG4(CSyntaxTreeNode node) {

        switch (node.indexOfRule) {

            case 0:
            case 1:
                return makeTetradG4R0_1(node);
            case 2:
            case 3:
            case 4:
                return makeTetradG4R2_4(node);
            case 5:
                return makeTetradG4R5(node);
            case 6:
                return makeTetradG4R6(node);
            case 7:
                return makeTetradG4R7(node);
            case 8:
            case 9:
            case 10:
                return makeTetradG4R8(node);
            case 11:
                return makeTetradG4R11(node);

            default:
                System.err.println("Unexpected index of rule (" + node.indexOfRule + ") for grammar #" + node.indexOfGrammar);
                return false;
        }
    }

    private boolean makeTetradG4R11(CSyntaxTreeNode node) {

        //(11) VALUE -> ID
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        String id = subnodes.get(0).s.id;
        CTetradObject idTetrad = new CTetrad("#" + id);
        /*
        CTetradObject idTetrad = CTetrad.getTetrad(id);
        if (idTetrad == null) {
            System.err.println("Not found tetrad by id #" + id);
            return false;
        }*/

        //TODO: write +id or tetrad(id) ???
        node.setTetrad(idTetrad);
        //CTetradObject ruleTetrad = new CTetrad(EOpcode.MOV, idTetrad);
        //node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG4R8(CSyntaxTreeNode node) {

        //(8) MATREXPBODY -> OB MATREXPELEM CB comma OB MATREXPELEM CB
        /*
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());


        if (subnodes.size() != 7) return false;
        */

        return false;
    }

    private boolean makeTetradG4R7(CSyntaxTreeNode node) {

        //(7) MATREXP -> OB MATREXPBODY CB
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 3) return false;

        CTetradObject ruleTetrad = subnodes.get(1).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG4R6(CSyntaxTreeNode node) {

        //(6) MATREXP -> NEW TYPE8 OBT INT CBT
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 5) return false;

        int i = Integer.parseInt(subnodes.get(1).s.id);

        double matrix[][] = new double[i][];
        CTetradObject ruleTetrad = new CTetrad(matrix);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG4R5(CSyntaxTreeNode node) {

        //(5) MATREXP -> NEW TYPE8 OBT INT CBT OBT INT CBT
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 8) return false;

        int i = Integer.parseInt(subnodes.get(4).s.id);
        int j = Integer.parseInt(subnodes.get(1).s.id);

        double matrix[][] = new double[i][j];
        CTetradObject ruleTetrad = new CTetrad(matrix);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG4R2_4(CSyntaxTreeNode node) {

        //(2) VALUE -> INT
        //(3) VALUE -> BOOL
        //(4) VALUE -> REAL

        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 1) return false;

        String conId = subnodes.get(0).s.id;
        conId = conId.toUpperCase();
        String conValue = getConFromTab(conId);

        if (conValue == null) {
            System.err.println("Not found constant id #" + conId);
            return false;
        }

        CTetradObject ruleTetrad = new CTetrad(conValue);
        node.setTetrad(ruleTetrad);

        return true;
    }


    private boolean makeTetradG4R0_1(CSyntaxTreeNode node) {

        //(0) INIT -> ASSIGN EXRESSION
        //(1) INIT -> ASSIGN MATREXP
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 2) return false;

        CTetradObject op = subnodes.get(0).getTetrad();

        String assign = subnodes.get(1).s.id;
        int idAssign = Integer.valueOf(assign.substring(assign.length() - 1));

        EOpcode opcode = EOpcode.DEFAULT;
        switch (idAssign) {
            case 0: // =
                opcode = EOpcode.MOV;
                CTetradObject ruleTetrad = new CTetrad(opcode, op);
                node.setTetrad(ruleTetrad);
                return true;
            case 1: // *=
                opcode = EOpcode.MULA;
                return false;
            case 2: // /=
                opcode = EOpcode.DIVA;
                return false;
            case 3: // +=
                opcode = EOpcode.ADDA;
                return false;
            case 4: // -=
                opcode = EOpcode.SUBA;
                return false;

            default:
                System.err.println("Undefined operation of assign");
                return false;
        }

        //TODO: for MULA, DIVA, ADDA and SUBA implement the throw.
        /*
        String idTetrad = subnodes.get()
        CTetradObject op1 = CTetrad.getTetrad()
        CTetradObject ruleTetrad = new CTetrad(opcode, )
        */
    }

    private boolean makeTetradG3(CSyntaxTreeNode node) {

        switch (node.indexOfRule) {

            case 0:
            case 1:
                return makeTetradG3R0_1(node);

            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return makeTetradG3R2_11(node);

            default:
                System.err.println("Unexpected index of rule (" + node.indexOfRule + ") for grammar #" + node.indexOfGrammar);
                return false;
        }

    }

    private boolean makeTetradG3R2_11(CSyntaxTreeNode node) {

        //(2) PRIMTYPE -> type0
        //...
        //(11) REFTYPE -> id
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());
        if (subnodes.size() != 1) return false;

        String strType = subnodes.get(0).s.id;
        CTetradObject ruleTetrad = new CTetrad(strType);
        node.setTetrad(ruleTetrad);
        return true;
    }


    private boolean makeTetradG3R0_1(CSyntaxTreeNode node) {

        //(0) TYPE -> PRIMTYPE
        //(1) TYPE -> REFTYPE
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());
        if (subnodes.size() != 1) return false;

        CTetradObject ruleTetrad = subnodes.get(0).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG2(CSyntaxTreeNode node) {

        switch (node.indexOfRule) {

            case 0:
                return makeTetradG2R0(node);
            case 1:
                return makeTetradG2R1(node);
            case 2:
                return makeTetradG2R2(node);
            case 3:
                return makeTetradG2R3(node);
            case 4:
                return makeTetradG2R4(node);
            case 5:
                return makeTetradG2R5(node);
            case 10:
                return makeTetradG2R10(node);
            case 11:
                return makeTetradG2R11(node);

            default:
                System.err.println("Unexpected index of rule (" + node.indexOfRule + ") for grammar #" + node.indexOfGrammar);
                return false;
        }
    }

    private boolean makeTetradG2R5(CSyntaxTreeNode node) {

        //System.err.println("CCodeGen.makeTetradG2R5");
        //(5)	$CLASSBODY ➝ MODIFIER TYPE ID OP CP OB METHODBODY  CB
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 8) return false;

        CTetradObject ruleTetrad = subnodes.get(1).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG2R4(CSyntaxTreeNode node) {

        //System.err.println("CCodeGen.makeTetradG2R4");
        //(4)	$CLASSBODY ➝ MODIFIER TYPE ID OP $METHODPARAM CP OB METHODBODY  CB
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        //System.err.println("SIZE: " + subnodes.size());

        //TODO: for dydus - input 8 args, but is expected 9!!!
        if (subnodes.size() != 8) return false;

        CTetradObject ruleTetrad = subnodes.get(1).getTetrad();
        //System.err.println("RULETETRAD: " + ruleTetrad.toString());
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG2R11(CSyntaxTreeNode node) {

        //(11)	$CLASSBODY ➝ MODIFIER TYPE ID INIT SCOLON $CLASSBODY
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 6) return false;

        CTetradObject op1 = subnodes.get(2).getTetrad();
        String var = subnodes.get(3).s.id;

        CTetradObject tetrad1 = new CTetrad(EOpcode.MOV, op1, var);
        CTetradObject tetrad2 = subnodes.get(0).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(Arrays.asList(tetrad1, tetrad2));
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG2R10(CSyntaxTreeNode node) {

        //(10)	$CLASSBODY ➝ MODIFIER TYPE ID SCOLON $CLASSBODY
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 5) return false;

        CTetradObject defOp1 = new CTetrad();
        String var = subnodes.get(2).s.id;

        CTetradObject tetrad1 = new CTetrad(EOpcode.MOV, defOp1, var);
        CTetradObject tetrad2 = subnodes.get(0).getTetrad();
        CTetradObject ruleTetrad = new CTetrad(Arrays.asList(tetrad1, tetrad2));
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG2R3(CSyntaxTreeNode node) {

        //(3)	$CLASSBODY ➝ MODIFIER TYPE ID SCOLON
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 4) return false;

        CTetradObject defOp1 = new CTetrad();
        String var = subnodes.get(1).s.id;

        CTetradObject ruleTetrad = new CTetrad(EOpcode.MOV, defOp1, var);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG2R2(CSyntaxTreeNode node) {

        //(2)	$CLASSBODY ➝ MODIFIER TYPE ID INIT  SCOLON
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 5) return false;

        CTetradObject op1 = subnodes.get(1).getTetrad();
        String var = subnodes.get(2).s.id;

        CTetradObject ruleTetrad = new CTetrad(EOpcode.MOV, op1, var);
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG2R1(CSyntaxTreeNode node) {
        return false;
    }

    private boolean makeTetradG2R0(CSyntaxTreeNode node) {

        //(0)	$CLASSBODY ➝ENM ID OB $ENUMBODY CB
        /*
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());


        CTetradObject op2 = subnodes.get(1).getTetrad();
        String strOp2 = subnodes.get(3).s.id;
        CTetradObject op1 = new CTetrad(strOp2);

        CTetradObject ruleTetrad = null;
        node.setTetrad(ruleTetrad);*/
        return false;
    }

    private boolean makeTetradG1(CSyntaxTreeNode node) {

        switch (node.indexOfRule) {

            case 0:
                return makeTetradG1R0(node);
            case 2:
                return makeTetradG1R2(node);


            default:
                System.err.println("Unexpected index of rule (" + node.indexOfRule + ") for grammar #" + node.indexOfGrammar);
                return false;
        }
    }

    private boolean makeTetradG1R2(CSyntaxTreeNode node) {

        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 6) return false;

        CTetradObject ruleTetrad = subnodes.get(1).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG1R0(CSyntaxTreeNode node) {

        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 7) return false;

        CTetradObject ruleTetrad = subnodes.get(1).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

    private boolean makeTetradG0(CSyntaxTreeNode node) {

        switch (node.indexOfRule) {

            case 0:
                return makeTetradG0R0(node);

            default:
                System.err.println("Unexpected index of rule (" + node.indexOfRule + ") for grammar #" + node.indexOfGrammar);
                return false;
        }
    }

    private boolean makeTetradG0R0(CSyntaxTreeNode node) {

        //(0)	$TRANSLATED ➝ PACKAGE ID SCOLON TRANSLATEDDEC
        ArrayList<CSyntaxTreeNode> subnodes = Collections.list(node.children());

        if (subnodes.size() != 4) return false;

        CTetradObject ruleTetrad = subnodes.get(0).getTetrad();
        node.setTetrad(ruleTetrad);
        return true;
    }

}
