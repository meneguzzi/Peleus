/**
 * 
 */
package org.soton.peleus.test;

import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.environment.Environment;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.soton.peleus.script.JasonScript;
import org.soton.peleus.script.JasonScriptContentHandler;
import org.xml.sax.SAXException;

/**
 * A Jason Environment class that allows the <em>scripts</em> of events to be used
 * for testing purposes.
 * 
 * @author Felipe Meneguzzi
 *
 */
public abstract class TestEnvironment extends Environment implements Runnable {
	protected Logger logger = Logger.getLogger(TestEnvironment.class.getName());
	
	protected JasonScript script = null;
	
	protected boolean running;
	
	protected Thread environmentThread;
	
	protected int cycleSize;
	
	protected int currentCycle;
	
	protected EnvironmentActions actions = null;
	
	protected TestEnvironment() {
		this.running = false;
		this.environmentThread = new Thread(this, "TestEnvironment");
		this.cycleSize = 1000;
		this.currentCycle = 0;
	}
	
	public TestEnvironment(EnvironmentActions actions) {
		this();
		this.actions = actions;
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
