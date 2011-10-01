//A battery-operated robot

//empty(bay1).
//empty(bay2).
//empty(bay3).
//empty(charger).

+at(A) : true
	<- .print("Added ",at(A)).
	
-at(A) : true
	<- .print("Removed ",at(A)).

+batt(critical) : true
	<- !charge.

+over(Packet,Bay) : true
	<- !sort(Packet).

+!sort(Packet) : packet(Packet) & over(Packet,Bay) & at(A)
	<- .print("Sorting packet");
	   !move(A,Bay);
	   .send(simpleEnvironment, tell, pickup(Packet));//!pickup(Packet);
	   !move(Bay,pigeonHoles);
	   .send(simpleEnvironment, tell, drop(Packet)).//!drop(Packet).

/*+!pickup(Item) : over(Item,A) & at(A) & not held(_)
	<- -over(Item,A);
	   +held(Item);
	   pickup(Item).

+!drop(Item) : at(A) & held(Item)
	<- +over(Item, A);
	   -held(Item);
	   drop(Item).*/

/*+!move(A,B) : not at(B) & not batt(empty)
	<- -at(A);
	   +at(B);
	   move(A,B).*/

+!move(A,B) : A = B
	<- true.

+!move(A,B) : at(B)
	<- true.

+!move(A,B) : not at(B) & not batt(empty)
	<- .send(simpleEnvironment, tell, move(A,B));
	   .wait(1000);
	   ?not(at(A));
	   ?at(B).

+!charge : at(A)
	<- move(A,charger);
		charge.