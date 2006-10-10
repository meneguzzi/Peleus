package org.soton.peleus.act.planner.jplan;

import jason.asSyntax.Plan;
import jason.asSyntax.Pred;
import jason.asSyntax.Term;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import jplan.JPlan;

import org.soton.peleus.act.planner.GoalState;
import org.soton.peleus.act.planner.PlannerConverter;
import org.soton.peleus.act.planner.ProblemObjects;
import org.soton.peleus.act.planner.ProblemOperators;
import org.soton.peleus.act.planner.StartState;
import org.soton.peleus.act.planner.StripsPlan;

public class JPlanPlannerConverter implements PlannerConverter {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(PlannerConverter.class.getName());
	
	protected GoalState goalState;
	
	protected StartState startState;
	
	protected ProblemOperators operators;
	
	protected ProblemObjects objects;
	
	protected StripsPlan plan;
	
	protected int planNumber = 0;

	public void createPlanningProblem(List<Term> beliefs, List<Plan> plans, List<Term> goals) {
		objects = new ProblemObjectsImpl();
		startState = new StartStateImpl(this);
		goalState = new GoalStateImpl();

		for (Iterator iter = goals.iterator(); iter.hasNext();) {
			Term term = (Term) iter.next();
			goalState.addTerm(term);
		}
		
		for (Term term : beliefs) {
			if(term.getFunctor().startsWith("object")) {
				Term newTerm = Term.parse(term.getTerm(0)+"("+term.getTerm(1)+")");
				objects.addTerm(newTerm);
			}else if( (term.getTermsSize()!= 0) && (!term.getFunctor().startsWith("des"))){
				startState.addTerm(term);
			}
		}
		
		operators = new ProblemOperatorsImpl();

		// logger.info("Plans found: ");
		for (Plan plan : plans) {
			Pred pred = plan.getLabel();
			if (pred != null && pred.toString().startsWith("action")) {
				operators.add(plan);
			}

			// logger.info(plan.toString());
		}
	}

	public GoalState getGoalState() {
		return goalState;
	}

	public StartState getStartState() {
		return startState;
	}

	public ProblemOperators getProblemOperators() {
		return operators;
	}

	public ProblemObjects getProblemObjects() {
		return objects;
	}

	public boolean executePlanner(ProblemObjects objects, StartState startState, GoalState goalState, ProblemOperators operators) {
		ByteArrayOutputStream factStream = new ByteArrayOutputStream();
		
		/*logger.info(objects.toString());
		logger.info(startState.toString());
		logger.info(goalState.toString());
		logger.info(operators.toString());*/
		try {
			factStream.write(objects.toString().getBytes());
			factStream.write(startState.toString().getBytes());
			factStream.write(goalState.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		/*logger.fine("Operators:");
		logger.fine(operators.toString());
		
		logger.fine("Facts:");
		logger.fine(factStream.toString());*/
		
		ByteArrayInputStream opStream = new ByteArrayInputStream(operators.toString().getBytes());
		ByteArrayInputStream fctStream = new ByteArrayInputStream(factStream.toByteArray());
		
		JPlan jplan = new JPlan(opStream, fctStream, 30);
		
		ByteArrayOutputStream planStream = new ByteArrayOutputStream();
		ByteArrayOutputStream graphStream = new ByteArrayOutputStream();
		OutputStreamWriter planWriter = new OutputStreamWriter(planStream);
		OutputStreamWriter graphWriter = new OutputStreamWriter(graphStream);
		
		dumpPlanningProblem(new File("problemDump.txt"));
		
		jplan.startPlanner(graphWriter, planWriter);
		
		if(planStream.size() == 0) {
			//logger.info("Planning failed");
			return false;
		}
		
		this.plan = new StripsPlanImpl(planStream.toByteArray());
		
		dumpStripsPlan(new File("planDump.txt"));
		
		return true;
	}
	
	public StripsPlan getStripsPlan() {
		return plan;
	}

	public Plan getAgentSpeakPlan() {
		return plan.toAgentSpeakPlan(planNumber++);
	}
	
	public String toStripsString(Term term) {
		StringBuffer sb = new StringBuffer();
		
		if(term.isVar()) {
			sb.append("?");
		}
		
		sb.append(term.getFunctor());
		if(term.getTermsSize() > 0) {
			sb.append("(");
			for (Term termPar : term.getTermsArray()) {
				if(sb.charAt(sb.length()-1) != '(')
					sb.append(", ");
				sb.append(toStripsString(termPar));
			}
			sb.append(")");
		}
		
		return sb.toString();
	}

	protected void dumpPlanningProblem(File dumpFile) {
		try {
			FileOutputStream outputStream = new FileOutputStream(dumpFile);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream);
			writer.write(objects.toString());
			writer.write(startState.toString());
			writer.write(goalState.toString());
			writer.write(System.getProperty("line.separator"));
			writer.write(System.getProperty("line.separator"));
			writer.write(operators.toString());
			writer.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void dumpStripsPlan(File dumpFile) {
		try {
			FileOutputStream outputStream = new FileOutputStream(dumpFile);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream);
			writer.write(plan.toString());
			writer.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
