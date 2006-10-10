location(robot, a1).
location(waste, a1).
location(bin, a5).
adjacent(a1, a2).
adjacent(a2, a3).
adjacent(a3, a4).
adjacent(a4, a5).

+location(waste, X) : location(robot, X) &
                      location(bin, Y)
                      <- pick(waste);
                         !location(robot, Y);
                         drop(waste).

+!location(robot, X) : location(robot, X) <- true.

+!location(robot, X) : location(robot, Y) &
                       not X = Y &
                       adjacent(Y, Z)&
                       not location(car, Z)
                       <- .print("Moving robot from ",Y," to ", Z);
                       	  !move(Y, Z);
                          !location(robot, X).

+!move(X,Y) : location(robot,X) & not X = Y & adjacent(X,Y)
					   <- -location(robot,X);
					      +location(robot,Y).