# -*- coding: utf-8 -*-

from fabric.api import *


def help():
    """help"""
    print('help')


def run():
    """run"""
    local('etlas run')


def uberjar_mode():
    """uberjar mode"""
    local('etlas clean')
    local('etlas configure --enable-uberjar-mode')


def jar_mode():
    """jar mode"""
    local('etlas clean')
    local('etlas configure --disable-uberjar-mode')


def clean():
    """clean"""
    local('etlas clean')
    local('etlas install --dependencies-only')
