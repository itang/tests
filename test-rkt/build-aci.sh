acbuild begin
acbuild set-name example.com/hello
acbuild copy testrkt /bin/testrkt
acbuild set-exec /bin/testrkt
acbuild write hello-latest-linux-amd64.aci
acbuild end
