/**
 * 
 */
package org.soton.peleus.act.planner.jemplan;

import jason.asSyntax.BodyLiteral;
import jason.asSyntax.DefaultLiteral;
import jason.asSyntax.Plan;
import jason.asSyntax.Term;
import jason.asSyntax.Trigger;

import java.util.Iterator;
import java.util.List;

import org.soton.peleus.act.planner.ProblemOperators;

/**
 * @author Felipe Meneguzzi
 *
 */
public class ProblemOperatorsImpl extends ProblemOperators {
	
	private final EMPlanPlannerConverter converter;
	
	public ProblemOperatorsImpl(EMPlanPlannerConverter converter) {
		this.converter = converter;
	}

	/* (non-Javadoc)
	 * @see org.soton.peleus.act.planner.ProblemOperators#toPlannerString()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String toPlannerString() {
		StringBuffer sb = new StringBuffer();
		
		for (Plan plan : operators) {
			Trigger trigger = plan.getTriggerEvent();
			sb.append("operator ");
			sb.append(trigger.getLiteral());
			
			/*sb.append("(");
			if(trigger.getLiteral().getTermsSize() > 0) {
				Term types[] = plan.getLabel().getTermsArray();
				Term terms[] = trigger.getLiteral().getTermsArray();
				for (int i = 0; i < terms.length; i++) {
					sb.append(types[i]);
					//sb.append(" ?");
					//sb.append(terms[i]);
					sb.append(" ");
					sb.append(toStripsString(terms[i]));
					sb.append(((i+1) == terms.length) ? ")" : ", ");
				}
			}*/
			sb.append(System.getProperty("line.separator"));
			//Append preconditions
			sb.append("preconds (");
			List literals = plan.getContext();
			StringBuffer sbOpPreconds = new StringBuffer();
			
			//The constraints to the types are represented as preconditions
			if(trigger.getLiteral().getTermsSize() > 0) {
				Term types[] = plan.getLabel().getTermsArray();
				Term terms[] = trigger.getLiteral().getTermsArray();
				for (int i = 0; i < terms.length; i++) {
					sb.append(types[i]);
					sb.append("(");
					sb.append(converter.toStripsString(terms[i]));
					sb.append(")");
					if((i+1) != terms.length) {
						sb.append(", ");
					}
				}
				if(literals.size() > 0)
					sb.append(", ");
			}
			
			for (Iterator iter = literals.iterator(); iter.hasNext();) {
				DefaultLiteral literal = (DefaultLiteral) iter.next();
				Term term = literal.getLiteral();
				if(sbOpPreconds.length() != 0) {
					sbOpPreconds.append(", ");
				}
				sbOpPreconds.append(converter.toStripsString(term));
				
			}
			if(sbOpPreconds.length() == 0) {
				sb.append("true");
			}else {
				sb.append(sbOpPreconds);
			}
			sb.append(")");
			sb.append(System.getProperty("line.separator"));
			
			List<BodyLiteral> body = plan.getBody();
			StringBuffer sbEffects = new StringBuffer();
			for (BodyLiteral literal : body) {
				if(literal.getType() == BodyLiteral.HDelBel) {
					if(sbEffects.length() != 0) {
						sbEffects.append(", ");
					}
					sbEffects.append("-");
					sbEffects.append(converter.toStripsString(literal.getLiteral()));
				}else if(literal.getType() == BodyLiteral.HAddBel) {
					if(sbEffects.length() != 0) {
						sbEffects.append(", ");
					}
					sbEffects.append(converter.toStripsString(literal.getLiteral()));
				}
			}
			
			sb.append("effects (");
			if(sbEffects.length() > 0) {
				sb.append(sbEffects);
			} else {
				sb.append("true");
			}
			sb.append(")");
			
			sb.append(System.getProperty("line.separator"));
			sb.append(System.getProperty("line.separator"));
		}
		
		sb.append(System.getProperty("line.separator"));
		
		return sb.toString();
	}

}
