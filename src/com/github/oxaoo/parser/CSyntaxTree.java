package com.github.oxaoo.parser;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

/**
 * Created by Alexander on 30.11.2015.
 */
public class CSyntaxTree extends JFrame
{
    private JTree syntaxTree;
    private JLabel selectedNodeLabel;
    private CSyntaxTreeNode root;

    public CSyntaxTree(CSyntaxTreeNode root)
    {
        this.root = root;
        syntaxTree = new JTree(this.root);
        ImageIcon nodeIcon = new ImageIcon("res/node32.png");
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(nodeIcon);
        renderer.setOpenIcon(nodeIcon);
        renderer.setClosedIcon(nodeIcon);

        syntaxTree.setCellRenderer(renderer);
        syntaxTree.setShowsRootHandles(true);
        syntaxTree.setRootVisible(false);
        this.add(new JScrollPane(syntaxTree));

        selectedNodeLabel = new JLabel();
        selectedNodeLabel.setFont(new Font("Consolas", Font.PLAIN, 18));

        this.add(selectedNodeLabel, BorderLayout.SOUTH);
        syntaxTree.getSelectionModel().addTreeSelectionListener(e ->
        {
            CSyntaxTreeNode selectedNode = (CSyntaxTreeNode) syntaxTree.getLastSelectedPathComponent();
            selectedNodeLabel.setText(e.getPath().toString());
        });

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Syntax Tree");
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
/*
    public CSyntaxTreeNode getRoot() {
        return root;
    }
*/
}
