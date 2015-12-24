package com.github.oxaoo.codegen;

public enum EOpcode {
    BRL("BRL"),
    BF("BF"),
    DEFL("DEFL"),
    NEG("NEG"),
    MUL("MUL"),
    DIV("DIV"),
    MOD("MOD"),
    ADD("ADD"),
    SUB("SUB"),
    NOT("NOT"),
    AND("AND"),
    OR("OR"),
    GTS("GTS"),
    LTS("LTS"),
    NES("NES"),
    GETS("GETS"),
    LETS("LETS"),
    ES("ES"),
    MOV("MOV"),
    SUBS("SUBS"),
    INC("INC"),
    DEC("DEC"),
    ADDA("ADDA"),
    MULA("MULA"),
    DIVA("DIVA"),
    SUBA("SUBA"),
    MAIN("MAIN");

    private final String name;
    EOpcode(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return otherName != null && name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }


}
