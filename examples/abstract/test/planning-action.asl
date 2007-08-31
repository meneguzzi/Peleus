//Planning Plan
+!goalConj(Goals) : true
	<-  org.soton.peleus.act.plan(Goals,[makeGeneric(false)]);
		.print("Goals ",Goals," were satisfied").