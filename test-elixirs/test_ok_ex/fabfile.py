# -*- coding: utf-8 -*-

from fabric.api import *


def help():
    """help"""
    print('help')



def dist():
    """dist"""
    local('mix escript.build')


def run():
    """run"""
    dist()
    local('./test_ok_ex')


def repl():
    """repl"""
    local('iex -S mix')

