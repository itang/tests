# -*- coding: utf-8 -*-

from fabric.api import *


def help():
    """help"""
    print('help')


def run_musl():
    """run musl"""
    local('cargo run --target=x86_64-unknown-linux-musl')
