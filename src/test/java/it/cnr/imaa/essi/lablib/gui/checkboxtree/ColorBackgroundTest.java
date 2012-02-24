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
package it.cnr.imaa.essi.lablib.gui.checkboxtree;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * @author boldrini
 */
public class ColorBackgroundTest extends DefaultTreeModel {

    /**
     * @param root
     */
    public ColorBackgroundTest(TreeNode root) {
	super(root);
    }

    static final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    static final DefaultMutableTreeNode aChild = new DefaultMutableTreeNode("child A");

    static final DefaultMutableTreeNode bChild = new DefaultMutableTreeNode("child B");

    // static public boolean changed = false;

    @Override
    public boolean isLeaf(Object node) {
	// if (changed == false & node == bChild) {
	// return true;
	// }
	// if (changed == true & node == bChild) {
	// return false;
	// }
	return false;
    }

    public static void main(String[] args) {
	final CheckboxTree tree = new CheckboxTree();
	final DefaultTreeModel model = new ColorBackgroundTest(root);
	root.add(aChild);
	aChild.add(bChild);
	tree.setModel(model);
	JFrame frame = new JFrame();
	frame.add(new JScrollPane(tree));
	frame.setSize(300, 600);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
