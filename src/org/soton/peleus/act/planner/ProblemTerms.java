/**
 * 
 */
package org.soton.peleus.act.planner;

import jason.asSyntax.Term;

import java.util.ArrayList;
import java.util.List;

public abstract class ProblemTerms {
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