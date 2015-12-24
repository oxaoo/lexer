package com.github.oxaoo.asmgen;

/**
 * Created by Никита on 23.12.2015.
 */
public abstract class AbstractAsmBlock {

    protected String blockSeparator;

    public AbstractAsmBlock(String blockSeparator) {
        this.blockSeparator = blockSeparator;
    }
}
