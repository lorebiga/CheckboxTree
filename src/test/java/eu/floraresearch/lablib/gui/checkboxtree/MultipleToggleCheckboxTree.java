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

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import eu.floraresearch.lablib.gui.checkboxtree.CheckboxTreeCellRenderer;
import eu.floraresearch.lablib.gui.checkboxtree.DefaultCheckboxTreeCellRenderer;
import eu.floraresearch.lablib.gui.checkboxtree.DefaultTreeCheckingModel;
import eu.floraresearch.lablib.gui.checkboxtree.TreeCheckingEvent;
import eu.floraresearch.lablib.gui.checkboxtree.TreeCheckingListener;
import eu.floraresearch.lablib.gui.checkboxtree.TreeCheckingModel;

/**
 * This class was attached to ticket #5. It is supposed to implement multiple
 * nodes selection/deselection, but it doesn't seem to work.
 */
public class MultipleToggleCheckboxTree extends JTree {

    private class NodeCheckListener extends MouseAdapter {
	@Override
	public void mousePressed(MouseEvent e) {
	    if (!isEnabled()) {
		return;
	    }
	    // we use mousePressed instead of mouseClicked for performance
	    int x = e.getX();
	    int y = e.getY();
	    int row = getRowForLocation(x, y);
	    if (row == -1) {
		// click outside any node
		return;
	    }
	    Rectangle rect = getRowBounds(row);
	    if (rect == null) {
		// clic on an invalid node
		return;
	    }
	    if (((CheckboxTreeCellRenderer) getCellRenderer()).isOnHotspot(x - rect.x, y - rect.y)) {
		// NEW
		TreePath clickedPath = getPathForRow(row);
		getCheckingModel().toggleCheckingPath(clickedPath);
		// if the clicked node was selected with another ones, set (or
		// unset) all of them
		TreePath[] selectionPaths = getSelectionPaths();
		if (selectionPaths != null && Arrays.asList(selectionPaths).contains(clickedPath)) {
		    if (getCheckingModel().isPathChecked(getPathForRow(row))) {
			getCheckingModel().addCheckingPaths(selectionPaths);
		    } else {
			getCheckingModel().removeCheckingPaths(selectionPaths);
		    }
		}
	    }
	}
    };

    /*
     * Temporary solution for enabling spacebar checking. Should make use of
     * InputMaps?
     */
    private class SpaceListener extends KeyAdapter {

	@Override
	public void keyPressed(KeyEvent e) {
	    if (!isEnabled()) {
		return;
	    }
	    TreePath path = MultipleToggleCheckboxTree.this.getSelectionPath();

	    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		if (path != null) {
		    TreeCheckingModel cm = MultipleToggleCheckboxTree.this.getCheckingModel();
		    cm.toggleCheckingPath(path);
		}
	    }

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
    }

    private TreeCheckingModel checkingModel;

    /**
     * For GUI builders. It returns a CheckboxTree with a default tree model to
     * show something interesting. Creates a CheckboxTree with visible handles,
     * a default CheckboxTreeCellRenderer and a default TreeCheckingModel.
     */
    public MultipleToggleCheckboxTree() {
	super(getDefaultTreeModel());
	initialize();
    }

    /**
     * Creates a CheckboxTree with visible handles, a default
     * CheckboxTreeCellRenderer and a default TreeCheckingModel. The tree is
     * based on the specified tree model.
     */
    public MultipleToggleCheckboxTree(TreeModel treemodel) {
	super(treemodel);
	initialize();
    }

    /**
     * Creates a CheckboxTree with visible handles, a default
     * CheckboxTreeCellRenderer and a default TreeCheckingModel. The tree root
     * is the specified tree node.
     * 
     * @param root
     *            the root of the tree
     */
    public MultipleToggleCheckboxTree(TreeNode root) {
	super(root);
	initialize();
    }

    /**
     * Add a path in the checking.
     */
    public void addCheckingPath(TreePath path) {
	getCheckingModel().addCheckingPath(path);
    }

    /**
     * Add paths in the checking.
     */
    public void addCheckingPaths(TreePath[] paths) {
	getCheckingModel().addCheckingPaths(paths);
    }

    /**
     * Adds a listener for <code>TreeChecking</code> events.
     * 
     * @param tsl
     *            the <code>TreeCheckingListener</code> that will be notified
     *            when a node is checked
     */
    public void addTreeCheckingListener(TreeCheckingListener tsl) {
	this.checkingModel.addTreeCheckingListener(tsl);
    }

    /**
     * Clears the checking.
     */
    public void clearChecking() {
	getCheckingModel().clearChecking();
    }

    /**
     * Expand completely a tree
     */
    public void expandAll() {
	expandSubTree(getPathForRow(0));
    }

    private void expandSubTree(TreePath path) {
	expandPath(path);
	Object node = path.getLastPathComponent();
	int childrenNumber = getModel().getChildCount(node);
	TreePath[] childrenPath = new TreePath[childrenNumber];
	for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
	    childrenPath[childIndex] = path.pathByAddingChild(getModel().getChild(node, childIndex));
	    expandSubTree(childrenPath[childIndex]);
	}
    }

    /**
     * @return Returns the TreeCheckingModel.
     */
    public TreeCheckingModel getCheckingModel() {
	return this.checkingModel;
    }

    /**
     * Return paths that are in the checking.
     */
    public TreePath[] getCheckingPaths() {
	return getCheckingModel().getCheckingPaths();
    }

    /**
     * @return Returns the paths that are in the checking set and are the
     *         (upper) roots of checked trees.
     */
    public TreePath[] getCheckingRoots() {
	return getCheckingModel().getCheckingRoots();
    }

    /**
     * @return Returns the paths that are in the greying.
     */
    public TreePath[] getGreyingPaths() {
	return getCheckingModel().getGreyingPaths();
    }

    /**
     * Convenience initialization method. NEW
     */
    private void initialize() {
	setCheckingModel(new DefaultTreeCheckingModel(this.treeModel));
	setCellRenderer(new DefaultCheckboxTreeCellRenderer());
	MouseListener[] listener = getMouseListeners();
	for (MouseListener mouseListener : listener)
	    removeMouseListener(mouseListener);
	addMouseListener(new NodeCheckListener());
	for (MouseListener mouseListener : listener)
	    addMouseListener(mouseListener);

	addKeyListener(new SpaceListener());
	this.selectionModel.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
	setShowsRootHandles(true);
	putClientProperty("JTree.lineStyle", "Angled");// for Metal L&F
    }

    /**
     * Returns true if the item identified by the path is currently checked.
     * 
     * @param path
     *            a <code>TreePath</code> identifying a node
     * @return true if the node is checked
     */
    public boolean isPathChecked(TreePath path) {
	return getCheckingModel().isPathChecked(path);
    }

    /**
     * Remove a path from the checking.
     */
    public void removeCheckingPath(TreePath path) {
	getCheckingModel().removeCheckingPath(path);
    }

    /**
     * Remove paths from the checking.
     */
    public void removeCheckingPaths(TreePath[] paths) {
	getCheckingModel().removeCheckingPaths(paths);
    }

    /**
     * Removes a <code>TreeChecking</code> listener.
     * 
     * @param tsl
     *            the <code>TreeChckingListener</code> to remove
     */
    public void removeTreeCheckingListener(TreeCheckingListener tsl) {
	this.checkingModel.removeTreeCheckingListener(tsl);
    }

    /**
     * Sets the <code>CheckboxTreeCellRenderer</code> that will be used to draw
     * each cell.
     * 
     * @param x
     *            the <code>TreeCellRenderer</code> that is to render each cell
     */
    public void setCellRenderer(CheckboxTreeCellRenderer x) {
	super.setCellRenderer(x);
    }

    /**
     * Set the checking model of this CheckboxTree.
     * 
     * @param newCheckingModel
     *            The new TreeCheckingModel.
     */
    public void setCheckingModel(TreeCheckingModel newCheckingModel) {
	/*
	 * in case we are dealing with DefaultTreeCheckingModel, we link/unlink
	 * it from the model of this tree
	 */
	TreeCheckingModel oldCheckingModel = this.checkingModel;
	if (oldCheckingModel != null && oldCheckingModel instanceof DefaultTreeCheckingModel) {
	    // null the model to avoid dangling pointers
	    ((DefaultTreeCheckingModel) oldCheckingModel).setTreeModel(null);
	}
	// TODO: what if newCheckingModel == null ?
	this.checkingModel = newCheckingModel;
	if (newCheckingModel != null) {
	    if (newCheckingModel instanceof DefaultTreeCheckingModel) {
		((DefaultTreeCheckingModel) newCheckingModel).setTreeModel(getModel());
	    }
	    // add a treeCheckingListener to repaint upon checking modifications
	    newCheckingModel.addTreeCheckingListener(new TreeCheckingListener() {
		public void valueChanged(TreeCheckingEvent e) {
		    repaint();
		}
	    });
	}
    }

    /**
     * Set path in the checking.
     */
    public void setCheckingPath(TreePath path) {
	getCheckingModel().setCheckingPath(path);
    }

    /**
     * Set paths that are in the checking.
     */
    public void setCheckingPaths(TreePath[] paths) {
	getCheckingModel().setCheckingPaths(paths);
    }

    /**
     * Sets the TreeModel and links it to the existing checkingModel.
     */
    @Override
    public void setModel(TreeModel newModel) {
	super.setModel(newModel);
	if (checkingModel != null && checkingModel instanceof DefaultTreeCheckingModel) {
	    ((DefaultTreeCheckingModel) checkingModel).setTreeModel(newModel);
	}
    }

    /**
     * @return a string representation of the tree, including the checking,
     *         enabling and greying sets.
     */
    @Override
    public String toString() {
	String retVal = super.toString();
	TreeCheckingModel tcm = getCheckingModel();
	if (tcm != null) {
	    return retVal + "\n" + tcm.toString();
	}
	return retVal;
    }

    /**
     * Test function. NEW
     * 
     * @param args
     */
    public static void main(String[] args) {
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
	Random rand = new Random();
	java.util.List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();
	for (int i = 0; i < 100; i++) {
	    DefaultMutableTreeNode other = new DefaultMutableTreeNode("n " + i);
	    int hazardous = rand.nextInt(100);
	    if (hazardous < i) {
		nodes.get(hazardous).add(other);
	    } else {
		root.add(other);
	    }
	    nodes.add(other);
	}

	MultipleToggleCheckboxTree tree = new MultipleToggleCheckboxTree(root);
	frame.add(tree);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
    }
}