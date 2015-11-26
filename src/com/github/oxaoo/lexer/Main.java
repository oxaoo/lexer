package com.github.oxaoo.lexer;

import com.github.oxaoo.lexer.syntax.Grammar;
import com.github.oxaoo.lexer.syntax.SyntaxAnalizer;
import com.github.oxaoo.lexer.syntax.Terminal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 01.11.2015.
 *
 * ���������: ����������� ����������
 * ��������: ���������� �������� ���������
 * �������: 9
 */

public class Main
{
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("*** Start lexer ***");
        String filename = "res/code3.java";
        if (args.length > 0)
            filename = args[0];

        char[] code = CIO.readFile(filename);

        CLexer lexer = new CLexer(code);
        lexer.toScan();
        lexer.toEstimate();
        lexer.result(filename.split("\\.")[0]);

        try {
            BufferedReader br = new BufferedReader(new FileReader("res/code3.t"));
            String line;
            List<Terminal> tokens = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                tokens.add(new Terminal(line.toLowerCase()));
            }
            tokens.add(Grammar.emptyExpessionSymbol);

            SyntaxAnalizer sa = new SyntaxAnalizer();
            sa.parseGrammars();
            sa.parseTokens(tokens);
            br.close();
        } catch(IOException e){}
    }
}
