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
import it.cnr.imaa.essi.lablib.gui.checkboxtree.CheckboxTreeCellRenderer;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingModel;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingModel.CheckingMode;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreePath;

/**
 * Example showing the implementation of a CheckboxTree with RadioButton-style
 * checkboxes.
 * 
 * @author bigagli
 */
public class RadioButtonTreeCellRenderer extends JPanel implements CheckboxTreeCellRenderer {

    public static void main(String[] args) {
	CheckboxTree tree = new CheckboxTree();
	tree.getCheckingModel().setCheckingMode(CheckingMode.SINGLE);
	tree.setCellRenderer(new RadioButtonTreeCellRenderer());
	JFrame frame = new JFrame("RadioButton tree");
	frame.add(tree);
	tree.expandAll();
	frame.pack();
	frame.setVisible(true);
    }

    protected JRadioButton button = new JRadioButton();

    protected JLabel label = new JLabel();

    public RadioButtonTreeCellRenderer() {
	super();
	label.setFocusable(true);
	label.setOpaque(true);
	setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
	add(button);
	add(label);
	button.setBackground(UIManager.getColor("Tree.textBackground"));
	setBackground(UIManager.getColor("Tree.textBackground"));
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row,
	    boolean hasFocus) {
	label.setText(value.toString());
	if (selected)
	    label.setBackground(UIManager.getColor("Tree.selectionBackground"));
	else
	    label.setBackground(UIManager.getColor("Tree.textBackground"));
	TreeCheckingModel checkingModel = ((CheckboxTree) tree).getCheckingModel();
	TreePath path = tree.getPathForRow(row);
	boolean enabled = checkingModel.isPathEnabled(path);
	boolean checked = checkingModel.isPathChecked(path);
	button.setEnabled(enabled);
	label.setForeground(Color.black);
	button.setSelected(checked);
	return this;
    }

    public boolean isOnHotspot(int x, int y) {
	return (button.getBounds().contains(x, y));
    }
}