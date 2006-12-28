start.

@atomic
+start : true
	<- .send(simple, tell, packet(packet1));
	   .send(simple, tell, at(pos1));
       .send(simple, tell, over(packet1,bay)).

//@atomic
+move(A,B) [source(simple)]: true
	<- .send(simple, untell, at(A));
	   .send(simple, tell, at(B)).

//@atomic
+pickup(A) : true
	<- .send(simple, tell, held(A)).

//@atomic
+drop(A) : true
	<- .send(simple, untell, held(A)).