package org.soton.peleus.mot;

import java.util.logging.Logger;
import org.soton.peleus.mot.impl.GoalGenerationFunctionImpl;
import org.soton.peleus.mot.impl.IntensityUpdateFunctionImpl;
import org.soton.peleus.mot.impl.MitigationFunctionImpl;

/**
 * @author  meneguzz
 */
public class MotivationFactory {
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MotivationFactory.class.getName());
	
	private static MotivationFactory factorySingleton;
	
	private MotivationFactory() {
		
	}
	
	public static MotivationFactory getMotivationFactory() {
		if(factorySingleton == null) {
			factorySingleton = new MotivationFactory();
		}
		
		return factorySingleton;
	}
	
	/**
	 * Creates a new Motivation
	 * @param name
	 * @return
	 */
	public Motivation createMotivation(String name) {
		logger.fine("Creating motivation: '"+name+"'");
		return createMotivation(name, 10,
									  createIntensityUpdateFunction(),
									  createGoalGenerationFunction(),
									  createMitigationFunction());
	}
	
	/**
	 * Creates a motivation using the supplied components.
	 * 
	 * @param name
	 * @param intensityUpdateFunction
	 * @param generationFunction
	 * @param mitigationFunction
	 * @return
	 */
	public Motivation createMotivation(String name,
									   int threshold,
									   IntensityUpdateFunction intensityUpdateFunction,
									   GoalGenerationFunction generationFunction,
									   MitigationFunction mitigationFunction) {
		return new Motivation(name, threshold,
									intensityUpdateFunction, 
									generationFunction, 
									mitigationFunction);
	}
	
	protected <K> K instantiateClass(Class<K> classType, String classname) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class c = Class.forName(classname);
		
		K newObject = null;
		if(classType.isAssignableFrom(c)) {
			newObject = (K) c.newInstance();
		} else {
			throw new InstantiationException();
		}
		return newObject;
	}
	
	/**
	 * Returns a new <code>IntensityUpdateFunction</code> given the supplied description.
	 *  
	 * @param classname The description for the creation of this function.
	 * @return
	 */
	public IntensityUpdateFunction createIntensityUpdateFunction(String classname) {
		try {
			IntensityUpdateFunction function = instantiateClass(IntensityUpdateFunction.class,
																classname);
			return function;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns a new <code>IntensityUpdateFunction</code> using the default implementation.
	 *  
	 * @return
	 */
	public IntensityUpdateFunction createIntensityUpdateFunction() {
		return new IntensityUpdateFunctionImpl();
	}
	
	/**
	 * Returns a new <code>GoalGenerationFunction</code> given the supplied description.
	 * @param function The description for the creation of this function.
	 * @return
	 */
	public GoalGenerationFunction createGoalGenerationFunction(String classname) {
		GoalGenerationFunction function;
		try {
			function = instantiateClass(GoalGenerationFunction.class, 
																classname);
			return function;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns a new <code>GoalGenerationFunction</code> using the default implementation.
	 * @return
	 */
	public GoalGenerationFunction createGoalGenerationFunction() {
		return new GoalGenerationFunctionImpl();
	}
	
	/**
	 * Returns a new <code>MitigationFunction</code> given the supplied description.
	 * @param function The description for the creation of this function.
	 * @return
	 */
	public MitigationFunction createMitigationFunction(String classname) {
		try {
			MitigationFunction function = instantiateClass(MitigationFunction.class, 
															classname);
			return function;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Returns a new <code>MitigationFunction</code> using the default implementation.
	 * @return
	 */
	public MitigationFunction createMitigationFunction() {
		return new MitigationFunctionImpl();
	}
}
