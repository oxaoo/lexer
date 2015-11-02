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
        System.out.println("Start");
        String filename = "res/code2.java";
        char[] code = CIO.readFile(filename);

        CLexer lexer = new CLexer(code);
        lexer.toScan();
        lexer.toEstimate();
        lexer.result(filename.split("\\.")[0]);
    }
}
