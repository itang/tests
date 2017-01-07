# -*- coding: utf-8 -*-

from fabric.api import *


def watch():
    """watch"""
    # link: https://github.com/mattgreen/watchexec

    # local('watchexec -r -k gradle run')
    local("watchexec 'gradle run --no-daemon'")


def watch_test():
    """watch test"""
    local("watchexec 'gradle test --no-daemon'")
