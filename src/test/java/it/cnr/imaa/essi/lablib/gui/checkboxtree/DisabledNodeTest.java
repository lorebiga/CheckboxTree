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

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * A test for correct QuadristateCheckbox enabling/disabling.
 * 
 * @author bigagli
 */
public class DisabledNodeTest extends JFrame {

	public DisabledNodeTest() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 600);
		javax.swing.JPanel jContentPane = new JPanel();
		jContentPane.setLayout(new BorderLayout());
		jContentPane.add(getCheckboxTree(), BorderLayout.CENTER);
		this.setContentPane(jContentPane);
		this.setTitle("CheckboxTree");
	}

	/**
	 * Create the tree. Check one node. Disables it.
	 */
	private JScrollPane getCheckboxTree() {
		final CheckboxTree checkboxTree = new CheckboxTree();
		System.out.println(checkboxTree.toString());
		checkboxTree.getCheckingModel().setCheckingMode(
				TreeCheckingModel.CheckingMode.PROPAGATE);
		checkboxTree.setRootVisible(true);
		checkboxTree.setEnabled(true);
		checkboxTree.expandAll();

		DefaultMutableTreeNode mn = (DefaultMutableTreeNode) checkboxTree
				.getModel().getRoot();
		mn = (DefaultMutableTreeNode) mn.getChildAt(2);
		mn = (DefaultMutableTreeNode) mn.getChildAt(2);
		TreePath path = new TreePath(mn.getPath());
		System.out.println("row number: " + checkboxTree.getRowForPath(path));
		checkboxTree.addCheckingPath(path);
		checkboxTree.getCheckingModel().setPathEnabled(path, false);

		checkboxTree.addTreeCheckingListener(new TreeCheckingListener() {
			public void valueChanged(TreeCheckingEvent e) {
				System.out.println("checking set changed, leading path: "
						+ ((TreeNode) e.getPath().getLastPathComponent())
								.toString());
				System.out.println("checking roots: ");
				TreePath[] cr = checkboxTree.getCheckingRoots();
				for (TreePath path : cr) {
					System.out.println(path.getLastPathComponent());
				}
			}
		});
		return new JScrollPane(checkboxTree);
	}

	public static void main(String[] args) {
		DisabledNodeTest test = new DisabledNodeTest();
		test.setVisible(true);
	}

}
