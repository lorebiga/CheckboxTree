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

import javax.swing.JFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import eu.floraresearch.lablib.gui.checkboxtree.CheckboxTree;

/**
 * A test tree to highlight the root visibility behavior.
 * 
 * JTree keeps the root in the "not-expanded" state, if it doesn't contain anything 
 * at the time of its insertion in the tree model.
 * Hence, even if nodes are inserted afterwards, they are not rendered in the tree UI.
 * If the root itself is hidden, the tree UI is empty altogether.
 * 
 * @author bigagli
 */
public class RootVisibilityTest {

    static final DefaultMutableTreeNode aChild = new DefaultMutableTreeNode("child A");

    static final DefaultMutableTreeNode bChild = new DefaultMutableTreeNode("child B");

    static final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    public static void main(String[] args) {
	// since it is a leaf node, the root is not expanded by the underlying JTree...
	CheckboxTree tree = new CheckboxTree(root);
	// ... adding children to the root does not modify it being not expanded...
	root.add(aChild);
	aChild.add(bChild);
	// ... the root is not expanded and invisible: the tree is empty
	tree.setRootVisible(false);
	// a solution is to force the root expansion; another option is to add the root to the tree after it's been populated
	tree.expandPath(new TreePath(root));

	JFrame frame = new JFrame();
	frame.add(tree);
	frame.setSize(300, 300);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
