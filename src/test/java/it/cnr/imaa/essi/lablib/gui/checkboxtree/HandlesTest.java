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
public class HandlesTest extends DefaultTreeModel {

	/**
	 * @param root
	 */
	public HandlesTest(TreeNode root) {
		super(root);
	}

	static final DefaultMutableTreeNode root = new DefaultMutableTreeNode(
			"root");

	static final DefaultMutableTreeNode aChild = new DefaultMutableTreeNode(
			"child A");

	static final DefaultMutableTreeNode bChild = new DefaultMutableTreeNode(
			"child B");

	static final DefaultMutableTreeNode cChild = new DefaultMutableTreeNode(
			"child C");

	static public boolean changed = false;

	// public void addTreeModelListener(TreeModelListener l) {
	//
	// }

	// public Object getChild(Object parent, int index) {
	// if (parent == root) {
	// return aChild;
	// }
	// if (parent == aChild) {
	// return bChild;
	// }
	// if (changed == true & parent == bChild) {
	// return cChild;
	// }
	// return null;
	// }
	//
	// public int getChildCount(Object parent) {
	// if (parent == root) {
	// return 1;
	// }
	// if (parent == aChild) {
	// return 1;
	// }
	// if (changed == true & parent == bChild) {
	// return 1;
	// }
	// return 0;
	// }

	// public int getIndexOfChild(Object parent, Object child) {
	// return 0;
	// }

	// public Object getRoot() {
	// return root;
	// }

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

	// public void removeTreeModelListener(TreeModelListener l) {
	//
	// }
	//
	// public void valueForPathChanged(TreePath path, Object newValue) {
	//
	// }

	public static void main(String[] args) {
		final CheckboxTree tree = new CheckboxTree();
		final DefaultTreeModel model = new HandlesTest(root);
		root.add(aChild);
		// aChild.add(bChild);
		tree.setModel(model);
		JFrame frame = new JFrame();
		frame.add(new JScrollPane(tree));
		frame.setSize(300, 600);
		frame.setVisible(true);
		// new Thread() {
		// @Override
		// public void run() {
		// try {
		// sleep(3000);
		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// System.out.println("changed");
		// // changed = true;
		// // aChild.add(bChild);
		// // model.nodesWereInserted(aChild, new int[] { 0 });
		// model.nodeStructureChanged(aChild);
		// tree.collapsePath(new TreePath(aChild.getPath()));
		// aChild.remove(bChild);
		// bChild.remove(cChild);
		// model.nodeStructureChanged(bChild);
		// model.nodeChanged(aChild);
		// }
		// });
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		// }
		// }.start();

	}
}
