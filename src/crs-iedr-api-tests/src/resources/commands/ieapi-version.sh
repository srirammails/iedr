#!/bin/bash
for fl in *.xml; do
  mv $fl $fl.old
  sed 's/-1.2/-1.3/g' $fl.old > $fl
  rm -f $fl.old
done