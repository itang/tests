# -*- coding: utf-8 -*-

from fabric.api import *


def usage():
    """Usage"""
    print('usage')


def dev():
    """dev"""
    local('cargo watch -x run')
