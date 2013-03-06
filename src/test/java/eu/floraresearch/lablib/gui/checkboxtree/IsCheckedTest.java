package eu.floraresearch.lablib.gui.checkboxtree;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.junit.Before;
import org.junit.Test;

import eu.floraresearch.lablib.gui.checkboxtree.CheckboxTree;
import eu.floraresearch.lablib.gui.checkboxtree.TreeCheckingModel;

/**
 * A test to see if TreeCheckingModel.isPathChecked() works.
 * 
 * @author bigagli
 */
public class IsCheckedTest {

    static final DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");

    static final DefaultMutableTreeNode aChild = new DefaultMutableTreeNode("child A");

    static final DefaultMutableTreeNode bChild = new DefaultMutableTreeNode("child B");

    private CheckboxTree tree;

    @Before
    public void setUp() throws Exception {
	tree = new CheckboxTree();
	DefaultTreeModel model = new DefaultTreeModel(root);
	root.add(aChild);
	aChild.add(bChild);
	tree.setModel(model);
    }

    @Test
    public void test() {
	tree.setSelectionRow(1);
	TreePath tp = tree.getSelectionPath();
	TreeCheckingModel tcm = tree.getCheckingModel();
	assertFalse(tcm.isPathChecked(tp));
	tcm.addCheckingPath(tp);
	assertTrue(tcm.isPathChecked(tp));
	tcm.removeCheckingPath(tp);
	assertFalse(tcm.isPathChecked(tp));
    }
}
