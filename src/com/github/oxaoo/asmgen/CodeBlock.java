package com.github.oxaoo.asmgen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Никита on 23.12.2015.
 */
public class CodeBlock extends AbstractAsmBlock implements IAssembleable {

    /**
     * only for Main proc
     * **/
    private Procedure mainProc = new Procedure("_MAIN");
    /**
     *  for other procs
     *  **/
    private List<Procedure> procList = new ArrayList<Procedure>();

    public CodeBlock() {
        super(".CODE");
    }

    public void addProcedure(String name,String code) {
        Procedure proc = new Procedure(name);
        proc.addCode(code);
        procList.add(proc);
    }

    public void setMainProcCode(String code) {
        mainProc.addCode(code);
    }

    @Override
    public String assemble() {
        StringBuilder code = new StringBuilder();
        code.append(blockSeparator + IAssembleable.ENDL);
        procList.forEach(proc -> {
            code.append(proc.assemble());
        });
        code.append(mainProc.assemble());
        code.append(Procedure.END_MAIN+TAB+mainProc.getProcName());
        return code.toString();
    }


    class Procedure implements IAssembleable{
        public static final String END_MAIN = "END";
        private final String START_POINTER = "PROC";
        private final String FINAL_POINTER = "ENDP";
        private String procName = "";
        private StringBuilder codeString = new StringBuilder();

        public Procedure(String name) {
            procName = name;
        }

        public String toString() {
            String result = procName + TAB + START_POINTER
                    + ENDL + codeString.toString() +ENDL
                    + procName + TAB + FINAL_POINTER  + ENDL;
            return result;
        }

        public void addCode(String code) {
            codeString.append(code);
        }

        @Override
        public String assemble() {
            return toString();
        }

        public String getProcName() {
            return procName;
        }
    }
}
