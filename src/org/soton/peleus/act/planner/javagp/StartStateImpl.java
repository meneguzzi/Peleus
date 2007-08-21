package org.soton.peleus.act.planner.javagp;

import graphplan.domain.Proposition;
import graphplan.domain.jason.PropositionImpl;
import jason.asSyntax.Literal;

import java.util.ArrayList;
import java.util.List;

import org.soton.peleus.act.planner.StartState;

public class StartStateImpl extends StartState {
	
	protected final List<Proposition> startState;
	
	public StartStateImpl(List<Literal> beliefs) {
		startState = new ArrayList<Proposition>(beliefs.size());
		for(Literal literal : beliefs) {
			if(literal.getFunctor().startsWith("object")) {
				PropositionImpl proposition = new PropositionImpl(literal.getTerm(0)+"("+literal.getTerm(1)+")");
				startState.add(proposition);
			}else if( (literal.getTermsSize()!= 0) && (!literal.getFunctor().startsWith("des"))){
				PropositionImpl proposition = new PropositionImpl(literal);
				proposition.clearAnnots();
				startState.add(proposition);
			}
		}
	}
	
	public List<Proposition> getStartState() {
		return this.startState;
	}

	@Override
	public String toPlannerString() {
		return startState.toString();
	}

}
