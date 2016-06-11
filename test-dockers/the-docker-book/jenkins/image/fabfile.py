from fabric.api import *


def run():
    local('docker run -p 8080:8080 --name jenkins --privileged -d itang/dockerjenkins')
