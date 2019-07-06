# -*- coding: utf-8 -*-

from fabric.api import *


def usage():
    """Usage"""
    print('usage')


def start():
    """nginx start"""
    local('[ -d "./logs" ] || mkdir logs')
    #local('nginx -p `pwd`/ -c conf/nginx.conf -g "daemon off;"')
    local('nginx -p `pwd`/ -c conf/nginx.conf -g "daemon off;"')


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
    import time
    time.sleep(0.5)
    start()


def tip():
    """tip"""
    print 'sudo bash -c "`which nginx`  -p `pwd`/ -c conf/nginx.conf"'

