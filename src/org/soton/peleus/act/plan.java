// Internal action code for project Argos.mas2j

package org.soton.peleus.act;

import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Literal;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanLibrary;
import jason.asSyntax.Term;
import jason.asSyntax.TermImpl;
import jason.asSyntax.Trigger;
import jason.bb.BeliefBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.soton.peleus.act.planner.GoalState;
import org.soton.peleus.act.planner.PlannerConverter;
import org.soton.peleus.act.planner.ProblemObjects;
import org.soton.peleus.act.planner.ProblemOperators;
import org.soton.peleus.act.planner.StartState;
import org.soton.peleus.act.planner.jplan.JPlanPlannerConverter;

public class plan implements InternalAction {
	protected PlannerConverter plannerConverter = 
		new JPlanPlannerConverter();
		//new EMPlanPlannerConverter();
	
	protected static final Term trueTerm = TermImpl.parse("true");
	
	protected int planNumber = 0;

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(InternalAction.class.getName());
	
	public boolean suspendIntention() {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("unchecked")
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		// logger.info("not implemented!");
		
		ListTerm listTerm = (ListTerm) args[0];
		List<Term> goals = listTerm.getAsList();
		
		BeliefBase beliefBase = ts.getAg().getBB();
		Iterator<Literal> beliefsIterator = beliefBase.getAll();
		List<Literal> beliefs = new ArrayList<Literal>();
		while(beliefsIterator.hasNext()) {
			beliefs.add(beliefsIterator.next());
		}
		
		PlanLibrary planLibrary = ts.getAg().getPL();
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
		ts.getAg().getPL().add(plan);
		
		Trigger trigger = plan.getTriggerEvent();
		logger.info("Invoking "+trigger.getLiteral().getTerm(0));
		//ts.getC().addAchvGoal(Literal.parseLiteral("executePlan(plan"+(planNumber++)+")"), null);
		ts.getC().addAchvGoal(trigger.getLiteral(), null);

		return true;
	}

}
