{include("planning-action.asl")}

+event1 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal1]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).

+event2 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal2]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).

+event3 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal1,goal2]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).

+event4 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal3]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).

+event5 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal1,goal3]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).
	
+event6 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal1,goal2,goal3]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).

+event7 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal4]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).

+event8 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal1,goal4]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).

+event9 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal2,goal4]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).
	
+event10 : true
	<- org.soton.peleus.act.time_in_millis(T);
		!goalConj([goal1,goal2,goal4]);
		org.soton.peleus.act.time_in_millis(T2);
		Time = T2 - T;
		?totalTime(TotalTime);
		-+totalTime(TotalTime + Time).

{include("abstract-actions.asl")}