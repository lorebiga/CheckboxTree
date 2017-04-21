/*
 * Copyright 2007-2013 Enrico Boldrini, Lorenzo Bigagli, Louis Lecaroz This file is part of
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

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * TreeNodeObject is the model used by a TreeTableModel. 
 * It allows developers to declare manage nodes cells custome
 * rendering and to declare un selectable nodes in the tree 
 *
 * @version %I% %G%
 *
 * @author Louis Lecaroz 
 */
public interface TreeNodeObject
{
  Object getObject();
  boolean canBeChecked();
  boolean isEnabled();
  Component getTreeCellRendererComponent(JTree tree, TreeCellRenderer treeCellRenderer, DefaultTreeCellRenderer label, boolean selected, boolean expanded,
        boolean leaf, int row,
        boolean hasFocus);
}
