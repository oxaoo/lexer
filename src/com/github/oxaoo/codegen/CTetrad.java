package com.github.oxaoo.codegen;

public class CTetrad {

    private static int count = 0;

    private final EOpcode opcode;
    private final CTetrad operand1;
    private final CTetrad operand2;
    private final String result;

    public CTetrad(EOpcode opcode, CTetrad operand1, CTetrad operand2) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = operand2;
        result = String.valueOf(count);
        count++;
    }

    public CTetrad(EOpcode opcode, CTetrad operand1, boolean isRes) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = null;

        if (isRes) {
            result = String.valueOf(count);
            count++;
        }
        else
            result = "";
    }

    // for elem (NO for tetrad).
    public CTetrad(String str) {
        opcode = null;
        operand1 = null;
        operand2 = null;
        result = str;
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
        if (operand1 != null) strOp1 = operand1.toString();
        if (operand2 != null) strOp2 = operand2.toString();

        return "CTetrad{" +
                "opcode=" + opcode.toString() +
                ", operand1='" + strOp1 + '\'' +
                ", operand2='" + strOp2 + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}


