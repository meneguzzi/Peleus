/**
 * 
 */
package org.soton.peleus.act.planner.jemplan;

import jason.asSyntax.Literal;
import jason.asSyntax.Plan;
import jason.asSyntax.Pred;
import jason.asSyntax.RelExpr;
import jason.asSyntax.Term;
import jason.asSyntax.TermImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.meneguzzi.jemplan.EMPlan;
import org.soton.peleus.act.planner.GoalState;
import org.soton.peleus.act.planner.PlannerConverter;
import org.soton.peleus.act.planner.ProblemObjects;
import org.soton.peleus.act.planner.ProblemOperators;
import org.soton.peleus.act.planner.StartState;
import org.soton.peleus.act.planner.StripsPlan;

/**
 * @author  frm05r
 */
public class EMPlanPlannerConverter implements PlannerConverter {
	
	protected EMPlan planner;
	protected StartState startState;
	protected GoalState goalState;
	protected ProblemOperators operators;
	protected ProblemObjects objects;
	
	protected StripsPlan plan;
	
	protected int planNumber = 0;
	
	public EMPlanPlannerConverter() {
		planner = new EMPlan();
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#createPlanningProblem(java.util.List, java.util.List, java.util.List)
	 */
	public void createPlanningProblem(List<Literal> beliefs, List<Plan> plans, List<Term> goals) {
		startState = new StartStateImpl(this);
		goalState = new GoalStateImpl();
		operators = new ProblemOperatorsImpl(this);
		//XXX This variable is created just so the user don't get a null pointer when requesting for the objects
		objects = new ProblemObjectsImpl();
		
		for (Term goal : goals) {
			goalState.addTerm(goal);
		}
		
		for (Term term : beliefs) {
			if(term.getFunctor().startsWith("object")) {
				Term newTerm = TermImpl.parse(term.getTerm(0)+"("+term.getTerm(1)+")");
				startState.addTerm(newTerm);
			}else if( (term.getTermsSize()!= 0) && (!term.getFunctor().startsWith("des"))){
				startState.addTerm(term);
			}
		}
		
		for (Plan plan : plans) {
			Pred pred = plan.getLabel();
			if (pred != null && pred.toString().startsWith("action")) {
				operators.add(plan);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#getGoalState()
	 */
	/**
	 * @return  the goalState
	 * @uml.property  name="goalState"
	 */
	public GoalState getGoalState() {
		return goalState;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#getStartState()
	 */
	/**
	 * @return  the startState
	 * @uml.property  name="startState"
	 */
	public StartState getStartState() {
		return startState;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#getProblemOperators()
	 */
	public ProblemOperators getProblemOperators() {
		return operators;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#getProblemObjects()
	 */
	public ProblemObjects getProblemObjects() {
		return objects;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#executePlanner(org.soton.peleus.act.planner.ProblemObjects, org.soton.peleus.act.planner.StartState, org.soton.peleus.act.planner.GoalState, org.soton.peleus.act.planner.ProblemOperators)
	 */
	public boolean executePlanner(ProblemObjects objects,
			StartState startState, GoalState goalState,
			ProblemOperators operators) {
		boolean planFound = false;
		try {
			File planningProblem = File.createTempFile("emplan-problem",".txt");
			File planResult = File.createTempFile("emplan-plan",".txt");
			
			FileWriter writer = new FileWriter(planningProblem);
			writer.write(startState.toPlannerString());
			writer.write(goalState.toPlannerString());
			writer.write(operators.toPlannerString());
			writer.flush();
			writer.close();
			
			planFound = planner.emplan(planningProblem.getAbsolutePath(), planResult.getAbsolutePath());
			
			FileInputStream planFileStream = new FileInputStream(planResult);
			ByteArrayOutputStream planStream = new ByteArrayOutputStream();
			byte buf[] = new byte[512];
			while(planFileStream.available() > 0) {
				int read = planFileStream.read(buf);
				planStream.write(buf, 0, read);
			}
			planStream.flush();
			planFileStream.close();
			
			plan = new StripsPlanImpl(planStream.toByteArray());
			
			planningProblem.deleteOnExit();
			planResult.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return planFound;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#getStripsPlan()
	 */
	public StripsPlan getStripsPlan() {
		return plan;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#getAgentSpeakPlan()
	 */
	public Plan getAgentSpeakPlan() {
		return plan.toAgentSpeakPlan(planNumber++);
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#toStripsString(jason.asSyntax.Term)
	 */
	public String toStripsString(Literal literal) {
		StringBuffer sbTerm = new StringBuffer();
		
		if(literal.negated()) {
			sbTerm.append("-");
		}
		sbTerm.append(toStripsString((Term)literal));
		
		return sbTerm.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#toStripsString(jason.asSyntax.RelExpr)
	 */
	public String toStripsString(RelExpr expr) {
		//XXX Since the underlying planner can't do anything about this
		//We leave it like that
		return "";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.soton.peleus.act.planner.PlannerConverter#toStripsString(jason.asSyntax.Term)
	 */
	public String toStripsString(Term term) {
		StringBuffer sbTerm = new StringBuffer();
		
		sbTerm.append(term.getFunctor());
		
		if(term.getTermsSize() != 0) {
			sbTerm.append("(");
			for (Iterator iter = term.getTerms().iterator(); iter.hasNext();) {
				Term t = (Term) iter.next();
				sbTerm.append(toStripsString(t));
				if(iter.hasNext()) {
					sbTerm.append(", ");
				}
			}
			sbTerm.append(")");
		}
		
		return sbTerm.toString();
	}

}
