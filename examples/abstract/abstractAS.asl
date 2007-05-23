//Event 1 is to achieve goal1
+event1 : have(resource1) [source(self)]
	<- !goal1.
	
//Event 2 is to achieve goal1 and goal2
//But this plan serves only if we got resource2
+event2 : have(resource1) [source(self)] & have(resource2) [source(self)]
	<- 	!goal1;
		!goal2.

+!goal1 : goal1 [source(self)]
	<- true.

+!goal1 : have(resource1) [source(self)] & not goal1 [source(self)]
	<-  !action1.


+!goal2 : goal2 [source(self)]
	<- true.

+!goal2 : have(resource2) [source(self)]
	<- !action2.

//Event 2 is to achieve goal2
//But this plan serves only if we got resource3 and not resource2
+!goal2 : have(resource3) [source(self)] & not have(resource2) [source(self)]
	<- !action2Alt.
	
{include("abstract-actions.asl")}