package org.soton.peleus.act.planner.jplan;

import jason.asSyntax.BodyLiteral;
import jason.asSyntax.DefaultTerm;
import jason.asSyntax.Literal;
import jason.asSyntax.LogExpr;
import jason.asSyntax.Plan;
import jason.asSyntax.Pred;
import jason.asSyntax.RelExpr;
import jason.asSyntax.Structure;
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

/**
 * @author  meneguzz
 */
public class JPlanPlannerConverter implements PlannerConverter {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(PlannerConverter.class.getName());
	
	protected GoalState goalState;
	
	protected StartState startState;
	
	protected ProblemOperators operators;
	
	protected ProblemObjects objects;
	
	protected StripsPlan plan;
	
	protected int planNumber = 0;

	public void createPlanningProblem(List<Literal> beliefs, List<Plan> plans, List<Term> goals) {
		objects = new ProblemObjectsImpl();
		startState = new StartStateImpl(this);
		goalState = new GoalStateImpl();

		for (Iterator iter = goals.iterator(); iter.hasNext();) {
			Term term = (Term) iter.next();
			goalState.addTerm(term);
		}
		
		for (Literal literal : beliefs) {
			if(literal.getFunctor().startsWith("object")) {
				Term newTerm = DefaultTerm.parse(literal.getTerm(0)+"("+literal.getTerm(1)+")");
				objects.addTerm(newTerm);
			}else if( (literal.getArity()!= 0) && (!literal.getFunctor().startsWith("des"))){
				startState.addTerm(literal);
			}
		}
		
		operators = new ProblemOperatorsImpl(this);

		// logger.info("Plans found: ");
		for (Plan plan : plans) {
			operators.add(plan);
			// logger.info(plan.toString());
		}
	}

	/**
	 * @return  the goalState
	 * @uml.property  name="goalState"
	 */
	public GoalState getGoalState() {
		return goalState;
	}

	/**
	 * @return  the startState
	 * @uml.property  name="startState"
	 */
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
		return executePlanner(objects, startState, goalState, operators, 10);
	}
	
	public boolean executePlanner(ProblemObjects objects, StartState startState, GoalState goalState, ProblemOperators operators, int maxPlanSteps) {
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
		
		JPlan jplan = new JPlan(opStream, fctStream, maxPlanSteps);
		
		ByteArrayOutputStream planStream = new ByteArrayOutputStream();
		ByteArrayOutputStream graphStream = new ByteArrayOutputStream();
		OutputStreamWriter planWriter = new OutputStreamWriter(planStream);
		OutputStreamWriter graphWriter = new OutputStreamWriter(graphStream);
		
		//dumpPlanningProblem(new File("problemDump.txt"));
		jplan.startPlanner(graphWriter, planWriter);
		
		if(planStream.size() == 0) {
			//logger.info("Planning failed");
			return false;
		}
		
		this.plan = new StripsPlanImpl(planStream.toByteArray());
		
		//dumpStripsPlan(new File("planDump.txt"));
		
		return true;
	}
	
	public StripsPlan getStripsPlan() {
		return plan;
	}

	public Plan getAgentSpeakPlan(boolean generic) {
		return plan.toAgentSpeakPlan(planNumber++);
	}
	
	public String toStripsString(Literal literal) {
		StringBuffer sbTerm = new StringBuffer();
		
		if(literal.negated()) {
			sbTerm.append("-");
		}
		sbTerm.append(toStripsString((Term)literal));
		
		return sbTerm.toString();
	}
	
	public String toStripsString(Term term) {
		StringBuffer sb = new StringBuffer();
		
		if(term.isVar()) {
			sb.append("?");
		}
		
		if(term.isStructure()) {
			Structure structure = (Structure) term;
			sb.append(structure.getFunctor());
			if(structure.getTermsSize() > 0) {
				sb.append("(");
				for (Term termPar : structure.getTermsArray()) {
					if(sb.charAt(sb.length()-1) != '(')
						sb.append(", ");
					sb.append(toStripsString(termPar));
				}
				sb.append(")");
			}
		} else {
			sb.append(term.toString());
		}
		
		
		
		return sb.toString();
	}
	
	public String toStripsString(RelExpr expr) {
		StringBuffer sb = new StringBuffer();
		
		sb.append(toStripsString2(expr.getLHS()));
		sb.append(toStripsOperator(expr.getOp()));
		sb.append(toStripsString2(expr.getRHS()));
		
		return sb.toString();
	}
	
	public String toStripsString(BodyLiteral bodyLiteral) {
		StringBuffer sb = new StringBuffer();
		
		Literal literal = bodyLiteral.getLiteralFormula();
		
		sb.append(toStripsString2(literal));
		
		return sb.toString();
	}
	
	public String toStripsString2(Literal literal) {
		StringBuffer sb = new StringBuffer();
		
		if(literal.negated()) {
			sb.append("-");
		}
		sb.append(literal.getFunctor());
		if(literal.getTermsSize() > 0) {
			sb.append("(");
			for (Term termPar : literal.getTermsArray()) {
				if(sb.charAt(sb.length()-1) != '(')
					sb.append(", ");
				sb.append(toStripsString2(termPar));
			}
			sb.append(")");
		}
		
		return sb.toString();
	}
	
	public String toStripsString2(Term term) {
		StringBuffer sb = new StringBuffer();
		
		if(!term.isVar()) {
			sb.append("@");
		}
		
		if(term.isVar()) {
			sb.append("?");
		}
		
		if(term.isStructure()) {
			Structure structure = (Structure) term;
			sb.append(structure.getFunctor());
			if(structure.getTermsSize() > 0) {
				sb.append("(");
				for (Term termPar : structure.getTermsArray()) {
					if(sb.charAt(sb.length()-1) != '(')
						sb.append(", ");
					sb.append(toStripsString2(termPar));
				}
				sb.append(")");
			}
		} else {
			sb.append(term.toString());
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

	public String toStripsNegatedOperator(RelExpr.RelationalOp op) {
		switch (op) {
		case unify:
		case eq:
			return toStripsOperator(RelExpr.RelationalOp.dif);
		case dif:
			return toStripsOperator(RelExpr.RelationalOp.eq);
		case gt:
			return toStripsOperator(RelExpr.RelationalOp.lte);
		case gte:
			return toStripsOperator(RelExpr.RelationalOp.lt);
		case literalBuilder:
			return toStripsOperator(RelExpr.RelationalOp.literalBuilder);
		case lt:
			return toStripsOperator(RelExpr.RelationalOp.gte);
		case lte:
			return toStripsOperator(RelExpr.RelationalOp.gt);
		case none:
		default:
			return "";
		}
	}

	public String toStripsOperator(RelExpr.RelationalOp op) {
		switch (op) {
		case dif:
			return "!=";
		case eq:
			return "=";
		case gt:
			return ">";
		case gte:
			return ">=";
		case literalBuilder:
			return "ARGH";
		case lt:
			return "<";
		case lte:
			return "<=";
		case unify:
			return "==";
		case none:
		default:
			return "";
		}
	}
	
	public String toStripsOperator(LogExpr.LogicalOp logicalOp) {
		switch (logicalOp) {
		case and:
			return "&";
		case not:
			return "!";
		case or:
			return "|";
		case none:
		default:
			return "";
		}
	}
}
