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

public interface DependenciesModel<E>
{
  DependenciesModel<E> addDependency(E entry,E dependency);
  boolean isDependency(E node, E dependency);
  E[] parents(E node);
  E[] dependencies(E node);
}
