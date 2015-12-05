package com.github.oxaoo.parser;

import com.github.oxaoo.lexer.syntax.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

/**
 * Created by Alexander on 30.11.2015.
 */
public class CSyntaxTreeNode extends DefaultMutableTreeNode
{
    private static int counter = 0;
    private final int id;
    private static List<CSyntaxTreeNode> nodes = new LinkedList<>();
    public Symbol s;

    public CSyntaxTreeNode()
    {
        super();
        id = counter++;
    }

    public CSyntaxTreeNode(Symbol s) {
        this(s.id);
        this.s = s;
    }

    public CSyntaxTreeNode(Symbol s, CSyntaxTreeNode parent)
    {
        this(s.id, parent);
        this.s = s;
    }

    public CSyntaxTreeNode(String name)
    {
        super(name);
        id = counter++;
        nodes.add(this);
    }

    public CSyntaxTreeNode(String name, CSyntaxTreeNode parent)
    {
        super(name);
        id = counter++;
        nodes.add(this);
        int ind;
        if ((ind = nodes.indexOf(parent)) != -1)
            nodes.get(ind).add(this);
    }

    public void setChildren(List<CSyntaxTreeNode> nodes)
    {
        for (CSyntaxTreeNode node: nodes)
            this.add(node);
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSyntaxTreeNode that = (CSyntaxTreeNode) o;

        return id == that.id;

    }

    @Override
    public int hashCode()
    {
        return id;
    }

}
