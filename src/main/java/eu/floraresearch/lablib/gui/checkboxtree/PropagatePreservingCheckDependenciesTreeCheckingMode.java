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

import java.util.AbstractList;
import java.util.Vector;

import javax.swing.tree.TreePath;

/**
 * PropagatePreservingCheckDependenciesTreeCheckingMode define a TreeCheckingMode with down
 * and up recursion of the check when nodes are clicked. It propagates the
 * change not only to descendants but also to ancestors. With regard to
 * descendants this mode behaves exactly like the Propagate mode. With regard to
 * ancestors it checks/unchecks them as needed so that a node is checked if and
 * only if all of its children are checked.
 * 
 * This version based on PropagatePreservingCheckTreeCheckingMode also allows to propagate
 * nodes checks/unchecks based on a DependenciesModel dependency model describing nodes dependencies
 * 
 * @author Boldrini
 * @author Louis Lecaroz
 */
public class PropagatePreservingCheckDependenciesTreeCheckingMode<E> extends TreeCheckingMode
{
  
  final private DependenciesModel<E> dependencies;
  public PropagatePreservingCheckDependenciesTreeCheckingMode(DefaultTreeCheckingModel defaultTreeCheckingModel, DependenciesModel<E> dependencies)
  {
    super(defaultTreeCheckingModel);
    this.dependencies=dependencies;
  }

  private void doCheckPath(TreePath path)
  {
    // check is propagated to children
    this.model.checkSubTree(path);
    // check all the ancestors with subtrees checked
    TreePath[] parents = new TreePath[path.getPathCount()];
    
    parents[0] = path;
    boolean uncheckAll = false;
    boolean greyAll = false;
    for (int i = 1; i < parents.length; i++) {
      parents[i] = parents[i - 1].getParentPath();
      if(parents[i].getLastPathComponent() instanceof TreeNodeObject  && ((TreeNodeObject)parents[i].getLastPathComponent()).canBeChecked()==false) continue;

      if (uncheckAll) {
        this.model.removeFromCheckedPathsSet(parents[i]);
        if (greyAll) {
          this.model.addToGreyedPathsSet(parents[i]);
        }
        else {
          if (this.model.pathHasUncheckedChildren(parents[i])) {
            this.model.addToGreyedPathsSet(parents[i]);
            greyAll = true;
          }
          else {
            this.model.removeFromGreyedPathsSet(parents[i]);
          }
        }
      }
      else {
        switch (this.model.getChildrenChecking(parents[i])) {
          case HALF_CHECKED:
            this.model.removeFromCheckedPathsSet(parents[i]);
            this.model.addToGreyedPathsSet(parents[i]);
            uncheckAll = true;
            greyAll = true;
            break;
          case ALL_UNCHECKED:
            this.model.removeFromCheckedPathsSet(parents[i]);
            this.model.removeFromGreyedPathsSet(parents[i]);
            uncheckAll = true;
            break;
          case ALL_CHECKED:
            this.model.addToCheckedPathsSet(parents[i]);
            this.model.removeFromGreyedPathsSet(parents[i]);
            break;
          default:
          case NO_CHILDREN:
            System.err.println("This should not happen (PropagatePreservingCheckTreeCheckingMode)");
            break;
        }
      }
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void checkPath(TreePath path)
  {
    this.doCheckPath(path);
        
    // expand all dependencies of this node
    final AbstractList<E> toCheck=new Vector<E>();
    if(path.getLastPathComponent() instanceof TreeNodeObject) toCheck.add((E) ((TreeNodeObject)path.getLastPathComponent()).getObject());
    else this.doCheckPath(path);
    
    for(int pos=0;pos<toCheck.size();pos++) {
      final E element=toCheck.get(pos);
      E[] dependenciesElements=dependencies.dependencies(element);
      if(dependenciesElements!=null) {
        for(E dependency:dependenciesElements) {
          if(toCheck.contains(dependency)==false) toCheck.add(dependency); 
        }
      }
    }

    // Next get root parent & next drill on all child for finding dependencies
    TreePath parent=path;
    while(parent.getParentPath()!=null) parent=parent.getParentPath();
    Vector<TreePath> paths=new Vector<TreePath>();
    paths.add(parent);
    
    for(int pos=0;pos<paths.size();pos++) {
      for(TreePath childPath:this.model.getChildrenPath(paths.get(pos))) {
        if(childPath.getLastPathComponent() instanceof TreeNodeObject && ((TreeNodeObject)childPath.getLastPathComponent()).canBeChecked()==true  && ((TreeNodeObject)childPath.getLastPathComponent()).isEnabled()==true && this.model.isPathChecked(childPath)==false && toCheck.contains( ((TreeNodeObject)childPath.getLastPathComponent()).getObject())  ) {
          this.doCheckPath(childPath);
        
        }
        paths.add(childPath);
      }
    }
  }

  private void doUncheckPath(TreePath path) {
    // uncheck is propagated to children
    this.model.uncheckSubTree(path);
    TreePath parentPath = path;
    // uncheck is propagated to parents, too
    while ((parentPath = parentPath.getParentPath()) != null) {
      this.model.removeFromCheckedPathsSet(parentPath);
      this.model.updatePathGreyness(parentPath);
    }    
  }
  @SuppressWarnings("unchecked")
  @Override
  public void uncheckPath(TreePath path)
  {
    this.doUncheckPath(path);
    
    // expand all parents/dependencies owner of this node
    final AbstractList<E> toUncheck=new Vector<E>();
    if(path.getLastPathComponent() instanceof TreeNodeObject) toUncheck.add((E) ((TreeNodeObject)path.getLastPathComponent()).getObject());
    for(int pos=0;pos<toUncheck.size();pos++) {
      final E element=toUncheck.get(pos);
      E[] parents=dependencies.parents(element);
      if(parents!=null) {
        for(E parent:parents) {
          if(toUncheck.contains(parent)==false) toUncheck.add(parent); 
        }
      }
    }
    
    // uncheck all parents/dependencies owner of this node
    for(TreePath childPath: this.model.getCheckingPaths()) {
      Object childPathComponent=(Object) childPath.getLastPathComponent();
      if(childPathComponent instanceof TreeNodeObject && ((TreeNodeObject)childPathComponent).canBeChecked()==true  && ((TreeNodeObject)childPathComponent).isEnabled()==true && toUncheck.contains( ((TreeNodeObject)childPathComponent).getObject())) 
        this.doUncheckPath(childPath);
    }
}

  /*
   * (non-Javadoc)
   * 
   * @seeit.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingMode#
   * updateCheckAfterChildrenInserted(javax.swing.tree.TreePath)
   */
  @Override
  public void updateCheckAfterChildrenInserted(TreePath parent)
  {
    if (this.model.isPathChecked(parent)) {
      checkPath(parent);
    }
    else {
      uncheckPath(parent);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @seeit.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingMode#
   * updateCheckAfterChildrenRemoved(javax.swing.tree.TreePath)
   */
  @Override
  public void updateCheckAfterChildrenRemoved(TreePath parent)
  {
    if (!this.model.isPathChecked(parent)) {
      // System.out.println(parent +" was removed (not checked)");
      if (this.model.getChildrenPath(parent).length != 0) {
        if (!this.model.pathHasChildrenWithValue(parent, false)) {
          // System.out.println("uncheking it");
          checkPath(parent);
        }
      }
    }
    this.model.updatePathGreyness(parent);
    this.model.updateAncestorsGreyness(parent);
  }

  /*
   * (non-Javadoc)
   * 
   * @seeit.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingMode#
   * updateCheckAfterStructureChanged(javax.swing.tree.TreePath)
   */
  @Override
  public void updateCheckAfterStructureChanged(TreePath parent)
  {
    if (this.model.isPathChecked(parent)) {
      checkPath(parent);
    }
    else {
      uncheckPath(parent);
    }
  }

}