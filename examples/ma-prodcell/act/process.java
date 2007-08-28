package act;

import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import java.util.List;

import org.kcl.jason.env.action.ExternalAction;
import org.kcl.jason.env.scripted.ScriptedEnvironment;

public class process implements ExternalAction<ScriptedEnvironment> {

	public List<Literal> consequences(ScriptedEnvironment env, String agName, Term... terms) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean execute(ScriptedEnvironment env, String agName, Term... terms) {
		if(terms.length != 2) {
			logger.warning("process action requires two parameters");
			return false;
		} else {
			//XXX No action failure if the checks fail 
			//will need to correct this at some point
			Literal precond = Literal.parseLiteral("over("+terms[0]+","+terms[1]+")");
			if(!env.containsPercept(precond)) {
				logger.warning(terms[0]+" is not over "+terms[1]);
			}
			Literal processed = Literal.parseLiteral("processed("+terms[0]+","+terms[1]+")");
			env.addPercept(processed);
			return true;
		}
	}

	public String getFunctor() {
		return "process";
	}

}
