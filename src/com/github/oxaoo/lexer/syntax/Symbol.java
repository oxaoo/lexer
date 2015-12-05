package com.github.oxaoo.lexer.syntax;

/**
 * Created by dydus on 06/12/2015.
 */
public class Symbol {
    public final String id;

    Symbol(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        for (String type: Grammar.reservedTypes) {
            if (id.startsWith(type)) {
                return type.hashCode();
            }
        }
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj) {
            return true;
        }
        Symbol tr = (Symbol) obj;
        if (tr != null) {
            for (String type: Grammar.reservedTypes) {
                if (id.startsWith(type) && tr.id.startsWith(type)) {
                    return true;
                }
            }
            return tr.id.equals(this.id);
        }
        return false;
    }
}