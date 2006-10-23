package org.soton.peleus.mot;

import jason.asSyntax.Literal;
import jason.asSyntax.Trigger;
import jason.bb.BeliefBase;

import java.util.List;

/**
 * A strategy pattern defining the interface for the
 * goal generation function for a given motivation.
 * 
 * @author Felipe Rech Meneguzzi
 *
 */
public interface GoalGenerationFunction {
	
	/**
	 * Adds a mapping from a belief into an agentspeak <code>Trigger</code>
	 * for goal adoption. Any previous mapping within this function is 
	 * replaced.
	 * @param literal
	 * @param trigger
	 */
	public void addBeliefToGoalMapping(Literal literal, Trigger trigger);
	
	/**
	 * Removes the mapping of the specified literal from this function.
	 * @param literal
	 */
	public void removeBeliefToGoalMapping(Literal literal);
	
	/**
	 * Removes all mappings leading to the specified <code>Trigger</code>.
	 * @param trigger
	 */
	public void removeGoal(Trigger trigger);
	
	/**
	 * Generates the goals associated with this motivation.
	 * 
	 * @param beliefBase A reference to the agent's <code>BeliefBase</code>
	 * @return A list of new goals for the agent to accomplish 
	 * (Actually <code>Triggers</code>)
	 */
	public List<Trigger> generateGoals(BeliefBase beliefBase);
}
