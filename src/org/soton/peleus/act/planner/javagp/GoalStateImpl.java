package org.soton.peleus.act.planner.javagp;

import graphplan.domain.Proposition;
import graphplan.domain.jason.PropositionImpl;
import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.List;

import org.soton.peleus.act.planner.GoalState;

public class GoalStateImpl extends GoalState {
	protected final List<Proposition> goalState;
	
	public GoalStateImpl(List<Term> goals) {
		goalState = new ArrayList<Proposition>(goals.size());
		for(Term term : goals) {
			PropositionImpl proposition = new PropositionImpl(term.toString());
			proposition.clearAnnots();
			goalState.add(proposition);
		}
	}
	
	public List<Proposition> getGoalState() {
		return this.goalState;
	}

	@Override
	public String toPlannerString() {
		return goalState.toString();
	}

}
