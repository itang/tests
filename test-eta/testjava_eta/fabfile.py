    # -*- coding: utf-8 -*-

from fabric.api import *


def help():
    """help"""
    print('help')


def run():
    """run"""
    local('epm run')


def uberjar_mode():
    """uberjar mode"""
    local('epm clean')
    local('epm configure --enable-uberjar-mode')

def jar_mode():
    """jar mode"""
    local('epm clean')
    local('epm configure --disable-uberjar-mode')


def clean():
    """clean"""
    local('epm clean')
    local('epm install --dependencies-only')
