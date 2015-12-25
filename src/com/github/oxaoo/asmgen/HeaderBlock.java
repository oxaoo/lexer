package com.github.oxaoo.asmgen;

/**
 * Created by Никита on 24.12.2015.
 */
public class HeaderBlock implements IAssembleable {

    private final String ASM_HEADER = ".386\n" +
            ".model flat, stdcall\n" +
            "option casemap :none\n" +
            "include \\masm32\\include\\masm32.inc\n" +
            "include \\masm32\\include\\kernel32.inc\n" +
            "include \\masm32\\macros\\macros.asm\n" +
            "includelib \\masm32\\lib\\masm32.lib\n" +
            "includelib \\masm32\\lib\\kernel32.lib\n\n";

    @Override
    public String assemble() {
        return ASM_HEADER;
    }
}
