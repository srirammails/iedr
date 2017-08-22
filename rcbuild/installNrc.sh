pushd nrc
scp rpm.tar.gz root@$1:.
popd
ssh root@$1 /root/build_rpm.sh
ssh root@$1 /root/install_rpm.sh

