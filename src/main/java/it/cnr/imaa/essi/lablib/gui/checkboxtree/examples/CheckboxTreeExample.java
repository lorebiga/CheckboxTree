/*
 * Copyright 2007-2010 Enrico Boldrini, Lorenzo Bigagli This file is part of
 * CheckboxTree. CheckboxTree is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version. CheckboxTree is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * General Public License along with CheckboxTree; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA
 */
package it.cnr.imaa.essi.lablib.gui.checkboxtree.examples;

import it.cnr.imaa.essi.lablib.gui.checkboxtree.CheckboxTree;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingEvent;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingListener;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingModel;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * A simple example of the CheckboxTree with key listeners: 'a' for add a node
 * 'r' for remove 'SPACE' toggles the checking of the given node.
 * 
 * @author bigagli
 * @author boldrini
 */
public class CheckboxTreeExample extends JFrame {

    private class RefreshListener implements KeyListener {

	public void keyPressed(KeyEvent e) {
	    TreePath p = CheckboxTreeExample.this.checkboxTree.getSelectionPath();
	    DefaultMutableTreeNode resource = null;
	    if (p != null) {
		System.out.println("selection path: " + p.toString());
		resource = (DefaultMutableTreeNode) p.getLastPathComponent();
	    }

	    if (e.getKeyChar() == 'r') {
		if (resource != null) {
		    TreeNode parent = resource.getParent();
		    int index = parent.getIndex(resource);
		    System.out.println("Removing " + resource.toString());
		    resource.removeFromParent();
		    DefaultTreeModel dtm = (DefaultTreeModel) CheckboxTreeExample.this.checkboxTree.getModel();
		    // dtm.nodeStructureChanged(parent);
		    dtm.nodesWereRemoved(parent, new int[] { index }, new TreeNode[] { resource });
		}
	    }

	    if (e.getKeyChar() == 'a') {
		if (resource != null) {
		    System.out.println("Adding to " + resource.toString());
		    DefaultTreeModel dtm = (DefaultTreeModel) CheckboxTreeExample.this.checkboxTree.getModel();
		    DefaultMutableTreeNode node = new DefaultMutableTreeNode("test");
		    resource.add(node);
		    dtm.nodesWereInserted(resource, new int[] { resource.getIndex(node) });
		    System.out.println("posizione: " + resource.getIndex(node));
		}
	    }

	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
    }

    public static void main(String[] args) {
	CheckboxTreeExample test = new CheckboxTreeExample();
	test.setVisible(true);
    }

    private CheckboxTree checkboxTree = null;

    private javax.swing.JPanel jContentPane = null;

    /**
     * This is the default constructor
     */
    public CheckboxTreeExample() {
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(300, 600);
	this.setContentPane(getJContentPane());
	this.setTitle("CheckboxTree");
    }

    /**
     * Initialize the tree.
     * 
     */
    private JScrollPane getCheckboxTree() {
	if (this.checkboxTree == null) {
	    this.checkboxTree = new CheckboxTree();
	    this.checkboxTree.addKeyListener(new RefreshListener());
	    System.out.println(this.checkboxTree.toString());
	    this.checkboxTree.getCheckingModel().setCheckingMode(TreeCheckingModel.CheckingMode.PROPAGATE);
	    this.checkboxTree.setRootVisible(true);
	    this.checkboxTree.setEnabled(true);
	    this.checkboxTree.expandAll();

	    DefaultMutableTreeNode mn = (DefaultMutableTreeNode) this.checkboxTree.getModel().getRoot();
	    mn = (DefaultMutableTreeNode) mn.getChildAt(2);
	    mn = (DefaultMutableTreeNode) mn.getChildAt(2);
	    System.out.println("row number: " + this.checkboxTree.getRowForPath(new TreePath(mn.getPath())));
	    this.checkboxTree.addCheckingPath(new TreePath(mn.getPath()));

	    this.checkboxTree.addTreeCheckingListener(new TreeCheckingListener() {
		public void valueChanged(TreeCheckingEvent e) {
		    System.out.println("checking set changed, leading path: " + ((TreeNode) e.getPath().getLastPathComponent()).toString());
		    System.out.println("checking roots: ");
		    TreePath[] cr = CheckboxTreeExample.this.checkboxTree.getCheckingRoots();
		    for (TreePath path : cr) {
			System.out.println(path.getLastPathComponent());
		    }
		}
	    });
	}
	return new JScrollPane(this.checkboxTree);
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
	if (this.jContentPane == null) {
	    this.jContentPane = new JPanel();
	    this.jContentPane.setLayout(new BorderLayout());
	    this.jContentPane.add(getCheckboxTree(), BorderLayout.CENTER);
	}
	return this.jContentPane;
    }

}
