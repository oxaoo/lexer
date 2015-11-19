package com.github.oxaoo.lexer.syntax;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dydus on 19/11/2015.
 */
public class Convolution {
    public Symbol prefix;
    public List<Symbol> convolution = new ArrayList<Symbol>();

    @Override
    public String toString() {
        StringBuilder rightSide = new StringBuilder();
        for (Symbol symbol : convolution) {
            rightSide.append(" " + symbol);
        }
        return prefix + " [" + rightSide.toString() + " ]";
    }

    @Override
    public int hashCode() {
        int result = prefix.hashCode() >> 1;
        for (Symbol symbol : this.convolution) {
            result += symbol.hashCode() << 1;
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj) {
            return true;
        }
        if (obj.getClass() == this.getClass()) {
            Convolution tr = (Convolution) obj;
            if (!tr.prefix.equals(this.prefix) || tr.convolution.size() != this.convolution.size()) {
                return false;
            }
            for (int i = 0; i < tr.convolution.size(); i++) {
                if (!tr.convolution.get(i).equals(this.convolution.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}