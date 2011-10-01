start.

@plan1[atomic]
+start : true
	<- .send(simple, tell, packet(packet1));
	   .send(simple, tell, at(pos1));
       .send(simple, tell, over(packet1,bay)).

@plan2[atomic]
+move(A,B) [source(simple)]: true
	<- .print("Executing move(",A,",",B,")");
	   .send(simple, untell, at(A));
	   .send(simple, tell, at(B)).

@plan3[atomic]
+pickup(A) : true
	<- .send(simple, tell, held(A)).

@plan4[atomic]
+drop(A) : true
	<- .send(simple, untell, held(A)).