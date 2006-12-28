package org.soton.peleus.test;

import jason.asSyntax.Term;

public interface EnvironmentActions {
	
	public boolean executeAction(String agName, Term act);
}
