/*
 * Copyright 2007-2022 Enrico Boldrini, Lorenzo Bigagli This file is part of
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
package eu.essilab.lablib.checkboxtree;

import javax.swing.JFrame;

import eu.essilab.lablib.checkboxtree.CheckboxTree;
import eu.essilab.lablib.checkboxtree.CheckboxTreeCellRenderer;
import eu.essilab.lablib.checkboxtree.DefaultCheckboxTreeCellRenderer;

/**
 * A CheckboxTree that toggles the check clicking on the JLabel.
 * 
 * @author boldrini
 */
public class ToggleOnLabelTest extends CheckboxTree {

    public ToggleOnLabelTest() {
	setCellRenderer(new LabelCheckboxTreeCellRenderer());
    }

    public static void main(String[] args) {
	JFrame f = new JFrame();
	CheckboxTree tree = new ToggleOnLabelTest();
	f.add(tree);
	f.setSize(300, 200);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setVisible(true);
    }

    class LabelCheckboxTreeCellRenderer extends DefaultCheckboxTreeCellRenderer implements CheckboxTreeCellRenderer {
	@Override
	public boolean isOnHotspot(int x, int y) {
	    return true;
	}
    }

}
