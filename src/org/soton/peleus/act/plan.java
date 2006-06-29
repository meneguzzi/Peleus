// Internal action code for project Argos.mas2j

package org.soton.peleus.act;

import jason.asSemantics.InternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.BeliefBase;
import jason.asSyntax.BodyLiteral;
import jason.asSyntax.DefaultLiteral;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Literal;
import jason.asSyntax.Plan;
import jason.asSyntax.PlanLibrary;
import jason.asSyntax.Pred;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import jplan.JPlan;

public class plan implements InternalAction {
	protected static final Term trueTerm = Term.parse("true");
	
	protected int planNumber = 0;

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(InternalAction.class.getName());

	@SuppressWarnings("unchecked")
	public boolean execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		// logger.info("not implemented!");
		ProblemObjects objects = new ProblemObjects();
		StartState startState = new StartState();
		GoalState goalState = new GoalState();
		
		ListTerm listTerm = (ListTerm) args[0];
		List list = listTerm.getAsList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Term term = (Term) iter.next();
			goalState.addTerm(term);
		}
		
		BeliefBase beliefBase = ts.getAg().getBS();
		List<Term> beliefs = beliefBase.getAllBeliefs();
		for (Term term : beliefs) {
			if(term.getFunctor().startsWith("object")) {
				Term newTerm = Term.parse(term.getTerm(0)+"("+term.getTerm(1)+")");
				objects.addTerm(newTerm);
			}else if( (term.getTermsSize()!= 0) && (!term.getFunctor().startsWith("des"))){
				startState.addTerm(term);
			}
		}
		
		PlanLibrary planLibrary = ts.getAg().getPS();
		List plans = planLibrary.getPlans();
		
		ProblemOperators operators = new ProblemOperators();

		// logger.info("Plans found: ");
		for (Object object : plans) {
			Plan plan = (Plan) object;
			Pred pred = plan.getLabel();
			if (pred != null && pred.toString().startsWith("action")) {
				operators.add(plan);
			}

			// logger.info(plan.toString());
		}
		
		ByteArrayOutputStream factStream = new ByteArrayOutputStream();
		
		/*logger.info(objects.toString());
		logger.info(startState.toString());
		logger.info(goalState.toString());
		logger.info(operators.toString());*/
		factStream.write(objects.toString().getBytes());
		factStream.write(startState.toString().getBytes());
		factStream.write(goalState.toString().getBytes());
		
		logger.fine("Operators:");
		logger.fine(operators.toString());
		
		logger.fine("Facts:");
		logger.fine(factStream.toString());
		
		ByteArrayInputStream opStream = new ByteArrayInputStream(operators.toString().getBytes());
		ByteArrayInputStream fctStream = new ByteArrayInputStream(factStream.toByteArray());
		
		JPlan jplan = new JPlan(opStream, fctStream);
		ByteArrayOutputStream planStream = new ByteArrayOutputStream();
		ByteArrayOutputStream graphStream = new ByteArrayOutputStream();
		OutputStreamWriter planWriter = new OutputStreamWriter(planStream);
		OutputStreamWriter graphWriter = new OutputStreamWriter(graphStream);
		
		jplan.startPlanner(graphWriter, planWriter);
		
		ByteArrayInputStream inStream = new ByteArrayInputStream(planStream.toByteArray());
		InputStreamReader inReader = new InputStreamReader(inStream);
		BufferedReader reader = new BufferedReader(inReader);
		StringBuffer sbNewPlan = new StringBuffer();
		sbNewPlan.append("+!executePlan(plan"+(planNumber)+") : true");
		sbNewPlan.append(System.getProperty("line.separator"));
		sbNewPlan.append("  <- ");
		boolean firstStep = true;
		while(reader.ready()) {
			String planStep = reader.readLine();
			//logger.info("Adding goal: "+planStep);
			//ts.getC().addGoal(Trigger.TEAchvG,Literal.parseLiteral(planStep), null);
			if(firstStep) {
				firstStep = false;
			} else {
				sbNewPlan.append(";");
				sbNewPlan.append(System.getProperty("line.separator"));
			}
			sbNewPlan.append("!");
			sbNewPlan.append(planStep);
		}
		sbNewPlan.append(".");
		
		logger.info("Adding new plan: "+System.getProperty("line.separator")+sbNewPlan.toString());
		ts.getAg().getPS().add(Plan.parse(sbNewPlan.toString()));
		logger.info("Invoking plan"+planNumber);
		ts.getC().addAchvGoal(Literal.parseLiteral("executePlan(plan"+(planNumber++)+")"), null);

		return true;
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
	
	protected abstract class ProblemTerms {
		protected List<Term> terms;
		
		public ProblemTerms() {
			this.terms = new ArrayList<Term>();
		}
		
		public ProblemTerms(List<Term> terms) {
			this.terms = new ArrayList<Term>(terms);
		}
		
		public void addTerm(Term term) {
			terms.add(term);
		}

		public void addAll(List<Term> terms) {
			this.terms.addAll(terms);
		}
		
		public void addAll(Term termsArray[]) {
			for (Term term : termsArray) {
				this.terms.add(term);
			}
		}
	}
	
	protected class ProblemObjects extends ProblemTerms {
		
		public String toString() {
			StringBuffer sb = new StringBuffer();
			
			sb.append("objects:");
			sb.append(System.getProperty("line.separator"));
			for (Term term : terms) {
				sb.append(term.toString());
				sb.append(";");
				sb.append(System.getProperty("line.separator"));
			}
			
			sb.append(System.getProperty("line.separator"));
			
			return sb.toString();
		}
	}

	/**
	 * A class representing the start state of a planning problem
	 */
	protected class StartState extends ProblemTerms{

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("init: ");
			for (Iterator<Term> iter = terms.iterator(); iter.hasNext();) {
				Term term = iter.next();
				sb.append(toStripsString(term));
				if (iter.hasNext()) {
					sb.append(" & ");
				}
			}

			return sb.toString();
		}
	}

	/**
	 * A class representing the goal state of a planning problem
	 */
	protected class GoalState extends ProblemTerms {
		
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("goal: ");
			for (Iterator iter = terms.iterator(); iter.hasNext();) {
				Term term = (Term) iter.next();
				sb.append(term.toString());
				if (iter.hasNext()) {
					sb.append(" & ");
				}
			}

			return sb.toString();
		}
	}
	
	protected class ProblemOperators {
		protected List<Plan> operators;
		
		public ProblemOperators() {
			operators = new ArrayList<Plan>();
		}
		
		public void add(Plan plan) {
			operators.add(plan);
		}
		
		public String toPlainString() {
			StringBuffer sb = new StringBuffer();
			for (Plan plan : operators) {
				sb.append(plan.toString());
			}
			return sb.toString();
		}
		
		/**
		 * This method stinks big time by overriding the one in 
		 * the outter class.
		 * 
		 * @param term A Term to be converted into a STRIPS String
		 * @return A STRIPS-compatible String representation
		 */
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
					if(!termPar.isVar())
						sb.append("@");
					sb.append(toStripsString(termPar));
				}
				sb.append(")");
			}
			
			return sb.toString();
		}
		
		@SuppressWarnings("unchecked")
		public String toString() {
			StringBuffer sb = new StringBuffer();
			
			for (Plan plan : operators) {
				Trigger trigger = plan.getTriggerEvent();
				sb.append("operator: ");
				sb.append(trigger.getLiteral().getFunctor());
				
				//Append operator parameters
				sb.append("(");
				if(trigger.getLiteral().getTermsSize() > 0) {
					Term types[] = plan.getLabel().getTermsArray();
					Term terms[] = trigger.getLiteral().getTermsArray();
					for (int i = 0; i < terms.length; i++) {
						sb.append(types[i]);
						/*sb.append(" ?");
						sb.append(terms[i]);*/
						sb.append(" ");
						sb.append(toStripsString(terms[i]));
						sb.append(((i+1) == terms.length) ? ")" : ", ");
					}
				}
				sb.append(System.getProperty("line.separator"));
				//Append restrictions on objects
				List literals = plan.getContext();
				StringBuffer sbOpContext = new StringBuffer();
				StringBuffer sbOpPreconds = new StringBuffer();
				for (Iterator iter = literals.iterator(); iter.hasNext();) {
					DefaultLiteral literal = (DefaultLiteral) iter.next();
					Term term = literal.getLiteral();
					//If we are dealing with unifies terms, we keep on adding 
					//context information
					if(term.getFunctor().equals(".unifies")) {
						if(sbOpContext.length() != 0) {
							sbOpContext.append(" & ");
						}
						if(!term.getTerm(0).isVar())
							sbOpContext.append("@");
						sbOpContext.append(toStripsString(term.getTerm(0)));
						if(literal.isDefaultNegated()) {
							sbOpContext.append("!");
						}
						sbOpContext.append("=");
						if(!term.getTerm(1).isVar())
							sbOpContext.append("@");
						sbOpContext.append(toStripsString(term.getTerm(1)));
					} else { // Otherwise we are dealing with preconditions
						if(sbOpPreconds.length() != 0) {
							sbOpPreconds.append(" & ");
						}
						sbOpPreconds.append(toStripsString(term));
					}
					
				}
				if(sbOpContext.length() == 0) {
					sb.append("true");
				}else {
					sb.append(sbOpContext);
				}
				sb.append(System.getProperty("line.separator"));
				sb.append("[");
				sb.append(sbOpPreconds);
				sb.append("]");
				sb.append(System.getProperty("line.separator"));
				
				List<BodyLiteral> body = plan.getBody();
				StringBuffer sbPositiveLiterals = new StringBuffer();
				StringBuffer sbNegativeLiterals = new StringBuffer();
				for (BodyLiteral literal : body) {
					if(literal.getType() == BodyLiteral.HDelBel) {
						if(sbNegativeLiterals.length() != 0) {
							sbNegativeLiterals.append(" & ");
						}
						sbNegativeLiterals.append(toStripsString(literal.getLiteral()));
					}else if(literal.getType() == BodyLiteral.HAddBel) {
						if(sbPositiveLiterals.length() != 0) {
							sbPositiveLiterals.append(" & ");
						}
						sbPositiveLiterals.append(toStripsString(literal.getLiteral()));
					}
				}
				
				sb.append("[");
				sb.append(sbPositiveLiterals);
				sb.append("]");
				sb.append(System.getProperty("line.separator"));
				
				sb.append("[");
				sb.append(sbNegativeLiterals);
				sb.append("]");
				sb.append(System.getProperty("line.separator"));
				sb.append(System.getProperty("line.separator"));
			}
			
			sb.append(System.getProperty("line.separator"));
			
			return sb.toString();
		}
	}
}
