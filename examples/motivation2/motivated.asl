empty(procUnit1).
empty(procUnit2).
empty(procUnit3).
empty(procUnit4).
empty(depositBelt).

/*over(block1, feedBelt).
over(block2, feedBelt).
over(block3, feedBelt).*/

/*
+over(Block, feedBelt) : true
	<-.print("Boo ", Block).*/

/*@breakpoint
+over(Block, feedBelt) : true
	<- 	.print("Processing ",Block);
		!finish(Block).*/
	
+!finish(Block) : Block = block1
	<- .print("Processing ",Block);
	   !process(Block, procUnit1);
	   !process(Block, procUnit2);
	   !process(Block, procUnit3);
	   !move(Block, procUnit3, depositBelt);
	   !consume(Block).

+!finish(Block) : Block = block2
	<- !process(Block, procUnit2);
	   !process(Block, procUnit4);
	   !move(Block, procUnit4, depositBelt);
	   !consume(Block).

+!finish(Block) : Block = block3
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