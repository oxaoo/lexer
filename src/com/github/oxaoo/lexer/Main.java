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
        CIOFile iof = new CIOFile();
        char[] code = iof.readFile("code2.java");

        CLexer lexer = new CLexer(code);
        lexer.toScan();
    }
}
