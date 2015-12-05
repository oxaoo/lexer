package com.github.oxaoo.lexer.syntax;
import com.github.oxaoo.parser.CSyntaxTreeNode;

import java.util.*;

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
    TRANSFER, CONVOLUTION, ERROR, ACCESS, MOVING;

    public int nextGrammar = -1;
    public static TransferType parse(String type) {
        if (type.equals("S")) {
            return TransferType.TRANSFER;
        } else if (type.equals("R")) {
            return TransferType.CONVOLUTION;
        } else if (type.equals("A")) {
            return TransferType.ACCESS;
        }
//        else {
//            try {
//                int next = Integer.parseInt(type);
//                TransferType result = TransferType.MOVING;
//                result.nextGrammar = next;
//                return result;
//            } catch (Exception e) {}
//        }

        return TransferType.ERROR;
    }
}

class Transfer {
    public List<Symbol> row = new ArrayList<Symbol>();
    public Terminal column;
    public boolean isSkipNonterm = true;
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
            List<Symbol> right = new ArrayList<>(tr.row);
            if (this.isSkipNonterm) {
                for (ListIterator iterator = right.listIterator(right.size()); iterator.hasPrevious();) {
                    Symbol s = (Symbol) iterator.previous();
                    if (s instanceof NotTerminal) {
                        iterator.remove();
                    } else {
                        break;
                    }
                }
            }

            if (!tr.column.equals(this.column) || right.size() != this.row.size()) {
                return false;
            }

            for (int i = 0; i < right.size(); i++) {
                if (!this.row.get(i).equals(right.get(i))) {
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
    public static List<String> reservedTypes = Arrays.asList("id", "type", "int", "real", "bool", "modifier", "assign", "addit", "logic", "mult", "compare");
    public static final Terminal emptyExpessionSymbol = new Terminal("$");
    public static final Symbol anySymbol = new Symbol("C");
    public static final Symbol emptyStackSymbol = new Symbol("#");

    public Integer name = 0;
    public NotTerminal S;
    public List<Terminal> terminals = new ArrayList<>();
    public List<NotTerminal> notTerminals = new ArrayList<>();
    public List<Rule> rules = new ArrayList<>();

    public List<Convolution> sortedConvolution = new ArrayList<>();
    public List<Integer> convolutionValue = new ArrayList<>();

    public List<Transfer> sortedTransfer = new ArrayList<>();
    public List<TransferType> transferValue = new ArrayList<>();

    public Map<Symbol, Integer> nextGrammars = new HashMap<>();
    public List<Symbol> mp = new ArrayList<>();
    public List<CSyntaxTreeNode> nodes = new ArrayList<>();

    Grammar() {
        this.mp.add(Grammar.emptyStackSymbol);
    }

    Grammar(Grammar g) {
        name = g.name;
        S = g.S;
        terminals = g.terminals;
        notTerminals = g.notTerminals;
        rules = g.rules;
        transferValue = g.transferValue;
        sortedConvolution = g.sortedConvolution;
        convolutionValue = g.convolutionValue;
        sortedTransfer = g.sortedTransfer;
        nextGrammars = g.nextGrammars;
        nodes = new ArrayList<>();
        mp = new Stack<>();
        mp.add(Grammar.emptyStackSymbol);
    }

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
        result.append(sortedConvolution + "\n");
        result.append(convolutionValue + "\n");

        result.append("\r\nTransfer\r\n");
        result.append(sortedTransfer + "\n");
        result.append(transferValue + "\n");

        return result.toString();
    }
}