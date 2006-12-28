/**
 * 
 */
package org.soton.peleus.test;

import jason.asSemantics.Unifier;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.environment.Environment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.soton.peleus.script.JasonScript;
import org.soton.peleus.script.JasonScriptContentHandler;
import org.xml.sax.SAXException;

/**
 * @author Felipe Rech Meneguzzi
 *
 */
public class MotivationTestEnvironment extends Environment implements Runnable {
	protected Logger logger = Logger.getLogger(MotivationTestEnvironment.class.getName());
	
	protected JasonScript script = null;
	
	protected boolean running;
	
	protected Thread environmentThread;
	
	protected int cycleSize;
	
	protected int currentCycle;
	
	protected EnvironmentActions actions;
	
	public MotivationTestEnvironment() {
		this.running = false;
		this.environmentThread = new Thread(this, "TestEnvironment");
		this.cycleSize = 1000;
		this.currentCycle = 0;
		this.actions = new MotivationEnvironmentActions(this);
	}
	@Override
	public void init(String[] args) {
		super.init(args);
		clearPercepts();
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			JasonScriptContentHandler contentHandler = new JasonScriptContentHandler();
			if(args.length > 0) {
				File scriptFile = new File(args[0]);
				if(scriptFile.exists()) {
					logger.info("Reading script file: "+args[0]);
					parser.parse(scriptFile, contentHandler);
					this.script = contentHandler.getJasonScript();
					this.running = true;
					environmentThread.start();
				}
			} 
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		this.running = false;
	}
	
	public void addPercepts(List<Literal> list) {
		for (Literal literal : list) {
			logger.info("Adding percept: "+literal);
			addPercept(literal);
		}
	}
	
	@Override
	public boolean executeAction(String agName, Term act) {
		//return super.executeAction(agName, act);
		return this.actions.executeAction(agName, act);
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

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public synchronized void run() {
		while (running) {
			try {
				wait(cycleSize);
				if(script.getEvents(currentCycle) != null) {
					this.addPercepts(script.getPercepts(currentCycle));
				}
				currentCycle++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
