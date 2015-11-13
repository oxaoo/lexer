package com.github.oxaoo.lexer.syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dydus on 13/11/2015.
 */
public class SyntaxAnalizer {
    public List<Grammar> gr = new ArrayList<Grammar>();
    public void parseGrammars() {
        String path = "res/syntax/";
        GrammarParser parser = new GrammarParser();
        for (int i = 0; i <= 6; i++) {
            Grammar g = parser.parse(path + i + "/");
            gr.add(g);
            System.out.println(g.toString());
        }
    }

    public void parseTokens( List<Symbol> tokens) {

    }
}
