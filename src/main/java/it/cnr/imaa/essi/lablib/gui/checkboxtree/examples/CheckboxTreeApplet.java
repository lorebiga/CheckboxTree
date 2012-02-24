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
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingModel.CheckingMode;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author boldrini
 */
public class CheckboxTreeApplet extends JApplet {

    private void createGUI() {
	final CheckboxTree tree = new CheckboxTree();
	tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

	// Events panel
	JPanel eventsPanel = new JPanel();
	Border eventsBorder = BorderFactory.createTitledBorder("Events");
	eventsPanel.setBorder(eventsBorder);
	final JTextArea textArea = new JTextArea(7, 20);
	textArea.setLineWrap(true);
	JScrollPane textPane = new JScrollPane(textArea);
	tree.addTreeCheckingListener(new TreeCheckingListener() {
	    public void valueChanged(TreeCheckingEvent e) {
		// convert(e.getLeadingPath());
		textArea.append("Checking event source: " + (e.getPath().getLastPathComponent()) + "\n");
	    }
	    // private void convert(TreePath changedPath) {
	    // Object[] path = changedPath.getPath();
	    // TreeModel tm = tree.getModel();
	    // for (int i = 0; i < path.length - 1; i++) {
	    // System.out.println(tm.getIndexOfChild(path[i], path[i + 1]));
	    // }
	    // System.out.println();
	    // }
	});
	eventsPanel.add(textPane);

	// Modes panel
	JPanel modesPanel = new JPanel(new GridLayout(0, 1));
	Border border = BorderFactory.createTitledBorder("Checking Mode");
	modesPanel.setBorder(border);
	ButtonGroup group = new ButtonGroup();
	JRadioButton aRadioButton = null;
	final CheckingMode modes[] = { CheckingMode.SIMPLE, CheckingMode.PROPAGATE, CheckingMode.PROPAGATE_PRESERVING_UNCHECK,
		CheckingMode.PROPAGATE_PRESERVING_CHECK };
	for (int i = 0, n = modes.length; i < n; i++) {
	    final int g = i;
	    ActionListener modeListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
		    tree.getCheckingModel().clearChecking();
		    tree.getCheckingModel().setCheckingMode(modes[g]);
		}
	    };
	    aRadioButton = new JRadioButton(modes[i].toString());
	    modesPanel.add(aRadioButton);
	    group.add(aRadioButton);
	    aRadioButton.addActionListener(modeListener);
	}
	aRadioButton.setSelected(true);
	tree.getCheckingModel().setCheckingMode(CheckingMode.PROPAGATE_PRESERVING_CHECK);

	// Actions Panel
	JPanel commandsPanel = new JPanel(new GridLayout(0, 1));
	Border commandsBorder = BorderFactory.createTitledBorder("Actions");
	commandsPanel.setBorder(commandsBorder);
	// expand all action
	JButton actionButton = new JButton("Expand all");
	ActionListener actionListener = new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		tree.expandAll();
	    }
	};
	actionButton.addActionListener(actionListener);
	commandsPanel.add(actionButton);
	// clear all action
	JButton clearButton = new JButton("Clear checking");
	ActionListener clearListener = new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		tree.clearChecking();
	    }
	};
	clearButton.addActionListener(clearListener);
	commandsPanel.add(clearButton);
	// add a children action
	JButton addButton = new JButton("Add child");
	ActionListener addListener = new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		TreePath selected = tree.getSelectionPath();
		if (selected != null) {
		    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selected.getLastPathComponent();
		    DefaultMutableTreeNode child = new DefaultMutableTreeNode("child " + (parent.getChildCount() + 1));
		    parent.add(child);
		    DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
		    dtm.nodesWereInserted(parent, new int[] { parent.getIndex(child) });
		    tree.expandPath(selected);
		}
	    }
	};
	addButton.addActionListener(addListener);
	commandsPanel.add(addButton);
	// remove a children action
	JButton removeButton = new JButton("Remove selected node");
	ActionListener removeListener = new ActionListener() {
	    public void actionPerformed(ActionEvent actionEvent) {
		TreePath selected = tree.getSelectionPath();
		if (selected != null) {
		    DefaultMutableTreeNode child = (DefaultMutableTreeNode) selected.getLastPathComponent();
		    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) child.getParent();
		    if (parent != null) {
			int index = parent.getIndex(child);
			child.removeFromParent();
			DefaultTreeModel dtm = (DefaultTreeModel) tree.getModel();
			dtm.nodesWereRemoved(parent, new int[] { index }, new TreeNode[] { child });
		    }
		}
	    }
	};
	removeButton.addActionListener(removeListener);
	commandsPanel.add(removeButton);

	// Right panel
	JPanel rightPanel = new JPanel(new GridLayout(0, 1));
	rightPanel.add(modesPanel);
	rightPanel.add(commandsPanel);
	rightPanel.add(eventsPanel);

	// Main panel
	JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	mainPanel.add(new JScrollPane(tree));
	mainPanel.add(rightPanel);
	mainPanel.setSize(640, 480);
	mainPanel.setDividerLocation(0.55);
	mainPanel.setBorder(BorderFactory.createEtchedBorder());
	getContentPane().add(mainPanel);
	setSize(640, 480);
    }

    @Override
    public void init() {
	// Execute a job on the event-dispatching thread:
	// creating this applet's GUI.
	try {
	    javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
		public void run() {
		    createGUI();
		}
	    });
	} catch (Exception e) {
	    System.err.println("createGUI didn't successfully complete");
	}
    }
}
