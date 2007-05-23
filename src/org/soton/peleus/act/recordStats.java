package org.soton.peleus.act;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.Logger;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Term;

/**
 * <p>
 * Internal action: <b><code>.recordStats(File, Key, ...)</code></b>.
 * 
 * <p>
 * Description: records stats in the specified file.
 * 
 * <p>
 * Parameters:
 * <ul>
 * 
 * <li>+ File (string): the file into which stats will be written.</li>
 * <li>+ Key (number): the key to the stats line being written.</li>
 * <li>+ ...: Any number of items to be written.</li>
 * 
 * </ul>
 * 
 * <p>
 * Examples:
 * <ul>
 * 
 * <li> <code>.recordStats("stats", 10, 2.15)</code>: Writes a line in the stats.txt file.</li>
 * 
 * </ul>
 * 
 * @author Felipe
 * 
 */
public class recordStats extends DefaultInternalAction {
	private static final Logger logger = Logger.getLogger(DefaultInternalAction.class.getName());

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		if(args.length < 3 ) {
			logger.info("Stats requires at least 3 arguments.");
			return false;
		}
		String filename = args[0].toString();
		filename = filename.replaceAll("\"", "");
		if(!filename.endsWith(".txt")) {
			filename = filename + ".txt";
		}
		
		StringBuffer sbLine = new StringBuffer();
		
		for(int i=1; i<args.length; i++) {
			if(args[i].isNumeric()) {
				NumberTerm term = (NumberTerm) args[i];
				sbLine.append(term.solve()+" ");
			} else {
				sbLine.append(args[i].toString()+" ");
			}
		}
				
		
		//System.out.println("Will write to file" + filename);
		File file = new File(filename);
		FileWriter writer = new FileWriter(file, true);
		writer.write(sbLine.toString()+System.getProperty("line.separator"));
		writer.close();
		
		return true;
	}
}
