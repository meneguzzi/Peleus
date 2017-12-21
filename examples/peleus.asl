//---------------Initial states-----------------------------
object(block, b1).
object(block, b2).
object(block, b3).
object(block, table).

on(b1, b2).
on(b2, table).
on(b3, table).
clear(b1).
clear(b3).
clear(table).

//---------------Desires------------------------------------
des([on(b3, table), on(b2, b3), on(b1, b2)]).

//---------------Plans--------------------------------------
@p1[atomic]+des(Goals) : true <-  
	.print("Goals to plan: ",Goals);
	org.soton.peleus.act.plan(Goals,[maxSteps(10),makeGeneric(false)]);
	!checkGoals(Goals);
	.print("Goals ",Goals," were satisfied").

+!checkGoals([]) : true <-
	.print("checkGoals with empty list.").

@p2[atomic]+!checkGoals([H|T]) : true <-
	.print("Checking ", H);
  	org.soton.peleus.act.isTrue(H);
  	.print("List to be checked later: ", T);
	!checkGoals(T).

@p3[atomic]+!checkGoals(H) : true <-
	.print("Checking ", H);
  	org.soton.peleus.act.isTrue(H).
	
-!checkGoals([H|T]) : true <- .print("Failure.").

@action1(block, block)[] +!moveToTable (Block, From) : 
	Block \== From & Block \== table & 
	From \== table & clear(Block) & on(Block, From) <- 
	-on(Block, From);
    +on(Block, table);
    +clear(From);
    .print("Now ", Block, " is on the table. ",From, " is clear.").	

@action2(block, block, block) +!move(Block, From, To) : 
	Block \== From & Block \== To &
	From \== To & To \== table &
	on(Block, From) & clear(Block) &
	clear(To) <- 
	-on(Block, From);
	-clear(To);
	+on(Block, To);
	+clear(From);
	.print("Now ", Block, " is on ", To, ". ",From, " is clear.").

-on(Block, From) <- 
	.print("Removing belief on(",Block,",",From,")").

+on(Block, From) <- 
	.print("Adding belief on(",Block,",",From,")").

-clear(Block) <- 
	.print("Removing belief clear(",Block,")").

+clear(Block) <- 
	.print("Adding belief clear(",Block,")").