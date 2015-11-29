package com.github.oxaoo.lexer.syntax;

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
    public Stack<Grammar> stack = new Stack<>();

    public void parseGrammars() {
        String path = "res/syntax/";
        GrammarParser parser = new GrammarParser();
        for (int i = 0; i <= 6; i++) {
            Grammar g = parser.parse(path + i + "/");
            g.name = i;
            gr.add(g);
            System.out.println(g.toString());
        }

        fullGrammars = new ArrayList<>(gr);
    }

    public void parseTokens( List<Terminal> tokens) {
//        this.mp.add(Grammar.emptyStackSymbol);
        System.out.println(tokens.toString());

        stack.push(currentGrammar);
        currentGrammar = this.gr.get(0);

        System.out.println("START PARSING");
        while (tokens.size() > 1) {
            Terminal t = tokens.get(0);
            TransferType type = typeOfTransfer(t);
            if (type == TransferType.MOVING) {
                Grammar nextGrammar = new Grammar(gr.get(type.nextGrammar));
                stack.push(nextGrammar);
                currentGrammar = nextGrammar;
                System.out.println("NEXT GRAMMAR. NEXT: " + type.nextGrammar);
            } else if (type == TransferType.ERROR) {
                System.err.println("Error. Next Token: " + t + "\nMP: " + currentGrammar.mp);
                return;
            } else if (type == TransferType.ACCESS) {
                Grammar g = currentGrammar;
                currentGrammar = stack.pop();
                if (currentGrammar != null) {
                    currentGrammar.mp.add(g.mp.get(1));
                }
                System.out.println("ACCESS. Token: " + t);
            } else if (type == TransferType.TRANSFER) {
                tokens.remove(0);
                transfer(t);
                System.out.println("TRANSFER. Token: " + t);
            } else if (type == TransferType.CONVOLUTION) {
                System.out.println("START CONVOLUTION mp: " + currentGrammar.mp);
                convolution();

                tokens.remove(0);
                transfer(t);
                System.out.println("TRANSFER. Token: " + t);
            }
        }
        System.out.println("STOP PARSING");
    }


    public Grammar findNextGrammar(Terminal t) {
        for (int i = 0; i < gr.size(); i++) {
            Grammar g = gr.get(i);
            if (g.terminals.contains(t) && g != currentGrammar) {
                gr.remove(i);
                g.mp.clear();
                return g;
            }
        }
        return null;
    }

    public TransferType typeOfTransfer(Terminal t) {
        List<Transfer> availableTransfers = new ArrayList<>();
        for (int i = currentGrammar.mp.size(); i > 0 ; i--) {
            Transfer transfer = new Transfer();
            transfer.column = t;
            transfer.row = currentGrammar.mp.subList(0, i);

            availableTransfers.add(transfer);
        }

        for (Transfer tr : currentGrammar.sortedTransfer) {
            if (availableTransfers.contains(tr)) {
                TransferType result = currentGrammar.transferTable.get(tr);
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

        return TransferType.ERROR;
    }

    public void transfer(Terminal t) {
        currentGrammar.mp.add(0, t);
        gr = new ArrayList<>(fullGrammars);
    }

    public void convolution() {
        for (Convolution c : currentGrammar.sortedConvolution) {
            if (currentGrammar.mp.size() <= c.convolution.size()) {
                continue;
            }
            Convolution newConv = new Convolution();
            newConv.prefix = currentGrammar.mp.get(c.convolution.size());
            newConv.convolution = reverse( currentGrammar.mp.subList(0, c.convolution.size()) );

            if (c.equals(newConv)) {
                Integer indexOfRule = currentGrammar.convolutionTable.get(c);
                currentGrammar.mp = currentGrammar.mp.subList(c.convolution.size(), currentGrammar.mp.size());
                currentGrammar.mp.add(0, currentGrammar.rules.get(indexOfRule).left);
                output.add(indexOfRule);
                System.out.println("CONVOLUTION. mp: " + currentGrammar.mp);
                convolution();
                return;
            }
        }
    }

    private List<Symbol> reverse(List<Symbol> list) {
        List<Symbol> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}


//// TODO: NEXT GRAMMAR
//
//if (currentGrammar.mp.size() > 0 && currentGrammar.mp.get(0).getClass() == NotTerminal.class) {
//        tokens.remove(0);
//        transfer(t);
//        } else  {
//
//
//        System.out.println("NEXT GRAMMAR. Tokens: " + tokens);
//        Grammar g = findNextGrammar(t);
//        if (g == null) {
//        System.out.println("NEXT GRAMMAR is NULL");
//        if (currentGrammar.mp.size() > 0 && currentGrammar.mp.get(0).getClass() == NotTerminal.class) {
//        tokens.remove(0);
//        transfer(t);
//        } else {
//        return;
//        }
////                        if (gr.size() == 0) {
//        return;
////                        }
//        } else {
//        stack.push(currentGrammar);
//        currentGrammar = g;
//        }
//        }