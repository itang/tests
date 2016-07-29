from fabric.api import *
from multiprocessing import Process
import time

def run():
    print('run server...')
    local("cargo run --release")


def bench():
    print('start bench...')
    local("wrk http://localhost:8080 -c100 -d30 -t2")


def all():
    p = Process(target=run)
    p.start()

    time.sleep(2)
    bench()

    # p.close()
