#/bin/bash

PRJ=/var/www/trunk
F=${PRJ}/doc/class_heirarchy.dot
GRAPH=${PRJ}/doc/class_heirarchy.gif
SRCDIR=${PRJ}/src/www/protected

echo "digraph NRC_Class_Graph {" > $F
echo "rankdir=LR" >> $F

grep -srhC 1 extends $SRCDIR | \
	tr '\t' ' ' | tr -s ' ' | \
	grep "[ce][lx][at][se][sn]" | \
	sed -e "s|^.*class |class |" -e "s|{.*||" | \
	tr -d '[{/\/\*]' | \
	tr '\n' '@' | \
	sed "s|@ *extend| extend|g" | \
	tr '@' '\n' | \
	tr -s ' ' | \
	grep -v CRS | \
	sed "s|class \([^ ]*\) extends \(.*\)|\2 -> \1;|" | sort -u \
		>> $F

echo "}" >> $F

dot -Tgif $F -o $GRAPH

ls -l $GRAPH

