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

import java.awt.GridLayout;

import javax.swing.DefaultButtonModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class TestJCheckBoxRendering {

	public static void main(String args[]) throws Exception {
		JFrame frame = new JFrame("TestJCheckBoxRendering");
		frame.getContentPane().setLayout(new GridLayout(0, 1, 5, 5));

		JCheckBox swingBox = new JCheckBox("pressed");
		swingBox.setModel(new DefaultButtonModel());
		swingBox.getModel().setArmed(false);
		swingBox.getModel().setEnabled(true);
		swingBox.getModel().setRollover(false);
		swingBox.getModel().setPressed(true);
		swingBox.getModel().setSelected(false);
		frame.getContentPane().add(swingBox);

		JCheckBox swingBox1 = new JCheckBox("selected pressed");
		swingBox1.setModel(new DefaultButtonModel());
		swingBox1.getModel().setArmed(false);
		swingBox1.getModel().setEnabled(true);
		swingBox1.getModel().setRollover(false);
		swingBox1.getModel().setPressed(true);
		swingBox1.getModel().setSelected(true);
		frame.getContentPane().add(swingBox1);

		JCheckBox swingBox2 = new JCheckBox("");
		swingBox2.setModel(new DefaultButtonModel());
		swingBox2.getModel().setArmed(false);
		swingBox2.getModel().setEnabled(true);
		swingBox2.getModel().setRollover(false);
		swingBox2.getModel().setPressed(false);
		swingBox2.getModel().setSelected(false);
		frame.getContentPane().add(swingBox2);

		JCheckBox swingBox3 = new JCheckBox("selected");
		swingBox3.setModel(new DefaultButtonModel());
		swingBox3.getModel().setArmed(false);
		swingBox3.getModel().setEnabled(true);
		swingBox3.getModel().setRollover(false);
		swingBox3.getModel().setPressed(false);
		swingBox3.getModel().setSelected(true);
		frame.getContentPane().add(swingBox3);

		JCheckBox swingBox4 = new JCheckBox("pressed armed");
		swingBox4.setModel(new DefaultButtonModel());
		swingBox4.getModel().setArmed(true);
		swingBox4.getModel().setEnabled(true);
		swingBox4.getModel().setRollover(false);
		swingBox4.getModel().setPressed(true);
		swingBox4.getModel().setSelected(false);
		frame.getContentPane().add(swingBox4);

		JCheckBox swingBox5 = new JCheckBox("pressed armed selected");
		swingBox5.setModel(new DefaultButtonModel());
		swingBox5.getModel().setArmed(true);
		swingBox5.getModel().setEnabled(true);
		swingBox5.getModel().setRollover(false);
		swingBox5.getModel().setPressed(true);
		swingBox5.getModel().setSelected(true);
		frame.getContentPane().add(swingBox5);

		JCheckBox swingBox6 = new JCheckBox("armed");
		swingBox6.setModel(new DefaultButtonModel());
		swingBox6.getModel().setArmed(true);
		swingBox6.getModel().setEnabled(true);
		swingBox6.getModel().setRollover(false);
		swingBox6.getModel().setPressed(false);
		swingBox6.getModel().setSelected(false);
		frame.getContentPane().add(swingBox6);

		JCheckBox swingBox7 = new JCheckBox("armed selected");
		swingBox7.setModel(new DefaultButtonModel());
		swingBox7.getModel().setArmed(true);
		swingBox7.getModel().setEnabled(true);
		swingBox7.getModel().setRollover(false);
		swingBox7.getModel().setPressed(false);
		swingBox7.getModel().setSelected(true);
		frame.getContentPane().add(swingBox7);

		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// final QuadristateCheckbox winBox = new QuadristateCheckbox(
		// "Testing the quadristate checkbox", QuadristateCheckbox.SELECTED);
		// frame.getContentPane().add(winBox);
		// final JCheckBox winNormal = new JCheckBox("The normal checkbox");
		// frame.getContentPane().add(winNormal);
		// wait for 3 seconds, then enable all check boxes
		new Thread() {
			{
				start();
			}

			@Override
			public void run() {
				// try {
				// // winBox.setEnabled(false);
				// // winNormal.setEnabled(false);
				// // Thread.sleep(3000);
				// // winBox.setEnabled(true);
				// // winNormal.setEnabled(true);
				// } catch (InterruptedException ex) {
				// }
			}
		};
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}