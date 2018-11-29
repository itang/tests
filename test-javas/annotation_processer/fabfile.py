# -*- coding: utf-8 -*-

from fabric.api import *


def run():
    """run"""
    local('./gradlew run')


def dev():
    """dev"""
    local('watchexec -r "./gradlew run -no-daemon"')


def clean():
    """clean"""
    local('./gradlew clean')


def t():
    "t"
    local('gradle jar && javac -cp build/libs/annotation_processer.jar Hello.java')
