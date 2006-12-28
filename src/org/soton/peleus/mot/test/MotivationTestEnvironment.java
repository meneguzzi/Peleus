package org.soton.peleus.mot.test;

import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;

import java.util.ArrayList;
import java.util.List;

import org.soton.peleus.test.TestEnvironment;

public class MotivationTestEnvironment extends TestEnvironment {

	public MotivationTestEnvironment() {
		super();
		this.actions = new MotivationEnvironmentActions(this);
	}

	protected Literal findLiteralByFunctor(String key, List<Literal> literals) {
		if(literals == null)
			return null;
		for (Literal literal : literals) {
			if(literal.getFunctor().equals(key)) {
				return literal;
			}
		}
		return null;
	}

	protected List<Literal> findLiteralsByFunctor(String key, List<Literal> literals) {
		List <Literal> ret = new ArrayList<Literal>();
		for (Literal literal : literals) {
			if(literal.getFunctor().equals(key)) {
				ret.add(literal);
			}
		}
		return ret;
	}

	protected Literal findMatchingLiteral(Literal prototype, List<Literal> literals) {
		if(literals == null) {
			return null;
		}
		Unifier unifier = new Unifier();
		for (Literal literal : literals) {
			if(unifier.unifies(prototype, literal))
				return literal;
			unifier.clear();
		}
		return null;
	}
}
