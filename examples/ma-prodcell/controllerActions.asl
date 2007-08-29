//-----------------------------------------------------------------------------
//                    Common Controller Actions
//-----------------------------------------------------------------------------
@action1(block, procUnit)
+!process(Block, ProcUnit) : over(Block, ProcUnit)
	<- .print("Processing ",Block," in ",ProcUnit);
		//.wait(50);
	   +processed(Block, ProcUnit);
	   process(Block, ProcUnit).

@action2(block)
+!consume(Block) : over(Block,depositBelt)
	<- .print("Consuming ",Block);
	   //.wait(50);
	   -over(Block, depositBelt);
	   +empty(depositBelt);
	   +finished(Block);
	   consume(Block).

@action3(block, device, device)
+!move(Block, Device1, Device2) : over(Block,Device1) & empty(Device2)
	<- .print("Moving ",Block," from ",Device1," to ",Device2);
		//.wait(50);
	   +over(Block, Device2);
	   -over(Block, Device1);
	   -empty(Device2);
	   +empty(Device1);
	   move(Block, Device1, Device2).
