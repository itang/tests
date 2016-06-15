from fabric.api import *


def run():
    local('docker run -ti --rm -v $PWD/scala-native:/scala-native -v /home/itang/.ivy2:/root/.ivy2 test_scalanative-docker-jdk8')
