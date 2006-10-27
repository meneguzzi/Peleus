//A battery-operated robot

/*empty(bay1).
empty(bay2).
empty(bay3).
empty(charger).*/

/*+empty(bay1) : at(X)
	<- !move(X, bay1).*/
	
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

+!sort(Packet) : packet(Packet) & over(Packet,Bay) & at(A)
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

+!move(A,B) : not at(B) & not batt(empty)
	<- -at(A);
	   +at(B);
	   move(A,B).

+!charge : at(A)
	<- move(A,charger);
		charge.