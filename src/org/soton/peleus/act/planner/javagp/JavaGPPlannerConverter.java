package org.soton.peleus.act.planner.javagp;

import graphplan.Graphplan;
import graphplan.PlanResult;
import graphplan.domain.DomainDescription;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.Literal;
import jason.asSyntax.LiteralImpl;
import jason.asSyntax.LogicalFormula;
import jason.asSyntax.Plan;
import jason.asSyntax.RelExpr;
import jason.asSyntax.Term;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.soton.peleus.act.planner.GoalState;
import org.soton.peleus.act.planner.PlanContextGenerator;
import org.soton.peleus.act.planner.PlannerConverter;
import org.soton.peleus.act.planner.ProblemObjects;
import org.soton.peleus.act.planner.ProblemOperators;
import org.soton.peleus.act.planner.StartState;
import org.soton.peleus.act.planner.StripsPlan;

public class JavaGPPlannerConverter implements PlannerConverter {
	private static int planNumber = 0;
	
	protected StartStateImpl startState;
	protected GoalStateImpl goalState;
	protected ProblemOperatorsImpl operators;
	
	protected Graphplan graphplan;
	
	protected StripsPlanImpl stripsPlan;

	public JavaGPPlannerConverter() {
		this.graphplan = new Graphplan();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#createPlanningProblem(java.util.List, java.util.List, java.util.List)
	 */
	public void createPlanningProblem(List<Literal> beliefs, List<Plan> plans,
			List<Term> goals) {
		this.startState = new StartStateImpl(beliefs);
		this.goalState = new GoalStateImpl(goals);
		this.operators = new ProblemOperatorsImpl(plans);
	}

	/*
	 * (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#executePlanner(org.soton.peleus.act.planner.ProblemObjects, org.soton.peleus.act.planner.StartState, org.soton.peleus.act.planner.GoalState, org.soton.peleus.act.planner.ProblemOperators)
	 */
	public boolean executePlanner(ProblemObjects objects,
			StartState startState, GoalState goalState,
			ProblemOperators operators) {
		return executePlanner(objects, startState, goalState, operators, 10);
	}

	/*
	 * (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#executePlanner(org.soton.peleus.act.planner.ProblemObjects, org.soton.peleus.act.planner.StartState, org.soton.peleus.act.planner.GoalState, org.soton.peleus.act.planner.ProblemOperators, int)
	 */
	public boolean executePlanner(ProblemObjects objects,
			StartState startState, GoalState goalState,
			ProblemOperators operators, int maxPlanSteps) {
		if((startState == this.startState) &&
		   (goalState == this.goalState) &&
		   (operators == this.operators)) {
			DomainDescription description = new DomainDescription(this.operators.getOperators(),
																  this.startState.getStartState(),
																  this.goalState.getGoalState());
			
			PlanResult planResult;
			try {
				graphplan.setMaxLevels(maxPlanSteps);
				planResult = graphplan.plan(description);
				if(planResult.isTrue()) {
					this.stripsPlan = new StripsPlanImpl(planResult);
				}
				return planResult.isTrue();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#executePlanner(org.soton.peleus.act.planner.ProblemObjects, org.soton.peleus.act.planner.StartState, org.soton.peleus.act.planner.GoalState, org.soton.peleus.act.planner.ProblemOperators, int, int)
	 */
	public boolean executePlanner(ProblemObjects objects,
			StartState startState, GoalState goalState,
			ProblemOperators operators, int maxPlanSteps, long timeout)
			throws TimeoutException {
		if((startState == this.startState) &&
				   (goalState == this.goalState) &&
				   (operators == this.operators)) {
					DomainDescription description = new DomainDescription(this.operators.getOperators(),
																		  this.startState.getStartState(),
																		  this.goalState.getGoalState());
					
					PlanResult planResult;
					try {
						graphplan.setMaxLevels(maxPlanSteps);
						planResult = graphplan.plan(description,timeout);
						if(planResult.isTrue()) {
							this.stripsPlan = new StripsPlanImpl(planResult);
						}
						return planResult.isTrue();
					} catch (TimeoutException e) {
						//If planning was interrupted because of a timeout
						//we propagate the exception to the caller
						throw e;
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
				}
				return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#getAgentSpeakPlan(boolean)
	 */
	public Plan getAgentSpeakPlan(boolean generic) {
		if(generic) {
			ListTerm goals = new ListTermImpl();
			goals.addAll(goalState.getTerms());
			Literal literal = new LiteralImpl("goalConj");
			literal.addTerm(goals);
			LogicalFormula contextCondition = PlanContextGenerator.getInstance().generateContext(stripsPlan.getStripsSteps(), operators.getPlans());
			return stripsPlan.toGenericAgentSpeakPlan(literal, contextCondition);
		} else {
			return stripsPlan.toAgentSpeakPlan(planNumber++);
		}
	}

	public GoalState getGoalState() {
		return this.goalState;
	}

	public ProblemObjects getProblemObjects() {
		return null;
	}

	public ProblemOperators getProblemOperators() {
		return this.operators;
	}

	public StartState getStartState() {
		return this.startState;
	}

	public StripsPlan getStripsPlan() {
		return this.stripsPlan;
	}

	public String toStripsString(Literal literal) {
		return this.stripsPlan.toString();
	}

	public String toStripsString(Term term) {
		return null;
	}

	public String toStripsString(RelExpr expr) {
		return null;
	}
}
