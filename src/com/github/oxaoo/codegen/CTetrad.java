package com.github.oxaoo.codegen;

import java.util.*;

public class CTetrad extends CTetradObject {

    private static int count = 0;
    private static final List<CTetrad> tetrads = new ArrayList<>();

    public final EOpcode opcode;
    public final CTetradObject operand1;
    public final CTetradObject operand2;
    public final Object result;
    //public final List<CTetrad> tetradList;

    private static Set<String> labels = new TreeSet<>();

    /*
        for full-tetrad.
     */
    public CTetrad(EOpcode opcode, CTetradObject operand1, CTetradObject operand2, Object result) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
        //tetradList = Collections.EMPTY_LIST;
        tetrads.add(this);
    }

    /*
        for type or value.
    */
    public CTetrad(String result) {

        opcode = EOpcode.DEFAULT;
        operand1 = new CTetradObject();
        operand2 = new CTetradObject();

        Object res;
        try{
            res = Integer.parseInt(result);
        } catch (NumberFormatException e) {
            //System.err.println("Parsing exception: " + e.toString());

            try {
                res = Double.parseDouble(result);
            } catch (NumberFormatException e2) {
                //System.err.println("Parsing exception: " + e.toString());

                res = result;
            }
        }

        this.result = res;
        tetrads.add(this);

        //this.result = result;
        //tetradList = Collections.EMPTY_LIST;
    }

    /*
        for mov operate code with set variable.
     */
    public CTetrad(EOpcode opcode, CTetradObject operand1, String var) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = new CTetradObject();
        result = "#" + var;
        //tetradList = Collections.EMPTY_LIST;
        tetrads.add(this);
    }

    /*
        for mov operate code (temp variable).
     */
    public CTetrad(EOpcode opcode, CTetradObject operand1) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = new CTetradObject();
        result = "_" + ++count;
        //tetradList = Collections.EMPTY_LIST;
        tetrads.add(this);
    }

    public CTetrad(EOpcode opcode, CTetradObject operand1, CTetradObject operand2) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = operand2;
        result = "_" + ++count;
        tetrads.add(this);
    }

    /*
        for default init.
     */
    public CTetrad() {

        opcode = EOpcode.DEFAULT;
        operand1 = new CTetradObject();
        operand2 = new CTetradObject();
        this.result = 0;
        //tetradList = Collections.EMPTY_LIST;
        tetrads.add(this);
    }

    /*
        for list of tetrad.
     */
    public CTetrad(List<CTetradObject> tetradlist) {

        opcode = EOpcode.DEFAULT;
        operand1 = new CTetradObject();
        operand2 = new CTetradObject();
        result = tetradlist;
        //this.result = 0;
        //tetradList = Collections.EMPTY_LIST;
        tetrads.add(this);
    }

    /*
        for matrix IxJ.
     */
    public CTetrad(double[][] matrix) {

        opcode = EOpcode.DEFAULT;
        operand1 = new CTetradObject();
        operand2 = new CTetradObject();
        this.result = matrix;
        tetrads.add(this);
    }

    public static CTetradObject getTetrad(String id) {

        for (CTetrad tetrad : tetrads) {
            Object res = tetrad.result;
            if (res instanceof String) {
                String strRes = (String) res;
                if (strRes.equals(id))
                    return tetrad;
            }
        }

        return null;
    }

    public static String getNewLabel(String partLabel) {

        int lastIndex = 0;
        for(String label : labels) {
            if (label.startsWith(partLabel))
                lastIndex = Integer.valueOf(label.substring(partLabel.length()));
        }

        labels.add(partLabel + lastIndex);

        return partLabel + lastIndex;
    }


    @Override
    public String toString() {
        return "CTetrad{" +
                "opcode=" + opcode.toString() +
                ", operand1=" + operand1.toString() +
                ", operand2=" + operand2.toString() +
                ", result=" + result +
                //", tetradList=" + tetradList.toString() +
                '}';
    }

    //@Override
    public static String toStringFormat(CTetradObject to, int depth) {

        CTetrad t;

        try {
            t = (CTetrad) to;
        } catch (ClassCastException e) {
            return "{}";
        }
        /*
        if ( to != null && to instanceof CTetradObject)
            return "{}";*/

        //t = (CTetrad) to;

        String tab = "";
        for (int i = 0; i < depth; i++) tab += '\t';

        //String str = tab + "CTetrad\n" + tab + "{\n";
        String str = "CTetrad\n" + tab + "{\n";
        str += tab + "\topcode=" + t.opcode.toString() + ",\n";
        str += tab + "\toperand1=" + toStringFormat(t.operand1, depth + 1) + ",\n";
        str += tab + "\toperand2=" + toStringFormat(t.operand2, depth + 1) + ",\n";

        if (t.result instanceof List) {

            str += tab + "\tresult=\n" + tab + "\t[";
            List<CTetradObject> listResult = (List<CTetradObject>) t.result;
            for (CTetradObject toCur : listResult)
                str += "\n" + tab + "\t\t" + toStringFormat(toCur, depth + 2) + ",";
            str += "\n" + tab + "\t]";
        } else
            str += tab + "\tresult=" + t.result.toString() + "\n";

        if (depth == 0)
            str += "\n}";
        else
            str += tab + "}";

        return str;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CTetrad cTetrad = (CTetrad) o;

        if (opcode != cTetrad.opcode) return false;
        if (!operand1.equals(cTetrad.operand1)) return false;
        if (!operand2.equals(cTetrad.operand2)) return false;
        return result.equals(cTetrad.result);

    }

    @Override
    public int hashCode() {
        int result1 = opcode.hashCode();
        result1 = 31 * result1 + operand1.hashCode();
        result1 = 31 * result1 + operand2.hashCode();
        result1 = 31 * result1 + result.hashCode();
        return result1;
    }

}
