//Planning Plan
+!goalConj(Goals) : true
	<- org.soton.peleus.act.plan(Goals,true);
	   .print("Goals ",Goals," were satisfied").

//In case a generated plan fails
// -!executePlan(Plan)

+event1 : true
	<- !goalConj([goal1]).

+event2 : true
	<- !goalConj([goal1,goal2]).


{include("abstract-actions.asl")}