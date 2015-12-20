package com.github.oxaoo.codegen;

public class CTetrad {

    private static int count = 0;

    private final EOpcode opcode;
    private final String operand1;
    private final String operand2;
    private final String result;

    public CTetrad(EOpcode opcode, String operand1, String operand2) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = operand2;
        result = String.valueOf(count);
        count++;
    }

    public CTetrad(EOpcode opcode, String operand1, boolean isRes) {

        this.opcode = opcode;
        this.operand1 = operand1;
        this.operand2 = "";

        if (isRes) {
            result = String.valueOf(count);
            count++;
        }
        else
            result = "";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CTetrad cTetrad = (CTetrad) o;

        if (opcode != cTetrad.opcode) return false;
        if (!operand1.equals(cTetrad.operand1)) return false;
        if (!operand2.equals(cTetrad.operand2)) return false;
        return result.equals(cTetrad.result);

    }

    @Override
    public int hashCode()
    {
        int result1 = opcode.hashCode();
        result1 = 31 * result1 + operand1.hashCode();
        result1 = 31 * result1 + operand2.hashCode();
        result1 = 31 * result1 + result.hashCode();
        return result1;
    }

    @Override
    public String toString()
    {
        return "CTetrad{" +
                "opcode=" + opcode.toString() +
                ", operand1='" + operand1 + '\'' +
                ", operand2='" + operand2 + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}


