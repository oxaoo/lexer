package com.github.oxaoo.asmgen;

/**
 * Created by Никита on 23.12.2015.
 */
public interface ICodeGenerator {
    /**
     * Generate result asm source code
     * @param filename - asm listing file
     */
    void generate(String filename) throws Exception;
}
