#!/bin/bash
for f in *.xml
do
   sed -i  's/-1.2/-1.3/g' "$f"
done
