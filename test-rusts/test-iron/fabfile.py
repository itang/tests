import time
from fabric.api import *
from multiprocessing import Process


def run_server():
    print('run server...')
    local("cargo run --release")


def wrk():
    print('start bench...')
    local("wrk http://localhost:8080 -c100 -d10 -t4")


def bench():
    server = Process(target=run_server)
    server.start()

    time.sleep(2)

    wrk()

    server.terminate()
    server.join()  # TBC: exit correct??
