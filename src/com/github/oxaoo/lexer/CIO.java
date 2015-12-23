package com.github.oxaoo.lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Alexander on 01.11.2015.
 */
public class CIO
{
    public enum table {KEYWORD, MODIFIER, TYPE, UNARY, ADDIT, MULT, COMPARE, ASSIGN, LOGIC, OTHER, DELIM}

    public static char[] readFile(String path)
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        String line;

        try
        {
            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null)
            {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Exception while open the file: [" + e.toString() + "]");
        }
        catch (IOException e)
        {
            System.err.println("Exception while reading file: [" + e.toString() + "]");
        }
        finally
        {
            if (br != null) try
            {
                br.close();
            }
            catch (IOException e)
            {
                System.err.println("Exception while closing the file: [" + e.toString() + "]");
            }
        }

        if (sb != null)
            return sb.toString().toCharArray();
        else
            return null;
    }

    public static Map<String, String> readTable(table tab)
    {
        switch (tab)
        {
            case KEYWORD:
                return read("res/keyword.csv", true);

            case MODIFIER:
                return read("res/modifier.csv", false);

            case TYPE:
                return read("res/type.csv", false);

            case UNARY:
                return read("res/unary.csv", false);

            case ADDIT:
                return read("res/addit.csv", false);

            case MULT:
                return read("res/mult.csv", false);

            case COMPARE:
                return read("res/compare.csv", false);

            case ASSIGN:
                return read("res/assign.csv", false);

            case LOGIC:
                return read("res/logic.csv", false);

            case OTHER:
                return read("res/other.csv", true);

            case DELIM:
                return read("res/delim.csv", true);

        }
        return null;
    }

    private static Map<String, String> read(String path, boolean pair)
    {
        Map<String, String> table = new HashMap<String, String>();
        BufferedReader br = null;
        String line;

        try
        {
            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null)
            {
                String[] par = line.split(";");

                if (pair)
                {
                    if (par[0].equals("SCOLON")) //fitch.
                        table.put(";", par[0]);
                    else
                        table.put(par[1], par[0]);
                }
                else
                    table.put(par[2], par[0] + par[1]);
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Exception while open the file: [" + e.toString() + "]");
        }
        catch (IOException e)
        {
            System.err.println("Exception while reading file: [" + e.toString() + "]");
        }
        finally
        {
            if (br != null) try
            {
                br.close();
            }
            catch (IOException e)
            {
                System.err.println("Exception while closing the file: [" + e.toString() + "]");
            }
        }

        return table;
    }


    public static Set<String> readGrammar(String path)
    {
        Set<String> elems = new TreeSet<String>();
        BufferedReader br = null;
        String line;

        try
        {
            br = new BufferedReader(new FileReader(path));

            int iter = 0;
            while ((line = br.readLine()) != null && iter < 1)
            {
                line.replaceAll(" ", "");
                elems.addAll(Arrays.asList(line.split(",")));
                iter++;
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Exception while open the file: [" + e.toString() + "]");
        }
        catch (IOException e)
        {
            System.err.println("Exception while reading file: [" + e.toString() + "]");
        }
        finally
        {
            if (br != null) try
            {
                br.close();
            }
            catch (IOException e)
            {
                System.err.println("Exception while closing the file: [" + e.toString() + "]");
            }
        }

        return elems;
    }
}

