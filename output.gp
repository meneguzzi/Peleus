
Proposition Layer
Proposition: on(b1,b2)
 Mutex with: 
Proposition: clear(b1)
 Mutex with: 
Proposition: on(b3,table)
 Mutex with: 
Proposition: on(b2,table)
 Mutex with: 
Proposition: clear(b3)
 Mutex with: 
Proposition: clear(table)
 Mutex with: 
 Action Layer
noopclear(b3)
 Mutex With: move ( b1, b2, b3 ), 
noopon(b2,table)
 Mutex With: 
moveToTable ( b1, b2 )
 Mutex With: move ( b1, b2, b3 ), noopon(b1,b2), move ( b3, table, b1 ), 
move ( b1, b2, b3 )
 Mutex With: noopclear(b3), moveToTable ( b1, b2 ), noopon(b1,b2), move ( b3, table, b1 ), 
noopon(b1,b2)
 Mutex With: moveToTable ( b1, b2 ), move ( b1, b2, b3 ), 
move ( b3, table, b1 )
 Mutex With: moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,table), noopclear(b1), 
noopon(b3,table)
 Mutex With: move ( b3, table, b1 ), 
noopclear(b1)
 Mutex With: move ( b3, table, b1 ), 
noopclear(table)
 Mutex With: 
Proposition Layer
Proposition: clear(b1)
 Mutex with: on(b3,b1), 
Proposition: on(b3,table)
 Mutex with: on(b3,b1), 
Proposition: on(b2,table)
 Mutex with: 
Proposition: clear(table)
 Mutex with: 
Proposition: on(b1,b2)
 Mutex with: on(b1,table), clear(b2), on(b1,b3), 
Proposition: clear(b3)
 Mutex with: on(b1,b3), 
Proposition: on(b3,b1)
 Mutex with: clear(b1), on(b3,table), on(b1,table), clear(b2), on(b1,b3), 
Proposition: on(b1,table)
 Mutex with: on(b1,b2), on(b3,b1), on(b1,b3), 
Proposition: clear(b2)
 Mutex with: on(b1,b2), on(b3,b1), 
Proposition: on(b1,b3)
 Mutex with: on(b1,b2), clear(b3), on(b3,b1), on(b1,table), 
 Action Layer
move ( b2, table, b1 )
 Mutex With: moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopclear(b1), noopon(b3,b1), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), noopon(b2,table), 
moveToTable ( b3, b1 )
 Mutex With: move ( b2, table, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopclear(b1), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
move ( b3, table, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
noopclear(b3)
 Mutex With: move ( b2, table, b3 ), move ( b1, b2, b3 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b1, table, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b2, table, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), noopclear(b3), move ( b1, table, b2 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b2,table), 
move ( b3, b1, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopclear(b1), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
moveToTable ( b1, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b1, b2, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), noopclear(b3), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), 
noopclear(b1)
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, b1, b2 ), noopon(b3,b1), move ( b3, table, b1 ), 
noopon(b3,b1)
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopclear(b1), noopon(b1,table), move ( b3, table, b1 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
noopon(b1,table)
 Mutex With: moveToTable ( b3, b1 ), move ( b1, table, b2 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b3, table, b1 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopclear(b1), noopon(b3,b1), move ( b1, table, b3 ), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
noopon(b1,b2)
 Mutex With: move ( b2, table, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b1,table), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b1, table, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), noopclear(b3), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b1, b3, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), noopclear(b3), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), noopclear(b2), moveToTable ( b1, b3 ), noopon(b1,b3), 
noopclear(b2)
 Mutex With: moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), noopon(b1,b2), move ( b1, b3, b2 ), 
noopclear(table)
 Mutex With: 
moveToTable ( b1, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), noopclear(b3), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b1,b3), 
noopon(b1,b3)
 Mutex With: moveToTable ( b3, b1 ), move ( b3, table, b2 ), noopclear(b3), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b3,b1), noopon(b1,table), move ( b3, table, b1 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), 
noopon(b2,table)
 Mutex With: move ( b2, table, b1 ), move ( b2, table, b3 ), 
noopon(b3,table)
 Mutex With: moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b3, b1, b2 ), noopon(b3,b1), move ( b3, table, b1 ), 
Proposition Layer
Proposition: on(b3,table)
 Mutex with: on(b3,b2), on(b3,b1), 
Proposition: clear(b1)
 Mutex with: on(b3,b1), on(b2,b1), 
Proposition: on(b3,b2)
 Mutex with: on(b3,table), on(b1,b2), on(b3,b1), on(b2,b1), clear(b2), on(b2,b3), on(b1,b3), 
Proposition: on(b2,table)
 Mutex with: on(b2,b1), on(b2,b3), 
Proposition: clear(table)
 Mutex with: 
Proposition: on(b1,b2)
 Mutex with: on(b3,b2), on(b2,b1), on(b1,table), clear(b2), on(b2,b3), on(b1,b3), 
Proposition: clear(b3)
 Mutex with: on(b2,b3), on(b1,b3), 
Proposition: on(b3,b1)
 Mutex with: on(b3,table), clear(b1), on(b3,b2), on(b2,b1), on(b2,b3), on(b1,b3), 
Proposition: on(b2,b1)
 Mutex with: clear(b1), on(b3,b2), on(b2,table), on(b1,b2), on(b3,b1), on(b2,b3), 
Proposition: on(b1,table)
 Mutex with: on(b1,b2), on(b1,b3), 
Proposition: clear(b2)
 Mutex with: on(b3,b2), on(b1,b2), 
Proposition: on(b2,b3)
 Mutex with: on(b3,b2), on(b2,table), on(b1,b2), clear(b3), on(b3,b1), on(b2,b1), on(b1,b3), 
Proposition: on(b1,b3)
 Mutex with: on(b3,b2), on(b1,b2), clear(b3), on(b3,b1), on(b1,table), on(b2,b3), 
 Action Layer
move ( b2, table, b1 )
 Mutex With: moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b2,table), 
moveToTable ( b3, b1 )
 Mutex With: move ( b2, table, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
noopclear(b3)
 Mutex With: move ( b2, table, b3 ), moveToTable ( b2, b3 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b2,b3), move ( b2, b1, b3 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b3, table, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b3,b2), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
moveToTable ( b2, b1 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b2,table), 
move ( b1, table, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b1,table), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b2, table, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b2,table), 
move ( b3, b1, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
moveToTable ( b2, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b2,table), 
moveToTable ( b1, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b1,table), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b1, b2, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b1,table), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b2, b3, b1 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b2,table), 
noopclear(b1)
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), moveToTable ( b2, b1 ), move ( b3, b1, b2 ), move ( b2, b3, b1 ), noopon(b3,b1), move ( b2, b1, b3 ), move ( b3, table, b1 ), move ( b3, b2, b1 ), noopon(b2,b1), 
noopon(b3,b1)
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b2,b3), move ( b2, b1, b3 ), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
noopon(b1,table)
 Mutex With: move ( b1, table, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b1,b2), move ( b1, table, b3 ), move ( b1, b3, b2 ), moveToTable ( b1, b3 ), noopon(b1,b3), 
noopon(b2,b3)
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b2,table), 
move ( b2, b1, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b2,table), 
noopon(b1,b2)
 Mutex With: move ( b2, table, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b1,table), noopon(b2,b3), move ( b2, b1, b3 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b3, table, b1 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
move ( b3, b2, b1 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
moveToTable ( b3, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
move ( b1, table, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b1,table), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, b3, b2 ), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), 
move ( b1, b3, b2 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b1,table), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), noopclear(b2), noopon(b3,b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), 
noopclear(b2)
 Mutex With: move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b3, b1, b2 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), noopon(b1,b2), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, b3, b2 ), noopon(b3,b2), 
noopon(b3,b2)
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, b3, b2 ), noopclear(b2), noopon(b2,b1), moveToTable ( b1, b3 ), noopon(b1,b3), noopon(b3,table), 
noopon(b2,b1)
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopclear(b1), noopon(b3,b1), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), moveToTable ( b1, b3 ), noopon(b2,table), 
noopclear(table)
 Mutex With: 
moveToTable ( b1, b3 )
 Mutex With: move ( b2, table, b1 ), moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), moveToTable ( b2, b1 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b1,table), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), noopon(b2,b1), noopon(b1,b3), 
noopon(b1,b3)
 Mutex With: moveToTable ( b3, b1 ), noopclear(b3), move ( b3, table, b2 ), move ( b1, table, b2 ), move ( b2, table, b3 ), move ( b3, b1, b2 ), moveToTable ( b2, b3 ), moveToTable ( b1, b2 ), move ( b1, b2, b3 ), move ( b2, b3, b1 ), noopon(b3,b1), noopon(b1,table), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b1,b2), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), move ( b1, table, b3 ), move ( b1, b3, b2 ), noopon(b3,b2), moveToTable ( b1, b3 ), 
noopon(b2,table)
 Mutex With: move ( b2, table, b1 ), moveToTable ( b2, b1 ), move ( b2, table, b3 ), moveToTable ( b2, b3 ), move ( b2, b3, b1 ), noopon(b2,b3), move ( b2, b1, b3 ), noopon(b2,b1), 
noopon(b3,table)
 Mutex With: moveToTable ( b3, b1 ), move ( b3, table, b2 ), move ( b3, b1, b2 ), noopon(b3,b1), move ( b3, table, b1 ), move ( b3, b2, b1 ), moveToTable ( b3, b2 ), noopon(b3,b2), 
Proposition Layer
Proposition: on(b3,table)
 Mutex with: on(b3,b2), on(b3,b1), 
Proposition: clear(b1)
 Mutex with: on(b3,b1), on(b2,b1), 
Proposition: on(b3,b2)
 Mutex with: on(b3,table), on(b1,b2), on(b3,b1), clear(b2), on(b2,b3), 
Proposition: clear(table)
 Mutex with: 
Proposition: on(b2,table)
 Mutex with: on(b2,b1), on(b2,b3), 
Proposition: on(b1,b2)
 Mutex with: on(b3,b2), on(b2,b1), on(b1,table), clear(b2), on(b1,b3), 
Proposition: clear(b3)
 Mutex with: on(b2,b3), on(b1,b3), 
Proposition: on(b3,b1)
 Mutex with: on(b3,table), clear(b1), on(b3,b2), on(b2,b1), on(b1,b3), 
Proposition: on(b2,b1)
 Mutex with: clear(b1), on(b2,table), on(b1,b2), on(b3,b1), on(b2,b3), 
Proposition: on(b1,table)
 Mutex with: on(b1,b2), on(b1,b3), 
Proposition: clear(b2)
 Mutex with: on(b3,b2), on(b1,b2), 
Proposition: on(b2,b3)
 Mutex with: on(b3,b2), on(b2,table), clear(b3), on(b2,b1), on(b1,b3), 
Proposition: on(b1,b3)
 Mutex with: on(b1,b2), clear(b3), on(b3,b1), on(b1,table), on(b2,b3), 