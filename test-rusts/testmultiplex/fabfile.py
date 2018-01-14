# -*- coding: utf-8 -*-

from fabric.api import *


def dev():
    """dev"""
    local("cargo watch -x run")


def build_musl():
    """build musl"""
    local('cargo build --release --target x86_64-unknown-linux-musl')


def setup_idea():
    """setup idea"""
    local('unset https_proxy http_proxy;cargo metadata --verbose --format-version 1 --all-features')
