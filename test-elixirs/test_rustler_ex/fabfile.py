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
    local("""mix run -e 'IO.puts("#{Test.Fib.add(1,2)}")'""")


def run_bin():
    """run"""
    dist()
    local('./test_rustler_ex')
