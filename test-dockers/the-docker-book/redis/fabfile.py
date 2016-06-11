from fabric.api import *


def build():
    local('docker build -t itang/redis .')


def run():
    local('docker run -p 6379:6379 --name redis itang/redis')


def info():
    local('docker port redis 6379')
    local('docker top redis')
