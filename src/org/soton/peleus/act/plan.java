// Internal action code for project Argos.mas2j

package org.soton.peleus.act;

import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.BeliefBase;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanLibrary;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;

import java.util.List;
import java.util.logging.Logger;

import org.soton.peleus.act.planner.GoalState;
import org.soton.peleus.act.planner.PlannerConverter;
import org.soton.peleus.act.planner.ProblemObjects;
import org.soton.peleus.act.planner.ProblemOperators;
import org.soton.peleus.act.planner.StartState;
import org.soton.peleus.act.planner.jemplan.EMPlanPlannerConverter;
import org.soton.peleus.act.planner.jplan.JPlanPlannerConverter;

public class plan implements InternalAction {
	protected PlannerConverter plannerConverter = 
		new JPlanPlannerConverter();
		//new EMPlanPlannerConverter();
	
	protected static final Term trueTerm = Term.parse("true");
	
	protected int planNumber = 0;

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(InternalAction.class.getName());

	@SuppressWarnings("unchecked")
	public boolean execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		// logger.info("not implemented!");
		
		ListTerm listTerm = (ListTerm) args[0];
		List<Term> goals = listTerm.getAsList();
		
		BeliefBase beliefBase = ts.getAg().getBS();
		List<Term> beliefs = beliefBase.getAllBeliefs();
		
		PlanLibrary planLibrary = ts.getAg().getPS();
		List<Plan> plans = planLibrary.getPlans();
		
		plannerConverter.createPlanningProblem(beliefs, plans, goals);
		
		ProblemObjects objects = plannerConverter.getProblemObjects();
		StartState startState = plannerConverter.getStartState();
		GoalState goalState = plannerConverter.getGoalState();
		ProblemOperators operators = plannerConverter.getProblemOperators();
		
		boolean planFound = plannerConverter.executePlanner(objects, startState, goalState, operators);
		
		if(!planFound)
			return false;
		
		Plan plan = plannerConverter.getAgentSpeakPlan();
		
		logger.info("Adding new plan: "+System.getProperty("line.separator")+plan);
		ts.getAg().getPS().add(plan);
		
		Trigger trigger = plan.getTriggerEvent();
		logger.info("Invoking "+trigger.getLiteral().getTerm(0));
		//ts.getC().addAchvGoal(Literal.parseLiteral("executePlan(plan"+(planNumber++)+")"), null);
		ts.getC().addAchvGoal(trigger.getLiteral(), null);

		return true;
	}
}
