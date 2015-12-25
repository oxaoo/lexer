package com.github.oxaoo.codegen;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CTetrad {

    private static int count = 0;

    public final EOpcode opcode;
    public final CTetrad operand1;
    public final CTetrad operand2;
    public final Object result;
    public final List<CTetrad> tetradList;

    private static Set<String> labels = new TreeSet<>();

    public CTetrad(EOpcode opcode, CTetrad operand1, CTetrad operand2, String result) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = operand2;
        if (result.equals(""))
            this.result = result;
        else
            this.result = "#" + result;

        tetradList = Collections.EMPTY_LIST;
    }

    public CTetrad(EOpcode opcode, CTetrad operand1, CTetrad operand2) {
        /*
        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = operand2;*/
        //String strId = String.valueOf(count);
        //count++;
        this(opcode, operand1, operand2, String.valueOf(++count));
    }

    public CTetrad(EOpcode opcode, CTetrad operand1, String result) {
/*
        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = null;
*/
        this(opcode, operand1, null, result);
    }

    public CTetrad(EOpcode opcode, CTetrad operand1) {

        this(opcode, operand1, null, "");
    }

    // for elem (NO for tetrad).
    public CTetrad(Object obj) {
        opcode = null;
        operand1 = null;
        operand2 = null;
        result = obj;

        tetradList = Collections.EMPTY_LIST;
    }

    public CTetrad(List<CTetrad> tetrads) {
        tetradList = tetrads;

        opcode = null;
        operand1 = null;
        operand2 = null;
        result = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        CTetrad cTetrad = (CTetrad) o;

        if (opcode != cTetrad.opcode)
            return false;

        if (operand1 == null || operand2 == null)
            return result.equals(cTetrad.result);

        return operand1.equals(cTetrad.operand1)
                && operand2.equals(cTetrad.operand2)
                && result.equals(cTetrad.result);

    }

    @Override
    public int hashCode()
    {
        int result1 = opcode.hashCode();
        //result1 = 31 * result1 + operand1.hashCode();
        //result1 = 31 * result1 + operand2.hashCode();
        result1 = 31 * result1 + result.hashCode();
        return result1;
    }

    @Override
    public String toString()
    {
        String strOp1 = "null";
        String strOp2 = "null";
        String strRes = "null";
        String strOpc = "null";
        if (operand1 != null) strOp1 = operand1.toString();
        if (operand2 != null) strOp2 = operand2.toString();
        if (result != null) strRes = result.toString();
        if (opcode != null) strOpc = opcode.toString();

        return "CTetrad{" +
                "opcode=" + strOpc +
                ", operand1='" + strOp1 + '\'' +
                ", operand2='" + strOp2 + '\'' +
                ", result='" + strRes + '\'' +
                '}';
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
}


