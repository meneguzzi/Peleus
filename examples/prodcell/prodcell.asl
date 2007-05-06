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


//-------------------------



//-------------------------

//Trigger to process a bloc
//+over(block1, feedBelt) : true
//<- +des([processed(block1, procUnit1), processed(block1, procUnit2)]).
//  <- +des([processed(block1, procUnit1), processed(block1, procUnit2), processed(block1, procUnit3)]).

/** To stop the simulation and kill the agent*/
+endSimulation : true
	<- 	.print("Simulation is over, stopping MAS.");
		.stopMAS.

+over(Block, feedBelt) : true
	<- 	.print("Processing ",Block);
		!finish(Block).
	
+!finish(Block) : type(Block, type1)
	<- +des([processed(Block, procUnit1), 
	         processed(Block, procUnit2), 
	         processed(Block, procUnit3), 
	         finished(Block)]).

+!finish(Block) : type(Block, type2)
	<- +des([processed(Block, procUnit2), 
	         processed(Block, procUnit4), 
	         finished(Block)]).

+!finish(Block) : type(Block, type3)
	<- +des([processed(Block, procUnit1), 
	         processed(Block, procUnit3), 
	         finished(Block)]).

//Cleanup of the no longer needed beliefs
+finished(Block) : object(block,Block)
	<- -object(block,Block)[_];
	   -type(Block,_)[_];
	   -finished(Block)[_];
	   -over(Block,feedBelt)[_];
	   .abolish(processed(Block,_)[_]);
	   .print("Cleaned up beliefs about ", Block).


//Planning Plan
+des(Goals) : true
	<- org.soton.peleus.act.plan(Goals,10);
	   .print("Goals ",Goals," were satisfied").

//Actions
@action1(block, procUnit)
+!process(Block, ProcUnit) : over(Block, ProcUnit)
	<- .print("Processing ",Block," in ",ProcUnit);
	   +processed(Block, ProcUnit).

@action2(block)
+!consume(Block) : over(Block,depositBelt)
	<- .print("Consuming ",Block);
	   -over(Block, depositBelt);
	   +empty(depositBelt);
	   +finished(Block).

@action3(block, device, device)
+!move(Block, Device1, Device2) : over(Block,Device1) & empty(Device2)
	<- .print("Moving ",Block," from ",Device1," to ",Device2);
	   +over(Block, Device2);
	   -over(Block, Device1);
	   -empty(Device2);
	   +empty(Device1).
