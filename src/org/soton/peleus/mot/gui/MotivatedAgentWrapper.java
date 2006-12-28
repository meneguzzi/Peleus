package org.soton.peleus.mot.gui;

import jason.asSyntax.Literal;

import java.util.Iterator;
import java.util.List;

import org.soton.peleus.mot.MotivatedAgent;
import org.soton.peleus.mot.Motivation;

public class MotivatedAgentWrapper extends MotivatedAgent {
	
	protected MotivationsWindow motivationsWindow;
	
	public MotivatedAgentWrapper() {
		super();
	}
	
	@Override
	public void readMotivations(String file) throws Exception {
		this.motivationsWindow = new MotivationsWindow();
		super.readMotivations(file);
		for (Iterator iter = this.motivations.iterator(); iter.hasNext();) {
			Motivation motivation = (Motivation) iter.next();
			motivationsWindow.addMotivation(motivation);
		}
		motivationsWindow.updateData();
		motivationsWindow.setVisible(true);
	}
	
	@Override
	public void buf(List<Literal> percepts) {
		super.buf(percepts);
		motivationsWindow.updateData();
	}
}
