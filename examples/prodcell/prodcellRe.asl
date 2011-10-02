//Instructions to Graphplan
object(procUnit, procUnit1).
object(procUnit, procUnit2).
object(procUnit, procUnit3).
object(procUnit, procUnit4).
object(device, procUnit1).
object(device, procUnit2).
object(device, procUnit3).
object(device, procUnit4).
object(device, depositBelt).
object(device, feedBelt).

totalTime(0).
totalBlocks(0).


//-------------------------

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
//+empty(Device) [source(percept)] : true
//	<- +empty(Device).

//+object(Type, Object) [source(percept)] : not object(Type, Object) [source(self)]
//	<- +object(Type, Object).

//+type(Block, Type) [source(percept)] : not type(Block, Type) [source(self)]
//	<- +type(Block, Type).

//+over(Object, Device) [source(percept)] : not over(Object, Device)[source(self)]
//	<-  .print("Acknowledging ",over(Object, Device));
//		+over(Object, Device).

//+over(Block, Device) : true
//	<- .print("Perceived ",over(Block, Device)).

// +P[source(percept)] : true
//	<- +P.
//-P[source(percept)] : true
//	<- -P[source(self)].

//----------------------------------------------------------

+over(Block, feedBelt)[source(percept)] : true
	<- 	.print("Processing ",Block);
		org.soton.peleus.act.time_in_millis(Time1);
		!finish(Block);
		org.soton.peleus.act.time_in_millis(Time2);
		PlanningTime = Time2 - Time1;
		.print("Planning took ",PlanningTime," miliseconds");
		?totalTime(T);
		TotalTime = T + PlanningTime;
		-+totalTime(TotalTime).

+!finish(Block) : type(Block, type1)
	<- !goalConj([processed(Block, procUnit1), 
	         processed(Block, procUnit2), 
	         processed(Block, procUnit3), 
	         finished(Block)]).

+!finish(Block) : type(Block, type2)
	<- !goalConj([processed(Block, procUnit2), 
	         processed(Block, procUnit4), 
	         finished(Block)]).

+!finish(Block) : type(Block, type3)
	<- !goalConj([processed(Block, procUnit1), 
	         processed(Block, procUnit3), 
	         finished(Block)]).

//Cleanup of the no longer needed beliefs
+finished(Block) : object(block,Block)
	<-  -object(block,Block)[_];
	   -type(Block,_)[_];
	   -finished(Block);
	   .abolish(processed(Block,_)[_]);
	   ?totalBlocks(B);
	   -+totalBlocks(B+1);
	   .print("Cleaned up beliefs about ", Block).


//Planning Plan
+!goalConj(Goals) : true
	<- org.soton.peleus.act.plan(Goals,[maxSteps(10),makeGeneric(false)]);
	   .print("Goals ",Goals," were satisfied").
	   
-!goalCong(Goals) : true
    <- .print("Failed to achieve ",Goals).

//Actions
@action1(block, procUnit)
+!process(Block, ProcUnit) : over(Block, ProcUnit)
	<- .print("Processing ",Block," in ",ProcUnit);
		//.wait(50);
		process(Block,ProcUnit);
	   ?processed(Block,ProcUnit).

@action2(block)
+!consume(Block) : over(Block,depositBelt)
	<- .print("Consuming ",Block);
	   //.wait(50);
	   consume(Block);
	   ?not(over(Block, depositBelt));
	   ?empty(depositBelt);
	   ?finished(Block).

@action3(block, device, device)
+!move(Block, Device1, Device2) : over(Block,Device1) & empty(Device2)
	<- .print("Moving ",Block," from ",Device1," to ",Device2);
		//.wait(50);
	   move(Block, Device1, Device2);
	   ?over(Block, Device2);
	   ?not(over(Block, Device1));
	   ?not(empty(Device2));
	   ?empty(Device1).
