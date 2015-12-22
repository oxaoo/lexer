package com.github.oxaoo.lexer.syntax;

import com.github.oxaoo.parser.CSyntaxTree;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import javax.swing.*;
import java.util.*;

/**
 * Created by dydus on 13/11/2015.
 */
public class SyntaxAnalizer {
    private Grammar currentGrammar;
    public List<Grammar> gr = new ArrayList<Grammar>();
    public List<Grammar> fullGrammars = new ArrayList<Grammar>();
//    public List<Symbol> mp = new ArrayList<>();
    public List<Integer> output = new ArrayList<>();
    public List<Grammar> stack = new ArrayList<>();

    public void parseGrammars() {
        String path = "res/syntax/";
        GrammarParser parser = new GrammarParser();
        for (int i = 0; i <= 6; i++) {
            Grammar g = parser.parse(path + i + "/");
            g.name = i;
            gr.add(g);
            //System.out.println(g.toString());
        }

        fullGrammars = new ArrayList<>(gr);
    }

    private CSyntaxTreeNode root;
    public CSyntaxTreeNode gerRoot() {
        return root;
    }

    public void parseTokens( List<Terminal> tokens) {
//        this.mp.add(Grammar.emptyStackSymbol);
        System.out.println(tokens.toString());
        currentGrammar = this.gr.get(0);
        Grammar root = currentGrammar;
        System.out.println("START PARSING");

        while (tokens.size() > 0) {
            Terminal t = tokens.get(0);
            TransferType type = typeOfTransfer(t);
            if (type == TransferType.MOVING) {
                Grammar nextGrammar = new Grammar(gr.get(type.nextGrammar));
                stack.add(0, currentGrammar);
                currentGrammar = nextGrammar;
                System.out.println("NEXT GRAMMAR. NEXT: " + type.nextGrammar);
            } else if (type == TransferType.ERROR) {
                System.err.println("Error. Next Token: " + t + " MP: " + currentGrammar.mp);
                return;
            } else if (type == TransferType.ACCESS) {

                if (stack.size()!=0) {
                    Grammar g = stack.remove(0);
                    g.mp.add(0, new Terminal(currentGrammar.S.id));
                    g.nodes.add(0, currentGrammar.nodes.get(0));
                    currentGrammar = g;
                } else {
                    break;
                }

                System.out.println("ACCESS. Token: " + t);
            } else if (type == TransferType.TRANSFER) {
                tokens.remove(0);
                transfer(t);
                System.out.println("TRANSFER. Token: " + t);
            } else if (type == TransferType.CONVOLUTION) {
                System.out.println("START CONVOLUTION mp: " + currentGrammar.mp);
                convolution();
            }
        }
        System.out.println("STOP PARSING");

        this.root = root.nodes.get(0);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CSyntaxTree(root.nodes.get(0));
            }
        });
    }

    public TransferType typeOfTransfer(Terminal t) {
        List<Transfer> availableTransfers = new ArrayList<>();
        List<Symbol> mpCopy = new ArrayList<>(currentGrammar.mp);

        for (int i = 1; i <= mpCopy.size(); i++) {
            Transfer transfer = new Transfer();
            transfer.column = t;
            transfer.row = reverse(mpCopy.subList(0, i));

            availableTransfers.add(0, transfer);
        }

        for (int i = 0; i < availableTransfers.size(); i++) {
            Transfer tr = availableTransfers.get(i);

            Integer index = null;
            for (int j = 0; j < currentGrammar.sortedTransfer.size(); j++) {
                if (currentGrammar.sortedTransfer.get(j).equals(tr)) {
                    index = j;
                    break;
                }
            }

            if (index != null && index != -1) {
                TransferType result = currentGrammar.transferValue.get(index);
                if (result != TransferType.ERROR) {
                    return result;
                }
            }
        }

        Integer next = currentGrammar.nextGrammars.get(currentGrammar.mp.get(0));
        if (next != null) {
            TransferType result = TransferType.MOVING;
            result.nextGrammar = next;
            return result;
        }

        next = currentGrammar.nextGrammars.get(t);
        if (next != null) {
            TransferType result = TransferType.MOVING;
            result.nextGrammar = next;
            return result;
        }

        if (!currentGrammar.terminals.contains(t)) {
            if (currentGrammar.S.equals(currentGrammar.mp.get(0))) {
                return TransferType.ACCESS;
            } else {
                return TransferType.CONVOLUTION;
            }
        }

        if (indexOfConvolution() != null) {
            convolution();
            return TransferType.ACCESS;
        }

        return TransferType.ERROR;
    }

    public void transfer(Terminal t) {
        currentGrammar.mp.add(0, t);
        currentGrammar.nodes.add(0, new CSyntaxTreeNode(t));
        gr = new ArrayList<>(fullGrammars);
    }

    public void convolution() {
        for (int i = 0; i < currentGrammar.sortedConvolution.size(); i++) {
            Convolution c = currentGrammar.sortedConvolution.get(i);
            if (currentGrammar.mp.size() <= c.convolution.size()) {
                continue;
            }
            Convolution newConv = new Convolution();
            newConv.prefix = currentGrammar.mp.get(c.convolution.size());
            newConv.convolution = reverse(currentGrammar.mp.subList(0, c.convolution.size()));

            if (c.equals(newConv)) {
                Integer indexOfRule = currentGrammar.convolutionValue.get(i);
                currentGrammar.mp = currentGrammar.mp.subList(c.convolution.size(), currentGrammar.mp.size());
                currentGrammar.mp.add(0, currentGrammar.S);

                CSyntaxTreeNode newNode = new CSyntaxTreeNode(currentGrammar.S);
                newNode.setChildren(currentGrammar.nodes.subList(0, c.convolution.size()));
                currentGrammar.nodes = currentGrammar.nodes.subList(c.convolution.size(), currentGrammar.nodes.size());
                currentGrammar.nodes.add(0, newNode);

                output.add(indexOfRule);
                System.out.println("CONVOLUTION. mp: " + currentGrammar.mp);
//                convolution();
                return;
            }
        }
    }

    public Integer indexOfConvolution() {
        for (int i = 0; i < currentGrammar.sortedConvolution.size(); i++) {
            Convolution c = currentGrammar.sortedConvolution.get(i);
            if (currentGrammar.mp.size() <= c.convolution.size()) {
                continue;
            }
            Convolution newConv = new Convolution();
            newConv.prefix = currentGrammar.mp.get(c.convolution.size());
            newConv.convolution = reverse( currentGrammar.mp.subList(0, c.convolution.size()) );

            if (c.equals(newConv)) {
                Integer indexOfRule = currentGrammar.convolutionValue.get(i);
                return indexOfRule;
            }
        }

        return null;
    }

    private List<Symbol> reverse(List<Symbol> list) {
        List<Symbol> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}