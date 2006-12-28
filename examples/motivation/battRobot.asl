//A battery-operated robot

/*batt(critical) :- batt(2) | batt(1).
batt(empty) :- batt(0).
batt(full) :- batt(10).*/

+batt(A) : not batt(critical)
	<- .print("Battery is ",A).

+batt(critical) : true
	<- !charge.

/*+over(Packet,Bay) : true
	<- !sort(Packet).*/
/*+over(Packet,Bay) : not busy
	<- !sort(Packet).*/
/*+over(Packet,bay1) : true
	<- +conjGoal(over(Packet,pigeonHoles)).*/

//@pb1[atomic]
+!sort(Packet) : packet(Packet) & over(Packet,Bay) & at(A) & not held(_)
	<- !move(A,Bay);
	   !pickup(Packet);
	   !move(Bay,pigeonHoles);
	   !drop(Packet).

+!pickup(Item) : over(Item,A) & at(A) & not held(_)
	<- -over(Item,A);
	   +held(Item);
	   pickup(Item).

+!drop(Item) : at(A) & held(Item)
	<- +over(Item, A);
	   -held(Item);
	   drop(Item).

+!move(A,A) : true
	<- true.

+!move(A,B) : not at(B) & not batt(0)
	<- -at(A);
	   +at(B);
	   move(A,B).

+!charge : at(A)
	<- move(A,charger);
		charge.