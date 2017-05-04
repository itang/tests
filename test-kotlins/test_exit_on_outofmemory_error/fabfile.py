# -*- coding: utf-8 -*-

from fabric.api import *


def help():
    """help"""
    print('help')


def test():
    """test"""
    cmd = 'JAVA_OPTS="-Xmx14M -XX:OnOutOfMemoryError=\'kill -9 %p\' -XX:+PrintFlagsFinal" bin/test_exit_on_outofmemory_error 10'
    local('./gradlew installDist')
    with lcd('build/install/test_exit_on_outofmemory_error'):
        local(cmd)
