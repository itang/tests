# -*- coding: utf-8 -*-

from fabric.api import *


def dev():
    """dev"""
    local("cargo watch -x run")


def run():
    """run"""
    local('cargo run')


def build():
    """build in release"""
    local('cargo build --release')


def build_musl():
    """build musl"""
    local('cargo build --release --target x86_64-unknown-linux-musl')


def build_musl_upx():
    """tip"""
    build_musl()
    local('du -sh target/x86_64-unknown-linux-musl/release/rguid')
    local('cp target/x86_64-unknown-linux-musl/release/rguid target/x86_64-unknown-linux-musl/release/rguid.orig')
    local('upx target/x86_64-unknown-linux-musl/release/rguid')
    local('du -sh target/x86_64-unknown-linux-musl/release/rguid')
    print('try run: target/x86_64-unknown-linux-musl/release/rguid')


def setup_idea():
    """setup idea"""
    local('unset https_proxy http_proxy;cargo metadata --verbose --format-version 1 --all-features')

