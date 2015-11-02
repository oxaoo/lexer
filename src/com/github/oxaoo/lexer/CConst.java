package com.github.oxaoo.lexer;

/**
 * Created by Alexander on 02.11.2015.
 */
public class CConst
{
    private static int m_count = 0;

    private String token = "n/a";
    private int id = 0;
    private String value = "";

    public CConst(String value)
    {
        id = m_count;
        m_count++;
        this.value = value;

        token = isType(value);
    }

    public CConst(String value, boolean b)
    {
        id = -1;
        this.value = value;

        token = isType(value);
    }

    public String getToken()
    {
        return token + id;
    }

    public String getValue()
    {
        return  value;
    }

    public static String isType(String value)
    {
        if (isInt(value) != null)
            return "INT";

        if (isReal(value) != null)
            return "REAL";

        if (isBool(value) != null)
            return "BOOL";

        if (isEnm(value) != null)
            return "ENM";

        if (isPackname(value) != null)
            return "PACKNAME";

        return null;
    }

    private static Integer isInt(String str)
    {
        try
        {
            return Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return null;
        }
    }

    private static Boolean isBool(String str)
    {
        if (str.toLowerCase().equals("true")
                ||str.toLowerCase().equals("false"))
            return true;

        return null;

    }

    private static Double isReal(String str)
    {
        try
        {
            return Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return null;
        }
    }

    private static String isEnm(String str)
    {
        if (str.contains("."))
            if (str.split(".").length == 2)
                return str;

        return null;
    }

    private static String isPackname(String str)
    {
        if (str.contains("."))
            if (str.split(".").length > 2)
                return str;

        return null;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof CId)
        {
            CConst con = (CConst) obj;
            return value.equals(con.value);
        }
        else
            return super.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }
}
