package prodcell;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.Logger;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Term;

public class recordStats extends DefaultInternalAction {
	private static final Logger logger = Logger.getLogger(DefaultInternalAction.class.getName());

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		if(args.length < 3 ) {
			logger.info("Wrong number of arguments for stats.");
			return false;
		}
		
		NumberTerm arg0 = (NumberTerm) args[0];
		NumberTerm arg1 = (NumberTerm) args[1];
		
		int blocks = (int) arg0.solve();
		double time = arg1.solve()/1000;
		
		String filename = args[2].toString();
		filename = filename.replaceAll("\"", "");
		//filename = filename + waypoints + ".txt";
		filename = filename + ".txt";
		
		//System.out.println("Will write to file" + filename);
		File file = new File(filename);
		FileWriter writer = new FileWriter(file, true);
		writer.write(""+blocks+" "+time+System.getProperty("line.separator"));
		writer.close();
		
		return true;
	}
}
