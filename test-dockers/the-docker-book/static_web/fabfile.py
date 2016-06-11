from fabric.api import *


def build():
    local('docker build -t itang/static_web .')


def run():
    local('docker run -ti -p 80 --name static_web itang/static_web nginx -g "daemon off;"')
