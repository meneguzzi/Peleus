{include("print.asl")}
{include("domain.asl")}
{include("stats.asl")}
//-----------------------------------------------------------------------------
//                   Plans to trigger processing of blocks
//-----------------------------------------------------------------------------

+over(Block, feedBelt1)[source(self)] : true
	<- 	.print("Processing ",Block);
		org.soton.peleus.act.time_in_millis(Time1);
		!finish(Block);
		!writeStats(Time1).

//---------------------------------------
//Cleanup of unnecessary beliefs
//---------------------------------------
+finished(Block) : object(block,Block)
	<-  -object(block,Block)[source(self)];
	   -type(Block,_)[source(self)];
	   -finished(Block)[source(self)];
	   .abolish(processed(Block,_)[source(self)]);
	   ?totalBlocks(B);
	   -+totalBlocks(B+1);
	   .print("Cleaned up beliefs about ", Block).


//-----------------------------------------------------------------------------
//                    East Controller Actions
//-----------------------------------------------------------------------------
@action1(block, procUnit)
+!processWest(Block, ProcUnit) : over(Block, ProcUnit) & west(ProcUnit)
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
+!moveWest(Block, Device1, Device2) 
   : over(Block,Device1) & empty(Device2) & west(Device1) & west(Device2)
   <- .print("Moving ",Block," from ",Device1," to ",Device2);
      //.wait(50);
      +over(Block, Device2);
      -over(Block, Device1);
      -empty(Device2);
      +empty(Device1);
      move(Block, Device1, Device2).

//-----------------------------------------------------------------------------
//                 Plans to act along with multiple agents
//-----------------------------------------------------------------------------

shared([action1,
        action2,
        action3]).

+?sharedOperators(S) : true
   <- .print(S," asked for my operators.");
      ?shared(Ops);
      !sendSharedPlans(Ops,[]).
      
+!sendSharedPlans([],Plans) : true
   <- .print("Sending plans: ",Plans);
      .send(controllerEast,tell,sharedOperators(Plans)).

+!sendSharedPlans([Op | Ops], Plans) : true
   <- .print("Getting plan for label ",Op);
      .plan_label(Plan,Op);
      .print("Plan is ",Plan);
      !sendSharedPlans(Ops, [Plan | Plans]).

//{include("controllerActions.asl")}
{include("peleus.asl")}