package com.github.oxaoo.lexer.syntax;

import com.sun.xml.internal.xsom.impl.Ref;

import java.util.ArrayList;
import java.util.List;

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
            gr.add(g);
            System.out.println(g.toString());
        }
    }

    public void parseTokens( List<Terminal> tokens) {
        this.mp.add(Grammar.emptyStackSymbol);
        System.out.print(tokens.toString());

        currentGrammar = this.gr.get(0);
        while (tokens.size() > 1) {
            Terminal t = tokens.get(0);
            TransferType type =  typeOfTransfer(t);
            if (type == TransferType.ERROR) {
                if (isCurrentGrammarValid()) {
                    System.out.print("ERROR. Tokens: " + tokens + " \n\rGrammar: " + gr.toString());
                    return;
                } else  {
                    System.out.print("NEXT GRAMMAR. Tokens: " + tokens + " \n\rGrammar: " + gr.toString());
                    findNextGrammar(t);
                }
            } else if (type == TransferType.ACCESS) {
                System.out.print("ACCESS. Tokens: " + tokens + " \\n\\rGrammar: " + gr.toString());
                return;
            } else if (type == TransferType.TRANSFER) {
                System.out.print("TRANSFER. Tokens: " + tokens + " \n\rGrammar: " + gr.toString());
                transfer(t);
            } else if (type == TransferType.CONVOLUTION) {
                System.out.print("TRANSFER. Tokens: " + tokens + " \n\rGrammar: " + gr.toString());
                transfer(t);
            }
        }
    }


    public Grammar findNextGrammar(Terminal t) {
        for (Grammar g : this.gr) {
            if (g.terminals.contains(t)) {
                return g;
            }
        }
        return null;
    }


    public TransferType typeOfTransfer(Terminal t) {
        for (int i = mp.size() - 1; i > 0 ; i--) {
            Transfer transfer = new Transfer();
            transfer.column = t;
            transfer.row = mp.subList(0, i);

            TransferType result = currentGrammar.transferTable.get(transfer);
        }

        return TransferType.ERROR;
    }

    public void transfer(Terminal t) {
        mp.add(0, t);
    }

    public Boolean isCurrentGrammarValid() {


        return false;
    }
}
