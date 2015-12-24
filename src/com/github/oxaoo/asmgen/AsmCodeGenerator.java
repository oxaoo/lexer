package com.github.oxaoo.asmgen;

import java.io.PrintWriter;

/**
 * Created by Никита on 23.12.2015.
 */
public class AsmCodeGenerator implements ICodeGenerator {

    private static final String DESCRIPTION = ";RESULT OF ASSEMBLY\n";
    private HeaderBlock headerBlock = new HeaderBlock();
    private DataBlock dataBlock = new DataBlock();
    private CodeBlock codeBlock = new CodeBlock();


    @Override
    public void generate(String filename) throws Exception {
        PrintWriter fileWriter = new PrintWriter(filename, "UTF-8");
        parseCodeGen();
        fileWriter.println(DESCRIPTION);
        fileWriter.println(headerBlock.assemble());
        fileWriter.println(dataBlock.assemble());
        fileWriter.println(codeBlock.assemble());
        fileWriter.close();
    }

    private void parseCodeGen(/** code gen sequence **/) throws Exception {
        /**
         * for test purposes only
         */
        dataBlock.addDataStructure("MESSAGE", "DB", "\"MESSAGE EXAMPLE!\",0");
        codeBlock.setMainProcCode(";-----------------------start----------------------------\n" +
                "    push offset MESSAGE\n" +
                "    call StdOut\n" +
                "    RET\n" +
                " ;------------------end routine---------------------------  \n" +
                " exit:\n" +
                "    push 0\n" +
                "    call ExitProcess \n");
    }
}
