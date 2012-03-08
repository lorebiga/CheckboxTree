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

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * A test tree whose nodes have no icons.
 * 
 * @author bigagli
 */
public class NoIconsTest extends DefaultTreeModel {

    static final DefaultMutableTreeNode aChild = new DefaultMutableTreeNode("child A");

    static final DefaultMutableTreeNode bChild = new DefaultMutableTreeNode("child B");

    static final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    public static void main(String[] args) {
	CheckboxTree tree = new CheckboxTree();
	DefaultCheckboxTreeCellRenderer dtcr = new DefaultCheckboxTreeCellRenderer();
	dtcr.setLeafIcon(null);
	dtcr.setOpenIcon(null);
	dtcr.setClosedIcon(null);
	tree.setCellRenderer(dtcr);
	DefaultTreeModel model = new NoIconsTest(root);
	root.add(aChild);
	aChild.add(bChild);
	tree.setModel(model);
	
	JFrame frame = new JFrame();
	frame.add(tree);
	frame.setSize(300, 300);

	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * @param root
     */
    public NoIconsTest(TreeNode root) {
	super(root);
    }

}
