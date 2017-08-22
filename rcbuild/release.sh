NRC_VERSION=26.10

SPRINT_NUMBER=36.10

echo "Releasing Sprint $SPRINT_NUMBER (press ENTER to continue)"
read
echo "Getting rpm with NRC release candidate..."
#scp root@172.19.243.33:/root/rpm/RPMS/noarch/nrc-1-$NRC_VERSION.noarch.rpm .
echo "Press ENTER to continue"
read
cp nrc-1-$NRC_VERSION.noarch.rpm ~/Projekty/IEDR-NRC/repo/builds/.
svn add ~/Projekty/IEDR-NRC/repo/builds/nrc-1-$NRC_VERSION.noarch.rpm
echo "Adding rpm with NRC rc to the trunk..."
svn commit -m "release candidate for Sprint $SPRINT_NUMBER" ~/Projekty/IEDR-NRC/repo/builds
svn copy https://drotest4.nask.net.pl/svn/iedr-crs/branches/bpr-sprint36fixes/  https://drotest4.nask.net.pl/svn/iedr-crs/tags/bpr-sprint$SPRINT_NUMBER -m "Released version BPR Sprint $SPRINT_NUMBER"
echo "Tag created: https://drotest4.nask.net.pl/svn/iedr-crs/tags/bpr-sprint$SPRINT_NUMBER"
svn copy http://83.71.193.106:81/svn/CRS-PII-G/branches/sprint36fixes http://83.71.193.106:81/svn/CRS-PII-G/tags/sprint$SPRINT_NUMBER -m "Released version BPR Sprint $SPRINT_NUMBER"
echo "Tag created: http://83.71.193.106:81/svn/CRS-PII-G/tags/sprint$SPRINT_NUMBER"

