
# -*- coding: utf-8 -*-

from fabric.api import *


def run():
    """Run"""
    local("sbt run")


def dev():
    """Dev"""
    local("sbt \"~reStart\"")
