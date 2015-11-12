package com.github.oxaoo.lexer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Alexander on 01.11.2015.
 */
public class CLexer
{
    private enum status
    {
        GOOD, ERROR, END
    }

    private char[] m_text = null;
    private int m_index = 0;

    private Set<Character> m_CNum = new HashSet<Character>(10);
    private Set<Character> m_CChar = new HashSet<Character>(53);
    private Set<Character> m_CSep = new HashSet<Character>(9);
    private Set<Character> m_COper = new HashSet<Character>(6);
    private Set<Character> m_CLog = new HashSet<Character>(4);
    private Set<Character> m_CAddit = new HashSet<Character>(2);

    private List<String> m_letters = new ArrayList<String>(100);
    private StringBuilder m_buffer = new StringBuilder();

    private List<String> m_tokens = new ArrayList<String>(100);
    private List<CId> m_tabId = new ArrayList<CId>(20);
    private List<CConst> m_tabConst = new ArrayList<CConst>(20);

    public CLexer(char[] text)
    {
        //заполнение лексем класса "Символ".
        for (int c = 65; c < 123; c++)
        {
            if (c > 90 && c < 97 && c != 95)
                continue;

            m_CChar.add((char) c);
        }

        //заполнение лексем класса "Цифра".
        for (int n = 48; n < 58; n++)
        {
            m_CNum.add((char) n);
        }

        //заполнение лексем класса "Разделители".
        Collections.addAll(m_CSep, '.', ',', ';', '(', ')', '[', ']', '{', '}');

        //заполнение лексем класса "Оператор".
        Collections.addAll(m_COper, '!', '=', '<', '>', '/', '*');

        //заполнение лексем класса "Логические".
        Collections.addAll(m_CLog, '&', '|', ':', '?');

        //заполнение лексем класса "Аддитивный".
        Collections.addAll(m_CAddit, '+', '-');

        //удаление излишних символов.
        removeExcess(text);
    }

    //1 этап - сканирование текста.
    public void toScan()
    {
        if (m_text.length == 0)
            return;

        status st = status.END;

        //char lastLetter = '~';

        do
        {
            st = stateStart();

            if (st == status.GOOD)
            {
                m_letters.add(m_buffer.toString());
                m_buffer.setLength(0);

                char lastLetter = m_text[m_index - 1];
                char curLetter = m_text[m_index];

                //gr1, gr2, gr3, gr4.
                if ((m_CNum.contains(lastLetter) || m_CChar.contains(lastLetter)) && (m_CNum.contains(curLetter) || m_CChar.contains(curLetter)))
                {
                    System.err.println("Inadequate letter: '" + curLetter + "'");
                    m_index++;
                    continue;
                }

                //gr6.
                if (m_CLog.contains(lastLetter) && m_CLog.contains(curLetter))
                {
                    System.err.println("Inadequate letter: '" + curLetter + "'");
                    m_index++;
                    continue;
                }

                continue;
            }

            if (st == status.ERROR)
            {
                if (m_buffer.length() > 0)
                {
                    System.err.println("Lexical error code: [" + m_buffer.toString() + "]");
                    m_buffer.setLength(0);
                    continue;
                }
                //System.err.println("Can not recognize the lexeme: [" + m_buffer + "]");
                if (m_text[m_index++] == ' ')
                    continue;
                else
                {
                    System.err.println("The lexical analyzer does not recognize the code: ");
                    for (int i = -1; i + m_index < m_text.length && i < 10; i++)
                        System.err.print(m_text[i + m_index]);
                    System.err.print("...\n");
                    break;
                }
            }
        }
        while(st != status.END);

        System.out.println("*** Result scan ***");
        int i = 1;
        for (String let : m_letters)
            System.out.println("#" + (i++) + ": " + let);
    }

    private status stateStart()
    {
        if (m_index >= m_text.length)
            return status.END;

        if (m_CNum.contains(m_text[m_index]))
            return stateNorf();

        if (m_text[m_index] == '-')
            return stateEqordec();

        if (m_text[m_index] == '+')
            return stateEqorinc();

        if (m_COper.contains(m_text[m_index]))
            return stateEq();

        if (m_CSep.contains(m_text[m_index]))
            return stateSep();

        if (m_CChar.contains(m_text[m_index]))
            return stateId();

        if (m_CLog.contains(m_text[m_index]))
            return stateLog();

        return status.ERROR;
    }

    private status stateId()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_CNum.contains(m_text[m_index])
                || m_CChar.contains(m_text[m_index]))
            return stateId();

        if (m_text[m_index] == '.')
            return stateDot();

        return status.GOOD;
    }

    private status stateDot()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_CChar.contains(m_text[m_index]))
            return stateVar();

        return status.ERROR;
    }

    private status stateVar()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_CNum.contains(m_text[m_index])
                || m_CChar.contains(m_text[m_index]))
            return stateVar();

        if (m_text[m_index] == '.')
            return stateDot();

        return status.GOOD;
    }

    private status stateLog()
    {
        if (m_index >= m_text.length)
            return status.END;

        char temp = m_text[m_index];
        m_buffer.append(m_text[m_index++]);

        if (temp == '?' || temp == ':')
            return status.GOOD;

        if (m_text[m_index] == '&' && temp == '&'
                || m_text[m_index] == '|' && temp == '|')
            return state2();

        return status.ERROR;
    }

    private status state2()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        return status.GOOD;
    }


    private status stateSep()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        return status.GOOD;
    }

    private status stateEq()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_text[m_index] == '=')
            return stateEnd();

        return status.GOOD;
    }

    private status stateEqorinc()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_text[m_index] == '=' || m_text[m_index] == '+')
            return stateEnd();

        return status.GOOD;
    }

    private status stateEqordec()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_CNum.contains(m_text[m_index]))
            return stateNorf();

        if (m_text[m_index] == '=' || m_text[m_index] == '-')
            return stateEnd();

        return status.GOOD;
    }

    private status stateEnd()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        return status.GOOD;
    }

    private status stateNorf()
    {
        m_buffer.append(m_text[m_index++]);

        if (m_CNum.contains(m_text[m_index]))
            return stateNum();

        if (m_text[m_index] == '.')
            return stateFrac();

        return status.GOOD;
    }

    private status stateNum()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_CNum.contains(m_text[m_index]))
            return stateNum();

        if (m_text[m_index] == '.')
            return stateFrac();

        return status.GOOD;
    }

    private status stateFrac()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_CNum.contains(m_text[m_index]))
            return stateFnum();

        return status.ERROR;
    }

    private status stateFnum()
    {
        if (m_index >= m_text.length)
            return status.END;

        m_buffer.append(m_text[m_index++]);

        if (m_CNum.contains(m_text[m_index]))
            return stateFnum();

        return status.GOOD;
    }

    public void toEstimate()
    {

        Map<String, String>[] tables = new Map[11];
        int i = 0;

        //загрузка таблиц лексем.
        for (CIO.table t : CIO.table.values())
            tables[i++] = CIO.readTable(t);

        for (String letter: m_letters)
        {
            String token = null;

            for (int t = 0; t < tables.length; t++)
            {
                if (tables[t].containsKey(letter))
                {
                    token = tables[t].get(letter);
                    m_tokens.add(token);
                    break;
                }
            }

            if (token == null)
            {
                if (CConst.isType(letter) != null)
                {
                    //константа.
                    CConst con;
                    int index = m_tabConst.indexOf(new CConst(letter, false));
                    if (index < 0)
                    {
                        con = new CConst(letter);
                        m_tabConst.add(con);
                    }
                    else
                        con = m_tabConst.get(index);

                    m_tokens.add(con.getToken());
                }
                else
                {
                    //идентефикатор.
                    CId id;// = new CId(letter);
                    int index = m_tabId.indexOf(new CId(letter, false));
                    if (index < 0)
                    {
                        id = new CId(letter);
                        m_tabId.add(id);
                    }
                    else
                        id = m_tabId.get(index);

                    m_tokens.add(id.getToken());
                }
            }
        }

    }

    public void result(String filename)
    {
        PrintWriter conTab;
        PrintWriter idTab;
        PrintWriter tk;
        try
        {
            conTab = new PrintWriter(filename + "_conTab.csv", "UTF-8");
            idTab = new PrintWriter(filename + "_idTab.csv", "UTF-8");
            tk = new PrintWriter(filename + ".t", "UTF-8");
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Exception. File not found: [" + e.toString() + "]");
            return;
        }
        catch (UnsupportedEncodingException e)
        {
            System.err.println("Exception while write to file: [" + e.toString() + "]");
            return;
        }


        System.out.println("\n*** Table of constant ***");
        conTab.println("Token;Value");
        for (CConst con : m_tabConst)
        {
            System.out.println(con.getToken() + "[" + con.getValue() + "]");
            conTab.println(con.getToken() + ";" + con.getValue());
        }
        conTab.close();

        System.out.println("\n *** Table of id ***");
        idTab.println("Token;Value");
        for (CId id : m_tabId)
        {
            System.out.println(id.getToken() + "[" + id.getValue() + "]");
            idTab.println(id.getToken() + ";" + id.getValue());
        }
        idTab.close();

        System.out.println("\nTokens:");
        for (String token : m_tokens)
        {
            System.out.println(token);
            tk.println(token);
        }
        tk.close();
    }

    /*
    удаление комменатриев
    и лишних пробельных символов.
     */
    private void removeExcess(char[] text)
    {
        String str = String.valueOf(text);
        //System.out.println("Size str before=" + str.length());
        str = str.replaceAll("/\\*(.|[\\r\\n])*?\\*/", ""); /* ... */
        str = str.replaceAll("//.*\\r\\n", ""); // ...
        str = str.replaceAll("\\s+", " "); // \r\n, ...
        //System.out.println("Size str after=" + str.length());
        m_text = str.toCharArray();
        //System.out.println("Reading text: " + String.valueOf(m_text));
    }
}
