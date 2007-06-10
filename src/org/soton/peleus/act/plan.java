// Internal action code for project Argos.mas2j

package org.soton.peleus.act;

import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.DefaultTerm;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Literal;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanLibrary;
import jason.asSyntax.Pred;
import jason.asSyntax.Term;
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
import org.soton.peleus.act.planner.jemplan.EMPlanPlannerConverter;
import org.soton.peleus.act.planner.jplan.JPlanPlannerConverter;

/**
 * An <code>InternalAction</code> that links an AgentSpeak agent to
 * an external planning module. This action converts specially designed 
 * plans in the agent's <code>PlanLibrary</code> into a planning problem 
 * to create a new high-level plan. This plan is athen added to the plan
 * library and adopted as a new intention by the agent.
 *  
 * @author  Felipe Meneguzzi
 */
public class plan implements InternalAction {
	protected PlannerConverter plannerConverter = 
		//new JPlanPlannerConverter();
		new EMPlanPlannerConverter();
	
	protected static final Term trueTerm = DefaultTerm.parse("true");
	
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
		
		//First check that the action was properly invoked with an AgentSpeak
		//list as its parameter.
		if(args.length < 1) {
			logger.info("plan action must have a parameter");
			return false;
		}
		if(!(args[0] instanceof ListTerm)){
			logger.info("plan action requires a list of literals as its parameter");
			return false;
		}
		
		ListTerm listTerm = (ListTerm) args[0];
		List<Term> goals = listTerm.getAsList();
		int maxPlanSteps = 10;
		boolean makeAtomic = true;
		
		//The second optional parameter is the maximum number of steps
		//allowed for the generated plan (to constrain the planner).
		if(args.length > 1 && (args[1] instanceof NumberTerm)) {
			NumberTerm term = (NumberTerm) args[1];
			maxPlanSteps = (int) term.solve();
		} else if(args.length > 1 && (args[1] instanceof Term)) {
			makeAtomic = Boolean.parseBoolean(args[1].toString());
			//logger.info("Making plans atomic: "+makeAtomic);
		}
		
		//Extract the literals in the belief base to be used
		//as the initial state for the planning problem
		BeliefBase beliefBase = ts.getAg().getBB();
		Iterator<Literal> beliefsIterator = beliefBase.iterator();
		List<Literal> beliefs = new ArrayList<Literal>();
		while(beliefsIterator.hasNext()) {
			//Modified to filter out perceptions and rely only on beliefs
			Literal belief = beliefsIterator.next();
			if(belief.getAnnots().contains(BeliefBase.TSelf)) {
				beliefs.add(belief);
			}
		}
		
		//Extract the plans from the plan library to generate
		//STRIPS operators in the conversion process
		PlanLibrary planLibrary = ts.getAg().getPL();
		List<Plan> plans = planLibrary.getPlans();
		
		plannerConverter.createPlanningProblem(beliefs, plans, goals);
		
		ProblemObjects objects = plannerConverter.getProblemObjects();
		StartState startState = plannerConverter.getStartState();
		GoalState goalState = plannerConverter.getGoalState();
		ProblemOperators operators = plannerConverter.getProblemOperators();
		
		//Invoke the planner with the generated planning problem
		boolean planFound = plannerConverter.executePlanner(objects, startState, goalState, operators, maxPlanSteps);
		
		if(!planFound)
			return false;
		
		Plan plan = plannerConverter.getAgentSpeakPlan(true);
		if(makeAtomic) {
			//plan.setLabel(Pred.parsePred(plan.getTriggerEvent().getLiteral().getTerm(0)+"[atomic]"));
			plan.setLabel(Pred.parsePred("plan"+(planNumber++)+"[atomic]"));
		}
		
		logger.info("Adding new plan: "+System.getProperty("line.separator")+plan);
		//ts.getAg().getPL().add(plan);
		ts.getAg().getPL().add(plan,true);
		
		Trigger trigger = plan.getTriggerEvent();
		logger.info("Invoking plan "+planNumber);
		//ts.getC().addAchvGoal(Literal.parseLiteral("executePlan(plan"+(planNumber++)+")"), null);
		// Now we are adding the new goal to the current intention
		ts.getC().addAchvGoal(trigger.getLiteral(), ts.getC().getSelectedIntention());

		return true;
	}

}
