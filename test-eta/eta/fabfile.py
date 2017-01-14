# -*- coding: utf-8 -*-

from fabric.api import *


def run():
    """run"""
    local('docker-compose up -d')
    local('docker attach eta_eta_1')
