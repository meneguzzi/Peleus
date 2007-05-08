totalTime(0).
totalBlocks(0).

/** To stop the simulation and kill the agent*/
+endSimulation : true
	<- !endSimulation.

+!endSimulation : true
	<- .print("Simulation is over, stopping MAS.");
		?totalBlocks(B);
		.print("Processed ",B," blocks");
		?totalTime(T);
		.print("Total time planning was ",T," milliseconds");
		prodcell.recordStats(B,T,"stats");
		.stopMAS.

//----------------------------------------------------------
// Plans to update the Belief Base and optimise the testing
+empty(Device) [source(percept)] : true
	<- +empty(Device).

+object(Type, Object) [source(percept)] : not object(Type, Object) [source(self)]
	<- +object(Type, Object).

+type(Block, Type) [source(percept)] : not type(Block, Type) [source(self)]
	<- +type(Block, Type).

+over(Object, Device) [source(percept)] : not over(Object, Device)[source(self)]
	<-  .print("Acknowledging ",over(Object, Device));
		+over(Object, Device).


//----------------------------------------------------------

@breakpoint
+over(Block, feedBelt)[source(self)] : true
	<- 	.print("Processing ",Block);
		org.soton.peleus.act.time_in_millis(Time1);
		!finish(Block);
		org.soton.peleus.act.time_in_millis(Time2);
		PlanningTime = Time2 - Time1;
		.print("Planning took ",PlanningTime," miliseconds");
		?totalTime(T);
		TotalTime = T + PlanningTime;
		-+totalTime(TotalTime).

//Cleanup of the no longer needed beliefs
+finished(Block) : true
	<- -finished(Block)[_];
	   //-over(Block,feedBelt)[_];
	   .abolish(processed(Block,_));
	   ?totalBlocks(B);
	   -+totalBlocks(B+1);
	   .print("Cleaned up beliefs about ", Block).
	   
	
+!finish(Block) : type(Block,type1)
	<- !process(Block, procUnit1);
	   !process(Block, procUnit2);
	   !process(Block, procUnit3);
	   !move(Block, procUnit3, depositBelt);
	   !consume(Block).

+!finish(Block) : type(Block,type2)
	<- !process(Block, procUnit2);
	   !process(Block, procUnit4);
	   !move(Block, procUnit4, depositBelt);
	   !consume(Block).

+!finish(Block) : type(Block,type3)
	<- !process(Block, procUnit1);
	   !process(Block, procUnit3);
	   !move(Block, procUnit3, depositBelt);
	   !consume(Block).

+!process(Block, ProcUnit) : not over(Block, ProcUnit) 
						  & empty(ProcUnit)
						  & over(Block, Device)
	<- !move(Block, Device, ProcUnit);
	   !process(Block, ProcUnit).


+!process(Block, ProcUnit) : over(Block, ProcUnit)
	<-  .print("Processing ", Block, " in ", ProcUnit);
		+processed(Block, ProcUnit).

+!consume(Block) : over(Block,depositBelt)
	<- .print("Consuming ", Block);
	   -over(Block, depositBelt);
	   +empty(depositBelt);
	   +finished(Block).

+!move(Block, Device1, Device2) : over(Block,Device1) & empty(Device2)
	<- .print("Moving ",Block, " from ", Device1, " to ", Device2);
	   +over(Block, Device2);
	   -over(Block, Device1);
	   -empty(Device2);
	   +empty(Device1).