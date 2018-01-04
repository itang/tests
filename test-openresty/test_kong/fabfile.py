# -*- coding: utf-8 -*-

from fabric.api import *


def usage():
    """Usage"""
    print('usage')


def migrate():
    """migrate"""
    local('docker run --rm --network="testkong_default" --link kong-database:kong-database -e "KONG_DATABASE=cassandra" -e "KONG_PG_HOST=kong-database" -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" kong:latest kong migrations up')
