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

import it.cnr.imaa.essi.lablib.gui.checkboxtree.QuadristateCheckbox;
import it.cnr.imaa.essi.lablib.gui.checkboxtree.QuadristateButtonModel.State;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class QuadristateCheckBoxTest {

    public static void main(String args[]) throws Exception {
	JFrame frame = new JFrame("QuadristateCheckBoxTest");
	frame.getContentPane().setLayout(new GridLayout(0, 1, 5, 5));
	final QuadristateCheckbox swingQuadristateBox = new QuadristateCheckbox("Quadristate Checkbox with Swing L&F", State.UNCHECKED);
	swingQuadristateBox.setEnabled(false);
	frame.getContentPane().add(swingQuadristateBox);
	final JCheckBox swingNormalBox = new JCheckBox("Normal Checkbox with Swing L&F");
	swingNormalBox.setEnabled(false);
	frame.getContentPane().add(swingNormalBox);
	try {
	    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	final QuadristateCheckbox quadristateCheck = new QuadristateCheckbox("Quadristate Checkbox with Native L&F", State.UNCHECKED);
	quadristateCheck.setEnabled(false);
	frame.getContentPane().add(quadristateCheck);
	final JCheckBox normalCheck = new JCheckBox("Normal Checkbox with Native L&F");
	normalCheck.setEnabled(false);
	frame.getContentPane().add(normalCheck);
	// wait for 3 seconds, then enable all check boxes
	new Thread() {
	    @Override
	    public void run() {
		try {
		    Thread.sleep(3000);
		    swingQuadristateBox.setEnabled(true);
		    swingNormalBox.setEnabled(true);
		    quadristateCheck.setEnabled(true);
		    normalCheck.setEnabled(true);
		} catch (InterruptedException ex) {
		}
	    }
	}.start();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
	frame.setVisible(true);
    }

}