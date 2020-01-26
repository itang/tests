from invoke import task
from fabric import Connection


@task
def version(c, docs=False):
    'print python version'
    print(type(c))
    c.local('python --version')
    c.run('python --version')
    if docs:
        print('make docs...')


@task
def remote(c, docs=False):
    'remote version'
    print('remote version')
    c = Connection('user@hostname:port')
    version(c)
