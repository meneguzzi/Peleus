/**
 * 
 */
package org.soton.peleus.act.planner.jplan;

import jason.asSyntax.BodyLiteral;
import jason.asSyntax.DefaultLiteral;
import jason.asSyntax.Plan;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;

import java.util.Iterator;
import java.util.List;

import org.soton.peleus.act.planner.ProblemOperators;

public class ProblemOperatorsImpl extends ProblemOperators{
	
	public ProblemOperatorsImpl() {
		super();
	}

	@Override
	public String toPlannerString() {
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
			
			if(sbPositiveLiterals.length() > 0) {
				sb.append("[");
				sb.append(sbPositiveLiterals);
				sb.append("]");
			} else {
				sb.append("true");
			}
			sb.append(System.getProperty("line.separator"));
			
			if(sbNegativeLiterals.length() > 0) {
				sb.append("[");
				sb.append(sbNegativeLiterals);
				sb.append("]");
			} else {
				sb.append("true");
			}
			sb.append(System.getProperty("line.separator"));
			sb.append(System.getProperty("line.separator"));
		}
		
		sb.append(System.getProperty("line.separator"));
		
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
}