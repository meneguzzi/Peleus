// Internal action code for project Argos.mas2j

package org.soton.peleus.act;

import jason.JasonException;
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
import jason.asSyntax.Structure;
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
import org.soton.peleus.act.planner.javagp.JavaGPPlannerConverter;
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
	protected PlannerConverter plannerConverter;
	
	protected static final Term trueTerm = DefaultTerm.parse("true");
	protected static final Term remote = DefaultTerm.parse("remote");
	
	protected int planNumber = 0;

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(InternalAction.class.getName());
	
	/**
	 * Default constructor
	 */
	public plan() {
		plannerConverter = createPlannerConverter("emplan");
	}
	
	public boolean suspendIntention() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Returns the regular expression that selects the plans
	 * that will be used in composing a new plan
	 * @return
	 */
	protected String getPlanSelector() {
		return "action(.)*";
	}
	
	/**
	 * Instantiates a planner converter based on the supplied planner 
	 * selection string.
	 * 
	 * TODO Perhaps I should do this instantiation using reflection.
	 * 
	 * @param plannerName
	 * @return
	 */
	protected PlannerConverter createPlannerConverter(String plannerName) {
		PlannerConverter converter = null;
		
		if(plannerName == "emplan") {
			converter = new EMPlanPlannerConverter();
		} else if (plannerName == "jplan") {
			converter = new JPlanPlannerConverter();
		} else if (plannerName == "javagp") {
			converter = new JavaGPPlannerConverter();
		} else {
			converter = new JavaGPPlannerConverter();
		}
		
		return converter;
	}
	
	/**
	 * Ignore plans that are not marked with the action annotation
	 * @param plans
	 * @param useRemote TODO
	 * @return
	 */
	protected List<Plan> selectUseablePlans(List<Plan> plans, boolean useRemote) {
		plans = ProblemOperators.getLabelledPlans(plans, getPlanSelector());
		for(Plan plan : plans) {
			if(!useRemote && plan.getLabel().getAnnots().contains(remote)) {
				plans.remove(plan);
			}
		}
		return plans;
	}
	
	/**
	 * Extracts the literals in the belief base to be used 
	 * as the initial state for the planning problem
	 * 
	 * @param beliefBase
	 * @return
	 */
	protected List<Literal> selectRelevantBeliefs(BeliefBase beliefBase) {
		Iterator<Literal> beliefsIterator = beliefBase.iterator();
		List<Literal> beliefs = new ArrayList<Literal>();
		while(beliefsIterator.hasNext()) {
			//Modified to filter out perceptions and rely only on beliefs
			Literal belief = beliefsIterator.next();
			if(belief.getAnnots().contains(BeliefBase.TSelf)) {
				beliefs.add(belief);
			}
		}
		return beliefs;
	}
	
	/**
	 * Invokes the external planner.
	 * @param beliefs
	 * @param goals
	 * @param plans
	 * @param maxPlanSteps
	 * @return
	 */
	protected boolean invokePlanner(List<Literal> beliefs,
									List<Term> goals,
									List<Plan> plans,
			                        int maxPlanSteps) {
		plannerConverter.createPlanningProblem(beliefs, plans, goals);
		
		ProblemObjects objects = plannerConverter.getProblemObjects();
		StartState startState = plannerConverter.getStartState();
		GoalState goalState = plannerConverter.getGoalState();
		ProblemOperators operators = plannerConverter.getProblemOperators();
		
		//Invoke the planner with the generated planning problem
		return plannerConverter.executePlanner(objects, startState, goalState, operators, maxPlanSteps);
	}
	
	/**
	 * Converts the last plan into an AgentSpeak representation
	 * And add the appropriate modifications to it.
	 * @param makeGeneric	Whether or not the plan should be made generic for reuse
	 * @param makeAtomic	Whether or not the plan should be make atomic
	 * @return
	 */
	protected Plan convertPlan(boolean makeGeneric, boolean makeAtomic, boolean makeRemote) {
		Plan plan = plannerConverter.getAgentSpeakPlan(makeGeneric);
		
		String atomic = "";
		String remote = "";
		
		if(makeAtomic) {
			atomic = "atomic";
			if(makeRemote) {
				atomic+=",";
			}
		}
		
		if(makeRemote) {
			remote = "remote";
		}
		
		//plan.setLabel(Pred.parsePred(plan.getTriggerEvent().getLiteral().getTerm(0)+"[atomic]"));
		plan.setLabel(Pred.parsePred("plan"+(planNumber++)+"["+atomic+remote+"]"));
		
		return plan;
	}
	
	/**
	 * Adds the new plan to the intention structure to execute it
	 * @param plan
	 * @param ts
	 * @throws JasonException
	 */
	protected void executeNewPlan(Plan plan, TransitionSystem ts) throws JasonException {
		logger.info("Adding new plan: "+System.getProperty("line.separator")+plan);
		ts.getAg().getPL().add(plan,true);
		
		Trigger trigger = plan.getTriggerEvent();
		logger.info("Invoking plan "+planNumber);
		//ts.getC().addAchvGoal(Literal.parseLiteral("executePlan(plan"+(planNumber++)+")"), null);
		// Now we are adding the new goal to the current intention
		ts.getC().addAchvGoal(trigger.getLiteral(), ts.getC().getSelectedIntention());
	}

	@SuppressWarnings("unchecked")
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		
		//First check that the action was properly invoked with an AgentSpeak
		//list as its parameter.
		if(args.length < 1) {
			logger.info("plan action must have at least one parameter");
			return false;
		}
		if(!(args[0] instanceof ListTerm)){
			logger.info("plan action requires a list of literals as its parameter");
			return false;
		}
		
		//The second optional parameter is a list of options for the
		//planner
		if(args.length > 2) {
			logger.info("plan action cannot have more than thow parameters");
			return false;
		}
		if(!(args[1] instanceof ListTerm)){
			logger.info("plan action requires a list as its second parameter");
			return false;
		}
		
		ListTerm listTerm = (ListTerm) args[0];
		List<Term> goals = listTerm.getAsList();
		ListTerm plannerParams = (ListTerm) args[1];
		List<Term> params = plannerParams.getAsList();
		int maxPlanSteps = 50;
		boolean makeAtomic = true;
		boolean makeGeneric = true;
		//Whether or not to use remote operators
		boolean useRemote = false;
		String plannerName = null;
		
		for(int i=0; i<params.size(); i++) {
			Structure param = (Structure) params.get(i);
			if(param.getFunctor().equals("maxSteps")) {
				NumberTerm term = (NumberTerm) param.getTerm(0);
				maxPlanSteps = (int) term.solve();
			} else if(param.getFunctor().equals("makeGeneric")) {
				makeGeneric = Boolean.parseBoolean(param.getTerm(0).toString());
			} else if(param.getFunctor().equals("makeAtomic")) {
				makeAtomic = Boolean.parseBoolean(param.getTerm(0).toString());
			} else if(param.getFunctor().equals("useRemote")) {
				useRemote = Boolean.parseBoolean(param.getTerm(0).toString());
			} else if(param.getFunctor().equals("planner")) {
				plannerName = param.getTerm(0).toString();
			}
		}
		
		//If the planner(X) parameter was specified
		//select another planner converter
		if(plannerName != null) {
			PlannerConverter converter = createPlannerConverter(plannerName);
			if(converter != null) {
				this.plannerConverter = converter;
			}
		}
		
		//Extract the literals in the belief base to be used
		//as the initial state for the planning problem
		BeliefBase beliefBase = ts.getAg().getBB();
		List<Literal> beliefs = selectRelevantBeliefs(beliefBase);
		
		//Extract the plans from the plan library to generate
		//STRIPS operators in the conversion process
		PlanLibrary planLibrary = ts.getAg().getPL();
		List<Plan> plans = planLibrary.getPlans();
		plans = selectUseablePlans(plans, useRemote);
		
		//Invoke the planner
		boolean planFound = invokePlanner(beliefs, goals, plans, maxPlanSteps);
		
		if(!planFound)
			return false;
		
		Plan plan = convertPlan(makeGeneric, makeAtomic, useRemote);
		
		executeNewPlan(plan, ts);

		return true;
	}

}
