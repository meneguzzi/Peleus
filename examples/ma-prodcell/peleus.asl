//-----------------------------------------------------------------------------
//               Plan that invokes the Planner
//-----------------------------------------------------------------------------
+!goalConj(Goals) : true
	<- org.soton.peleus.act.plan(Goals,10,false);
	   .print("Goals ",Goals," were satisfied").

-!goalConj(Goals) : true
	<- .print("Failed planning for Goals ",Goals,", will try another strategy").
	   