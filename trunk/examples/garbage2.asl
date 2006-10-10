//Instructions to Graphplan
object(object, robot).
object(object, waste).
object(object, bin).
object(location, a1).
object(location, a2).
object(location, a3).
object(location, a4).
object(location, a5).
//-------------------------

//Agent beliefs
location(robot, a1).
location(waste, a1).
location(bin, a5).
adjacent(a1, a2).
adjacent(a2, a3).
adjacent(a3, a4).
adjacent(a4, a5).

+des(Goals) : true
	<- org.soton.peleus.act.plan(Goals);
	   .print("Goals ",Goals," were satisfied").

+location(waste, X) : location(robot, X) &
                      location(bin, Y)
                      <- pick(waste);
                         +des([location(robot,Y)]);
                         drop(waste).

@action1(location, location)
+!move(X,Y) : location(robot,X) & not X = Y & adjacent(X,Y)
					   <- -location(robot,X);
					      +location(robot,Y).