package com.github.oxaoo.lexer.syntax;

import com.sun.xml.internal.xsom.impl.Ref;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by dydus on 13/11/2015.
 */
public class SyntaxAnalizer {
    private Grammar currentGrammar;
    public List<Grammar> gr = new ArrayList<Grammar>();
    public List<Symbol> mp = new ArrayList<>();
    public List<Integer> output = new ArrayList<>();

    public void parseGrammars() {
        String path = "res/syntax/";
        GrammarParser parser = new GrammarParser();
        for (int i = 0; i <= 6; i++) {
            Grammar g = parser.parse(path + i + "/");
            g.name = i;
            gr.add(g);
            System.out.println(g.toString());
        }
    }

    public void parseTokens( List<Terminal> tokens) {
        this.mp.add(Grammar.emptyStackSymbol);
        System.out.println(tokens.toString());

        currentGrammar = this.gr.get(0);
        System.out.println("START PARSING");
        while (tokens.size() > 1) {
            Terminal t = tokens.get(0);
            TransferType type = typeOfTransfer(t);
            if (type == TransferType.ERROR) {
                if (isCurrentGrammarValid(t)) {
                    System.out.println("ERROR. Tokens: " + tokens);
                    return;
                } else  {
                    System.out.println("NEXT GRAMMAR. Tokens: " + tokens);
                    currentGrammar = findNextGrammar(t);
                    if (currentGrammar == null) {
                        System.out.println("NEXT GRAMMAR is NULL");
                        return;
                    }
                }
            } else if (type == TransferType.ACCESS) {
                System.out.println("ACCESS. Tokens: " + tokens);
                return;
            } else if (type == TransferType.TRANSFER) {
                tokens.remove(0);
                transfer(t);
                System.out.println("TRANSFER. Tokens: " + tokens);
            } else if (type == TransferType.CONVOLUTION) {
                System.out.println("START CONVOLUTION mp: " + mp);
                convolution();
            }
        }
        System.out.println("STOP PARSING");
    }


    public Grammar findNextGrammar(Terminal t) {
        for (Grammar g : this.gr) {
            if (g.terminals.contains(t) && g != currentGrammar) {
                return g;
            }
        }
        return null;
    }


    public TransferType typeOfTransfer(Terminal t) {
        List<Transfer> availableTransfers = new ArrayList<>();
        for (int i = mp.size(); i > 0 ; i--) {
            Transfer transfer = new Transfer();
            transfer.column = t;
            transfer.row = mp.subList(0, i);

            availableTransfers.add(transfer);
        }

        for (Transfer tr : currentGrammar.sortedTransfer) {
            if (availableTransfers.contains(tr)) {
                TransferType result = currentGrammar.transferTable.get(tr);
                return result;
            }
        }

        return TransferType.ERROR;
    }

    public void transfer(Terminal t) {
        mp.add(0, t);
    }

    public void convolution() {
//        List<Convolution> availableConvolutions = new ArrayList<>();
//        List<Symbol> reversed = reverse(mp);
//        for (int i = 1; i < reversed.size() - 1; i++) {
//            Convolution newConv = new Convolution();
//            newConv.prefix = mp.get(i+1);
//            newConv.convolution = mp.subList(0, i);
//            availableConvolutions.add(newConv);
//        }

        for (Convolution c : currentGrammar.sortedConvolution) {
            if (mp.size() < c.convolution.size()) {
                continue;
            }
            Convolution newConv = new Convolution();
            newConv.prefix = mp.get(c.convolution.size());
            newConv.convolution = reverse(mp.subList(0, c.convolution.size()));

            if (c.equals(newConv)) {
                Integer indexOfRule = currentGrammar.convolutionTable.get(c);
                mp = mp.subList(c.convolution.size(), mp.size());
                mp.add(0, currentGrammar.rules.get(indexOfRule).left);
                output.add(indexOfRule);
                System.out.println("CONVOLUTION. mp: " + mp);
                convolution();
                return;
            }
        }
    }

    public Boolean isCurrentGrammarValid(Terminal t) {
        if (!currentGrammar.terminals.contains(t)) {
            return false;
        }

        for (Symbol s : mp) {
            if (!currentGrammar.terminals.contains(s) ||
                    !currentGrammar.notTerminals.contains(s) ||
                    !Grammar.emptyStackSymbol.equals(s)) {
                return false;
            }
        }

        return true;
    }

    private List<Symbol> reverse(List<Symbol> list) {
        List<Symbol> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}
