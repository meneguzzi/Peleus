/*---------------------------------------------------------------------------*/
/*Belief Update Functions													 */
/*---------------------------------------------------------------------------*/
+have(Resource)[source(percept)] : true
	<-  .print("Adding ",Resource);
		+have(Resource) [source(self)].

+~have(Resource)[source(percept)] : true
	<- //.print("Removing ",Resource);
		-have(Resource) [source(self)].
/*---------------------------------------------------------------------------*/
/*Abstract Actions															 */
/*---------------------------------------------------------------------------*/
@action1(resource)
+!action1 : have(resource1) [source(self)]
	<- .print("Doing action 1");
	   .wait(50);
	   +goal1.

@action2(resource)
+!action2 : have(resource2) [source(self)]
	<- .print("Doing Action 2");
	   .wait(50);
	   +goal2.

@action2Alt(resource)
+!action2Alt : have(resource3) [source(self)]
	<- .print("Doing Action 2 Alt");
	   .wait(50);
	   +goal2.
	   
@action3(resource)
+!action3 : have(resource3) [source(self)]
	<- .print("Doing Action 3");
	   .wait(50);
	   +goal3.

@action4(resource)
+!action4 : have(resource4) [source(self)]
	<- .print("Doing Action 4");
	   .wait(50);
	   +goal4.

@getResource(resource)
+!getResource(Resource) : not have(Resource) [source(self)]
	<- .print("Getting ",Resource);
	   .wait(50);
	   +have(Resource).
/*---------------------------------------------------------------------------*/