# -*- coding: utf-8 -*-

from fabric.api import *
import time


def usage():
    """Usage"""
    print('usage')


def start():
    """nginx start"""
    local('[ -d "./logs" ] || mkdir logs')
    local('nginx -p `pwd`/ -c conf/nginx.conf')


def run():
    """nginx run"""
    start()


def reload():
    """nginx reload"""
    local('nginx -p `pwd`/ -s reload')


def stop():
    """nginx stop"""
    with warn_only():
        local('nginx -p `pwd`/ -s stop')


def restart():
    """restart"""
    stop()
    time.sleep(0.1)
    run()
