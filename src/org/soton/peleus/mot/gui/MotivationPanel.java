package org.soton.peleus.mot.gui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.soton.peleus.mot.Motivation;
import javax.swing.border.SoftBevelBorder;

public class MotivationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Motivation motivation;  //  @jve:decl-index=0:

	private JLabel jMotivationNameLabel = null;

	private JLabel jMotivationThresholdLabel = null;

	private JLabel jMotivationIntensityLabel = null;

	private JLabel jMotivationNameLabel2 = null;

	private JLabel jMotivationThresholdLabel2 = null;

	private JLabel jMotivationIntensityLabel2 = null;

	/**
	 * This is the default constructor
	 */
	public MotivationPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jMotivationIntensityLabel2 = new JLabel();
		jMotivationIntensityLabel2.setText("Intensity Value");
		jMotivationThresholdLabel2 = new JLabel();
		jMotivationThresholdLabel2.setText("Threshold Value");
		jMotivationNameLabel2 = new JLabel();
		jMotivationNameLabel2.setText("Name Value");
		jMotivationIntensityLabel = new JLabel();
		jMotivationIntensityLabel.setText("Intensity");
		jMotivationNameLabel = new JLabel();
		jMotivationNameLabel.setText("Name");
		jMotivationThresholdLabel = new JLabel();
		jMotivationThresholdLabel.setText("Threshold");
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(3);
		gridLayout.setColumns(2);
		this.setLayout(gridLayout);
		this.setSize(200, 100);
		this.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
		this.add(jMotivationNameLabel, null);
		this.add(jMotivationNameLabel2, null);
		this.add(jMotivationThresholdLabel, null);
		this.add(jMotivationThresholdLabel2, null);
		this.add(jMotivationIntensityLabel, null);
		this.add(jMotivationIntensityLabel2, null);
	}
	
	public void updateData() {
		this.jMotivationNameLabel2.setText(this.motivation.getMotivationName());
		this.jMotivationThresholdLabel2.setText(""+this.motivation.getMotivationThreshold());
		this.jMotivationIntensityLabel2.setText(""+this.motivation.getMotivationIntensity());
	}

	public Motivation getMotivation() {
		return motivation;
	}

	public void setMotivation(Motivation motivation) {
		this.motivation = motivation;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
