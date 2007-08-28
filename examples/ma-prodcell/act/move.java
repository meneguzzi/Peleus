package act;

import jason.asSyntax.Literal;
import jason.asSyntax.Term;

import java.util.List;

import org.kcl.jason.env.action.ExternalAction;
import org.kcl.jason.env.scripted.ScriptedEnvironment;

public class move implements ExternalAction<ScriptedEnvironment> {

	public List<Literal> consequences(ScriptedEnvironment env, String agName, Term... terms) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	+over(Block, Device2);
//	-over(Block, Device1);
//	-empty(Device2);
//	+empty(Device1)

	public boolean execute(ScriptedEnvironment env, String agName, Term... terms) {
		if(terms.length != 3) {
			logger.warning("move action requires three parameters");
			return false;
		} else {
			Literal over1 = Literal.parseLiteral("over("+terms[0]+","+terms[1]+")");
			Literal empty1 = Literal.parseLiteral("empty("+terms[2]+")");
			if(!env.containsPercept(over1)) {
				logger.warning(terms[0]+" is not over "+terms[1]);
			}
			if(!env.containsPercept(empty1)) {
				logger.warning(terms[2]+" is not empty");
			}
			Literal over = Literal.parseLiteral("over("+terms[0]+","+terms[2]+")");
			Literal notOver = Literal.parseLiteral("~over("+terms[0]+","+terms[1]+")");
			
			Literal empty = Literal.parseLiteral("empty("+terms[1]+")");
			Literal notEmpty = Literal.parseLiteral("~empty("+terms[2]+")");
			
			env.addPercept(over);
			env.addPercept(notOver);
			env.addPercept(empty);
			env.addPercept(notEmpty);
			
			return true;
		}
	}

	public String getFunctor() {
		return "move";
	}

}
