package org.soton.peleus.test;

import jason.asSyntax.Term;

/**
 * An interface to allow the implementation of AgentSpeak external actions 
 * (i.e. which affect the environment) to be implemented outside the 
 * Environment class. This allows greater clarity for this kind of 
 * implementation.
 * @author Felipe Meneguzzi
 *
 */
public interface EnvironmentActions {
	
	/**
	 * A delegate method to allow 
	 * <code>Environment.executeAction(String agName, Term act)</code> to
	 * be forwarded to an external class.
	 * 
	 * @param agName The name of the agent executing the external action.
	 * @param act The term describing the action being invoked.
	 * @return Whether or not the action succeeded.
	 */
	public boolean executeAction(String agName, Term act);
}
