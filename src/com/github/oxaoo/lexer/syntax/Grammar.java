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
            if (!tr.left.equals(this.left) || tr.right.size() != this.right.size()) {
                return false;
            }
            for (int i = 0; i < tr.right.size(); i++) {
                if (!tr.right.get(i).equals(this.right.get(i))) {
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
        if (type.equals("S")) {
            return TransferType.TRANSFER;
        } else if (type.equals("R")) {
            return TransferType.CONVOLUTION;
        } else if (type.equals("A")) {
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
        int result = column.hashCode() >> 1;
        for (Symbol symbol : this.row) {
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
            Transfer tr = (Transfer) obj;
            if (!tr.column.equals(this.column) || tr.row.size() != this.row.size()) {
                return false;
            }

            for (int i = 0; i < tr.row.size(); i++) {
                if (!tr.row.get(i).equals(this.row.get(i))) {
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
    public static List<String> reservedTypes = Arrays.asList("id", "type", "int", "real", "bool");
    public static final Terminal emptyExpessionSymbol = new Terminal("$");
    public static final Symbol anySymbol = new Symbol("C");
    public static final Symbol emptyStackSymbol = new Symbol("#");

    public Integer name = 0;
    public NotTerminal S;
    public List<Terminal> terminals = new ArrayList<>();
    public List<NotTerminal> notTerminals = new ArrayList<>();
    public List<Rule> rules = new ArrayList<>();

    public Map<Convolution, Integer> convolutionTable = new HashMap<>();
    public List<Convolution> sortedConvolution = new ArrayList<>();

    public Map<Transfer, TransferType> transferTable = new HashMap<>();
    public List<Transfer> sortedTransfer = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Terminals = ");
        result.append(terminals.toString());

        result.append("\r\nNotterminals = ");
        result.append(notTerminals.toString());

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