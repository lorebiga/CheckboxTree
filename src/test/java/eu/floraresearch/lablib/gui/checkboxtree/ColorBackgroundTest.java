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
package eu.floraresearch.lablib.gui.checkboxtree;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import eu.floraresearch.lablib.gui.checkboxtree.CheckboxTree;

/**
 * A test to see if the tree background works properly.
 * Changing tree.setOpaque(true); to false should toggle the background to green to red.
 * 
 * @author bigagli
 */
public class ColorBackgroundTest extends DefaultTreeModel {

    static final DefaultMutableTreeNode aChild = new DefaultMutableTreeNode("child A");

    static final DefaultMutableTreeNode bChild = new DefaultMutableTreeNode("child B");

    static final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    public static void main(String[] args) {
	CheckboxTree tree = new CheckboxTree();
	DefaultTreeModel model = new ColorBackgroundTest(root);
	root.add(aChild);
	aChild.add(bChild);
	tree.setModel(model);
	
	JFrame frame = new JFrame();
	frame.add(tree);
	tree.setOpaque(true);
	tree.setBackground(new Color(0, 255, 0));
	frame.setSize(300, 300);

	Color color = new Color(255, 0, 0);
	frame.setBackground(color);

	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * @param root
     */
    public ColorBackgroundTest(TreeNode root) {
	super(root);
    }

}
