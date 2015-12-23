package com.github.oxaoo.codegen;

import com.github.oxaoo.lexer.CIO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Alexander on 23.12.2015.
 */
public class CGrammar {

    Set<String> grammarT[] = new Set[7];
    Set<String> grammarN[] = new Set[7];

    public CGrammar() {
        for (int i = 0; i < 7; i++) {
            grammarT[i] = new TreeSet<>();
            grammarN[i] = new TreeSet<>();
        }
    }

    public void loadGrammar() {

        for (int i = 0; i < 7; i++) {
            grammarT[i] = CIO.readGrammar("res/syntax/" + i + "/terminals");
            grammarN[i] = CIO.readGrammar("res/syntax/" + i + "/notterminals");
        }

        //System.out.println("GRAMMAR T[0]: " + grammarT[0].toString());
        //System.out.println("GRAMMAR N[0]: " + grammarN[0].toString());

    }
}
