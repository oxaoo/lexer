package com.github.oxaoo.lexer.syntax;
import java.util.*;

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
    public List<Symbol> right = new ArrayList<Symbol>();

    @Override
    public String toString() {
        StringBuilder rightSide = new StringBuilder();
        for (Symbol symbol : this.right) {
            rightSide.append(" " + symbol);
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
    TRANSFER, CONVOLUTION, ERROR, ACCESS;

    public static TransferType parse(String type) {
        if (type.equals("R")) {
            return TransferType.TRANSFER;
        } else if (type.equals("S")) {
            return TransferType.CONVOLUTION;
        } else if (type.equals("S")) {
            return TransferType.ACCESS;
        }

        return TransferType.ERROR;
    }
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

    @Override
    public String toString() {
        StringBuilder rightSide = new StringBuilder();
        for (Symbol symbol : row) {
            rightSide.append(" " + symbol);
        }
        return column + " [" + rightSide.toString() + " ]";
    }
}

/**
 * Created by dydus on 11/11/2015.
 */
public class Grammar {
    public static final Symbol emptyExpessionSymbol = new Symbol("$");
    public static final Symbol anySymbol = new Symbol("C");
    public static final Symbol emptyStackSymbol = new Symbol("#");

    public String name;
    public NotTerminal S;
    public List<Terminal> terminals = new ArrayList<Terminal>();
    public List<NotTerminal> notTerminals = new ArrayList<NotTerminal>();
    public List<Rule> rules = new ArrayList<Rule>();

    public Map<Convolution, Integer> convolutionTable = new HashMap<Convolution, Integer>();
    public List<Convolution> sortedConvolution = new ArrayList<Convolution>();

    public Map<Transfer, TransferType> transferTable = new HashMap<Transfer, TransferType>();
    public List<Transfer> sortedTransfer = new ArrayList<Transfer>();
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Terminals = [");
        for (Terminal t : terminals) {
            result.append(" " + t);
        }
        result.append(" ]");

        result.append("\r\nNotterminals = [");
        for (NotTerminal t : notTerminals) {
            result.append(" " + t);
        }
        result.append(" ]");

        result.append("\n\rS = " + S);

        result.append("\r\nRules\r\n");
        for (Rule r : rules) {
            result.append(r.toString() + "\r\n");
        }

        result.append("\r\nConvolution\r\n");
        for (Convolution r : sortedConvolution) {
            result.append(r + " : " + convolutionTable.get(r) + "\r\n");
        }

        result.append("\r\nTransfer\r\n");
        for (Transfer r : sortedTransfer) {
            result.append(r + " : " + transferTable.get(r) + "\r\n");
        }

        return result.toString();
    }
}