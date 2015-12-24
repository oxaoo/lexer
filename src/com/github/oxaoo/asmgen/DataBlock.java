package com.github.oxaoo.asmgen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Никита on 23.12.2015.
 */
public class DataBlock extends AbstractAsmBlock implements IAssembleable {

    private List<DataStructure> dataList = new ArrayList<DataStructure>();

    public DataBlock() {
        super(".DATA");
    }

    public void addDataStructure(String name, String type, String val) {
        DataStructure data = new DataStructure(name, type, val);
        dataList.add(data);
    }

    @Override
    public String assemble() {
        StringBuilder code = new StringBuilder();
        code.append(blockSeparator + ENDL);
        dataList.forEach(data -> {
            code.append(data.assemble());
        });
        code.append(ENDL);
        return code.toString();
    }

    class DataStructure implements  IAssembleable{
        private String type;
        private String name;
        private String value;

        public DataStructure(String name, String type, String value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }

        @Override
        public String assemble() {
            return TAB+name+TAB+type+TAB+value;
        }
    }

}
