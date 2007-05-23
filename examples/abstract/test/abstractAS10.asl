//Event 1 is to achieve goal1
+!goal1 : goal1 [source(self)]
	<- true.

+!goal1 : have(resource1) [source(self)] & not goal1 [source(self)]
	<-  !action1.

+!goal1 : not have(resource1) [source(self)] & not goal1 [source(self)]
	<-  !getResource(resource1);
		!action1.

+!goal2 : goal2 [source(self)]
	<- true.

+!goal2 : have(resource2) [source(self)] & not goal2 [source(self)]
	<-  !action2.

+!goal2 : not have(resource2) [source(self)] & not goal2 [source(self)]
	<-  !getResource(resource2);
		!action2.

+!goal3 : goal3 [source(self)]
	<- true.

+!goal3 : have(resource3) [source(self)] & not goal3 [source(self)]
	<-  !action3.

+!goal3 : not have(resource3) [source(self)] & not goal3 [source(self)]
	<-  !getResource(resource3);
		!action3.

+!goal4 : goal4 [source(self)]
	<- true.

+!goal4 : have(resource4) [source(self)] & not goal4 [source(self)]
	<-  !action4.

+!goal4 : not have(resource4) [source(self)] & not goal4 [source(self)]
	<-  !getResource(resource4);
		!action4.
	

+event1 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal1;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal1] were satisfied in ",Time, " miliseconds").

+event2 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal2;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal2]were satisfied in ",Time, " miliseconds").

+event3 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal1;
	   !goal2;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal1,goal2] were satisfied in ",Time, " miliseconds").

+event4 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal3;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal3] were satisfied in ",Time, " miliseconds").

+event5 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal1;
		!goal3;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal1,goal3] were satisfied in ",Time, " miliseconds").
	
+event6 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal1;
		!goal2;
		!goal3;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal1,goal2,goal3] were satisfied in ",Time, " miliseconds").

+event7 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal4;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal4] were satisfied in ",Time, " miliseconds").

+event8 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal1;
		!goal4;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal1,goal4] were satisfied in ",Time, " miliseconds").

+event9 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal2;
		!goal4;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal2,goal4] were satisfied in ",Time, " miliseconds").
	
+event10 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goal1;
		!goal2;
		!goal4;
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time);
	   .print("Goals [goal1,goal2,goal4] were satisfied in ",Time, " miliseconds").
	
{include("abstract-actions.asl")}