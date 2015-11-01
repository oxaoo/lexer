package com.github.oxaoo.lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Alexander on 01.11.2015.
 */
public class CIOFile
{
    public char[] readFile(String path)
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
}

