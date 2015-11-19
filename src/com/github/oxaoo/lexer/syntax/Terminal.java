package com.github.oxaoo.lexer.syntax;

/**
 * Created by dydus on 18/11/2015.
 */
public class Terminal extends Symbol {
    public Terminal(String id) {
        super(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }

        if (obj.getClass() == this.getClass()) {
            for (String type: Grammar.reservedTypes) {
                if (id.startsWith(type) && ((Terminal)obj).id.startsWith(type)) {
                    return true;
                }
            }
        }

        return false;
    }
}