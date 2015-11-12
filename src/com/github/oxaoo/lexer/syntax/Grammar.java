package com.github.oxaoo.lexer.syntax;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Symbol {
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
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj) {
            return true;
        }
        if (obj.getClass() == this.getClass()) {
            Symbol tr = (Symbol) obj;
            return tr.id == this.id;
        }
        return false;
    }
}

class Terminal extends Symbol {
    Terminal(String id) {
        super(id);
    }
}

class NotTerminal extends Symbol {
    NotTerminal(String id) {
        super(id);
    }
}

class Rule {
    public NotTerminal left;
    public List<Symbol> right;

    @Override
    public String toString() {
        StringBuilder rightSide = new StringBuilder();
        for (Symbol symbol : this.right) {
            rightSide.append(symbol);
        }
        return left + " ->" + rightSide.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj) {
            return true;
        }
        if (obj.getClass() == this.getClass()) {
            Rule tr = (Rule) obj;
            if (tr.left != this.left && tr.right.size() != this.right.size()) {
                return false;
            }
            for (int i = 0; i < tr.right.size(); i++) {
                if (tr.right.get(i) != this.right.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}


class Convolution {
    public Symbol prefix;
    public List<Symbol> convolution;

    @Override
    public String toString() {
        StringBuilder rightSide = new StringBuilder();
        for (Symbol symbol : convolution) {
            rightSide.append(symbol);
        }
        return prefix + " [" + rightSide.toString() + " ]";
    }

    @Override
    public int hashCode() {
        int result = prefix.hashCode();
        for (Symbol symbol : this.convolution) {
            result += symbol.hashCode();
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
            if (tr.prefix != this.prefix && tr.convolution.size() != this.convolution.size()) {
                return false;
            }
            for (int i = 0; i < tr.convolution.size(); i++) {
                if (tr.convolution.get(i) != this.convolution.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}


enum TransferType {
    TRANSFER, CONVOLUTION, ERROR, ACCESS
}

class Transfer {
    public List<Symbol> row = new ArrayList<Symbol>();
    public Terminal column;

    @Override
    public int hashCode() {
        int result = column.hashCode();
        for (Symbol symbol : this.row) {
            result += symbol.hashCode();
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
            Transfer tr = (Transfer) obj;
            if (tr.column != this.column && tr.row.size() != this.row.size()) {
                return false;
            }
            for (int i = 0; i < tr.row.size(); i++) {
                if (tr.row.get(i) != this.row.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

/**
 * Created by dydus on 11/11/2015.
 */
public class Grammar {
    public final Symbol emptySymbol = new Symbol("$");
    public final Symbol anySymbol = new Symbol("C");
    public String name;
    public List<Terminal> terminals = new ArrayList<Terminal>();
    public List<NotTerminal> notTerminals = new ArrayList<NotTerminal>();
    public List<Rule> rules = new ArrayList<Rule>();

    public Map<Convolution, Integer> convolutionTable = new HashMap<Convolution, Integer>();
    public Map<Transfer, TransferType> transferTable = new HashMap<Transfer, TransferType>();
}
