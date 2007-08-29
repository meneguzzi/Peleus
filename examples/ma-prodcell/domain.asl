//-----------------------------------------------------------------------------
//                Domain-related Instructions to Graphplan
//-----------------------------------------------------------------------------
object(procUnit, pu1).
object(procUnit, pu2).
object(procUnit, pu3).
object(procUnit, pu4).
object(device, pu1).
object(device, pu2).
object(device, pu3).
object(device, pu4).
object(device, depositBelt).
object(device, transferBelt).
object(device, feedBelt1).
object(device, feedBelt2).

west(feedBelt1).
west(pu1).
west(pu2).
west(depositBelt).
west(transferBelt).


east(feedBelt2).
east(pu3).
east(pu4).
east(depositBelt).
east(transferBelt).

//-----------------------------------------------------------------------------
//             Plans to update the Belief Base and optimise testing
//-----------------------------------------------------------------------------
+empty(Device) [source(percept)] : true
	<- +empty(Device).

+object(Type, Object) [source(percept)] : not object(Type, Object) [source(self)]
	<- +object(Type, Object).

+type(Block, Type) [source(percept)] : not type(Block, Type) [source(self)]
	<- +type(Block, Type).

+over(Object, Device) [source(percept)] : not over(Object, Device)[source(self)]
	<-  .print("Acknowledging ",over(Object, Device));
		+over(Object, Device).

//-----------------------------------------------------------------------------
//               Instructions on how to finish processing blocks
//-----------------------------------------------------------------------------

+!finish(Block) : type(Block, type1)
	<- !goalConj([processed(Block, pu1), 
	         //processed(Block, pu2), 
	         //processed(Block, pu3), 
	         finished(Block)]).

+!finish(Block) : type(Block, type2)
	<- !goalConj([processed(Block, pu3), 
	         //processed(Block, pu4), 
	         finished(Block)]).

+!finish(Block) : type(Block, type3)
	<- !goalConj([processed(Block, pu1), 
	         processed(Block, pu3), 
	         finished(Block)]).