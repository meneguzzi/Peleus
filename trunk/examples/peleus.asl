// Peleus in project Argos.mas2j
demo.
demo2.

//@object[block(b1)]
object(block, b1).
//@object[block(b2)]
object(block, b2).
//@object[block(b3)]
object(block, b3).
//@object[block(table)]
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

//+demo : true <- .print("hello world.").
//+demo2 : true <- org.soton.peleus.act.plan.
+des(Goals) : true
	<- org.soton.peleus.act.plan(Goals);
	   !checkGoals(Goals);
	   .print("Goals ",Goals," were satisfied").


/*+!checkGoals([H | T]) : true <- .print("Bloody Hell ",H).

+!checkGoals(Goals) : true <- .print("Bloody Hell ",Goals).*/

+!checkGoals([]) : true <- true.

+!checkGoals([H|T]) : true
  <- .print("Checking ", H);
  	 org.soton.peleus.act.isTrue(H);
	 !checkGoals(T).
  
  
  
@action1(block, block)[]
+!moveToTable (Block, From) :
  not Block = From & 
  not Block = table & 
  not From = table &
  clear(Block) & on(Block, From)
 <- -on(Block, From);
    +on(Block, table);
    +clear(From).

@action2(block, block, block)
+!move(Block, From, To) :
   not Block = From & 
   not Block = To &
   not From = To &
   not To = table &
   on(Block, From) &
   clear(Block) &
   clear(To)
  <- -on(Block, From);
     -clear(To);
     +on(Block, To);
     +clear(From).

-on(Block, From) : true
 <- .print("Removing belief on(",Block,",",From,")").

+on(Block, From) : true
 <- .print("Adding belief on(",Block,",",From,")").

-clear(Block) : true
 <- .print("Removing belief clear(",Block,")").

+clear(Block) : true
 <- .print("Adding belief clear(",Block,")").