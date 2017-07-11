# -*- coding: utf-8 -*-

from fabric.api import local


def usage():
    """Usage"""
    print('usage')


def build():
    """build"""
    local("npm install;webpack")


def run():
    """run"""
    build()
    local('xdg-open index.html')
