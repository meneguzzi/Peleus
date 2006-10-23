/**
 * 
 */
package org.soton.peleus.mot;

import jason.asSyntax.Literal;
import jason.bb.BeliefBase;

/** 
 * A strategy pattern defining the interface for the 
 * intention mitigation function.
 * 
 * @author Felipe Rech Meneguzzi
 */
public interface MitigationFunction {
	
	/**
	 * Adds a mapping from a belief <code>Literal</code> into an
	 * integer value used to update the intensity of a given 
	 * motivation. Existing mappings are removed by multiple calls.
	 * 
	 * @param literal
	 * @param value
	 */
	public void addBeliefToIntegerMapping(Literal literal, int value);
	
	/**
	 * Removes the mapping of the supplied <code>Literal</code> from 
	 * this function.
	 * @param literal
	 */
	public void removeBeliefToIntegerMapping(Literal literal);
	
	/**
	 * The function invoked when the goals associated with this motivation are 
	 * achieved.
	 * @param beliefBase A reference to the agent's <code>BeliefBase</code>
	 */
	public int mitigate(BeliefBase beliefBase);
}
