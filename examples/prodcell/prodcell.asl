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
object(block, block1).
object(block, block2).
object(block, block3).

//-------------------------

//Agent beliefs
empty(procUnit1).
empty(procUnit2).
empty(procUnit3).
empty(procUnit4).
empty(depositBelt).

//-------------------------

over(block1, feedBelt).
over(block2, feedBelt).
over(block3, feedBelt).

//Trigger to process a bloc
+over(block1, feedBelt) : true
//<- +des([processed(block1, procUnit1), processed(block1, procUnit2)]).
  <- +des([processed(block1, procUnit1), processed(block1, procUnit2), processed(block1, procUnit3)]).


+over(Block, feedBelt) : true
	<- 	.print("Processing ",Block);
		!finish(Block).
	
+!finish(Block) : Block = block1
	<- +des([processed(Block, procUnit1), 
	         processed(Block, procUnit2), 
	         processed(Block, procUnit3), 
	         finished(Block)]).

+!finish(Block) : Block = block2
	<- +des([processed(Block, procUnit2), 
	         processed(Block, procUnit4), 
	         finished(Block)]).

+!finish(Block) : Block = block3
	<- +des([processed(Block, procUnit1), 
	         processed(Block, procUnit3), 
	         finished(Block)]).


//Planning Plan
+des(Goals) : true
	<- org.soton.peleus.act.plan(Goals,5);
	   .print("Goals ",Goals," were satisfied").

//Actions
@action1(block, procUnit)
+!process(Block, ProcUnit) : over(Block, ProcUnit)
	<- +processed(Block, ProcUnit).

@action2(block)
+!consume(Block) : over(Block,depositBelt)
	<- -over(Block, depositBelt);
	   +empty(depositBelt);
	   +finished(Block).

@action3(block, device, device)
+!move(Block, Device1, Device2) : over(Block,Device1) & empty(Device2)
	<- +over(Block, Device2);
	   -over(Block, Device1);
	   -empty(Device2);
	   +empty(Device1).
