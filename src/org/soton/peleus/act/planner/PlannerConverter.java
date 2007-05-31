package org.soton.peleus.act.planner;

import jason.asSyntax.Literal;
import jason.asSyntax.Plan;
import jason.asSyntax.RelExpr;
import jason.asSyntax.Term;

import java.util.List;

/**
 * An interface responsible for converting AgentSpeak mental components into a planning formalism 
 * @author Felipe Meneguzzi <a href="mailto:felipe@meneguzzi.eu">felipe@meneguzzi.eu</a>
 *
 */
public interface PlannerConverter {
	
	/**
	 * Creates a planning problem from the set of Beliefs, Plans and the stated Goals.
	 * @param beliefs A list containing the Agent's belief base. 
	 * @param plans A list containing the Agent's plan library.
	 * @param goals A list containing the stated goals for the proposed problem.
	 */
	public void createPlanningProblem(List<Literal> beliefs, List<Plan> plans, List<Term> goals);
	
	/**
	 * Returns the Goal State for the planning problem converted using this class.  
	 * @return The converted GoalState
	 */
	public GoalState getGoalState();
	
	/**
	 * Returns the Start State for the planning problem converted using this class.  
	 * @return The converted StartState
	 */
	public StartState getStartState();
	
	/**
	 * Returns the Problem Operators for the planning problem converted using this class.  
	 * @return The converted ProblemOperators
	 */
	public ProblemOperators getProblemOperators();
	
	/**
	 * Returns the Problem Objects for the planning problem converted using this class.  
	 * @return The converted ProblemObjects
	 */
	public ProblemObjects getProblemObjects();
	
	/**
	 * Executes the underlying planner returning true or false depending on whether planning was successful.
	 * 
	 * @param objects 		The objects declared for the planning problem
	 * @param startState	The start state for the planning problem
	 * @param goalState		The goal state for the planning problem
	 * @param operators		The operatores for the planning problem
	 * @return 				Whether or not planning suceeded in generating a valid plan
	 */
	public boolean executePlanner(ProblemObjects objects, StartState startState, GoalState goalState, ProblemOperators operators);
	
	/**
	 * Executes the underlying planner returning true or false depending on whether planning was successful, and 
	 * tells the planner to limit search for plans with a maximum of maxPlanSteps, if allowed by the 
	 * 
	 * @param objects 		The objects declared for the planning problem
	 * @param startState	The start state for the planning problem
	 * @param goalState		The goal state for the planning problem
	 * @param operators		The operatores for the planning problem
	 * @param maxPlanSteps  The maximum number of plan steps allowed for any solution.
	 * @return 				Whether or not planning suceeded in generating a valid plan
	 */
	public boolean executePlanner(ProblemObjects objects, StartState startState, GoalState goalState, ProblemOperators operators, int maxPlanSteps);
	
	/**
	 * Returns the StripsPlan resulting from the invocation of the external planner.
	 * @return The StripsPlan from the external planner or null in case planning failed 
	 */
	public StripsPlan getStripsPlan();
	
	/**
	 * Returns the AgentSpeak plan resulting from the invocation of the external planner.
	 * @param generic Whether or not the generated AgentSpeak <code>Plan</code> should be
	 * 			made generic for further use in the plan library.
	 * @return The AgentSpeak from the external planner or null in case planning failed
	 */
	public Plan getAgentSpeakPlan(boolean generic);
	
	/**
	 * Converts an AgentSpeak Literal into a STRIPS string.
	 * This transformation includes converting Prolog-style variables (starting with uppercase characters), 
	 * into the STRIPS format (consisting of a starting question mark).
	 * @param literal The literal to be converted
	 * @return A String with the resulting STRIPS representation 
	 */
	public String toStripsString(Literal literal);
	
	/**
	 * Converts an AgentSpeak Term into a STRIPS string.
	 * This transformation includes converting Prolog-style variables (starting with uppercase characters), 
	 * into the STRIPS format (consisting of a starting question mark).
	 * @param term The term to be converted
	 * @return A String with the resulting STRIPS representation 
	 */
	public String toStripsString(Term term);

	public String toStripsString(RelExpr expr);
}
