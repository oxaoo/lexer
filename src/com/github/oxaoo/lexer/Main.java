package com.github.oxaoo.lexer;

/**
 * Created by Alexander on 01.11.2015.
 *
 * Программа: Лексический анализатор
 * Предметр: Разработка языковых процессов
 * Семестр: 9
 */

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("*** Start lexer ***");
        String filename = "res/code3.java";
        if (args.length > 0)
            filename = args[0];

        char[] code = CIO.readFile(filename);

        CLexer lexer = new CLexer(code);
        lexer.toScan();
        lexer.toEstimate();
        lexer.result(filename.split("\\.")[0]);
    }
}
