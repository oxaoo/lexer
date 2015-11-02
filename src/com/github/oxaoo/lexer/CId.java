package com.github.oxaoo.lexer;

/**
 * Created by Alexander on 02.11.2015.
 */
public class CId
{
    private static int m_count = 0;

    private String token = "ID";
    private int id = 0;
    private String value = "";

    public CId(String value)
    {
        this.value = value;
        id = m_count;
        m_count++;
    }

    public CId(String value, boolean b)
    {
        this.value = value;
        id = -1;
    }

    public String getToken()
    {
        return token + id;
    }

    public String getValue()
    {
        return  value;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof CId)
        {
            CId cid = (CId) obj;
            return value.equals(cid.value);
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
