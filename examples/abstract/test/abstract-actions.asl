totalTime(0).
totalPlans(0).
actionDelay(5).

//Plans to delay execution

/*+!delay : true
	<- true.*/

+!delay : true
	<- ?actionDelay(D);
		org.soton.peleus.act.time_in_millis(T);
		.println("Delaying ",D," miliseconds.");
		!delay(T, T+D).

+!delay(TimeNow, TimeTo) : TimeNow >= TimeTo
	<- .println("Delay is done.");
		true.

+!delay(TimeNow, TimeTo) : true
	<- .println("Delaying more.");
		org.soton.peleus.act.time_in_millis(T);
		!delay(T,TimeTo).



/** To stop the simulation and kill the agent*/
+endSimulation : true
	<- !endSimulation.

+!endSimulation : true
	<- .print("Simulation is over, stopping MAS.");
		?totalPlans(P);
		.print("Invoked ",P," plocks");
		?totalTime(T);
		.print("Total time planning was ",T," milliseconds");
		org.soton.peleus.act.recordStats("stats",P,T);
		.stopMAS.

/*---------------------------------------------------------------------------*/
/*Belief Update Functions													 */
/*---------------------------------------------------------------------------*/
+have(Resource)[source(percept)] : true
	<-  .print("Adding ",Resource);
		+have(Resource) [source(self)].

+~have(Resource)[source(percept)] : true
	<- //.print("Removing ",Resource);
		-have(Resource) [source(self)];
		+~have(Resource) [source(self)].

@clear[atomic]
+clear(_) [source(percept)] : true
	<- .println("Clearing goals");
		-goal1 [source(self)];
		-goal2 [source(self)];
		-goal3 [source(self)];
		-goal4 [source(self)];
		?totalPlans(P);
		-+totalPlans(P+1).
/*---------------------------------------------------------------------------*/
/*Abstract Actions															 */
/*---------------------------------------------------------------------------*/
@action1(resource)
+!action1 : have(resource1) [source(self)]
	<- .print("Doing action 1");
	   !delay;
	   +goal1.

@action2(resource)
+!action2 : have(resource2) [source(self)]
	<- .print("Doing Action 2");
	   !delay;
	   +goal2.

@action3(resource)
+!action3 : have(resource3) [source(self)]
	<- .print("Doing Action 3");
	   !delay;
	   +goal3.

@action4(resource)
+!action4 : have(resource4) [source(self)]
	<- .print("Doing Action 4");
	   !delay;
	   +goal4.

/*@action5(resource)
+!getResource(Resource) : true //not have(Resource) [source(self)]
	<- .print("Getting ",Resource);
	   !delay;
	   +have(Resource) [source(self)].*/
	   
/*---------------------------------------------------------------------------*/