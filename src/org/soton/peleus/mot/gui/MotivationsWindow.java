package org.soton.peleus.mot.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.soton.peleus.mot.Motivation;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class MotivationsWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	
	private List<MotivationPanel> motivationPanels;

	private JScrollPane jScrollPane = null;

	private JPanel jPanel = null;

	/**
	 * This is the default constructor
	 */
	public MotivationsWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("Motivations");
		
		motivationPanels = new Vector<MotivationPanel>();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	public void updateData() {
		for (Iterator iter = motivationPanels.iterator(); iter.hasNext();) {
			MotivationPanel panel = (MotivationPanel) iter.next();
			panel.updateData();
		}
		this.repaint();
	}
	
	public void addMotivation(Motivation motivation) {
		MotivationPanel panel = new MotivationPanel();
		panel.setMotivation(motivation);
		this.motivationPanels.add(panel);
		this.getJPanel().add(panel);
	}
	
	public void removeMotivation(Motivation motivation) {
		for (Iterator iter = motivationPanels.iterator(); iter.hasNext();) {
			MotivationPanel panel = (MotivationPanel) iter.next();
			if(panel.getMotivation() == motivation) {
				this.jContentPane.remove(panel);
			}
		}
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJPanel());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(10);
			gridLayout.setColumns(1);
			jPanel = new JPanel();
			jPanel.setLayout(gridLayout);
		}
		return jPanel;
	}
}
