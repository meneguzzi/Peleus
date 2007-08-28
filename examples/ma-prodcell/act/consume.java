package act;

import jason.asSyntax.Literal;
import jason.asSyntax.Term;

import java.util.List;

import org.kcl.jason.env.action.ExternalAction;
import org.kcl.jason.env.scripted.ScriptedEnvironment;

public class consume implements ExternalAction<ScriptedEnvironment> {

	public List<Literal> consequences(ScriptedEnvironment env, String agName, Term... terms) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean execute(ScriptedEnvironment env, String agName, Term... terms) {
		if(terms.length != 1) {
			logger.warning("consume action requires one parameter");
			return false;
		} else {
			//XXX No action failure if the checks fail 
			//will need to correct this at some point
			Literal over = Literal.parseLiteral("over("+terms[0]+",depositBelt)");
			if(!env.containsPercept(over)) {
				logger.warning(terms[0]+" is not over depositBelt");
				env.removePercept(over);
			}
			
			Literal notOver = new Literal(false, over);
			Literal empty = Literal.parseLiteral("empty(depositBelt)");
			Literal finished = Literal.parseLiteral("finished("+terms[0]+")");
			
			env.addPercept(notOver);
			env.addPercept(empty);
			env.addPercept(finished);
			return true;
		}
	}

	public String getFunctor() {
		return "consume";
	}

}
