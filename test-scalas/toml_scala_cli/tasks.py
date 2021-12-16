from invoke import task


@task
def version(c, cmd=None):
    """println version"""
    cmd = 'invoke' if cmd is None else cmd
    v = ('-version' if cmd == 'java' else
         'version' if cmd == 'go' else
         '--version'
         )

    c.run('{} {}'.format(cmd, v))


@task(default=True)
def usage(c):
    """Usage"""
    c.run('invoke -l')


def _is_windows():
    import sys
    return sys.platform.startswith('win')


@task
def install(c):
    '''install'''
    if _is_windows():
        c.run('cmd /C "echo TODO: install for windows ...')
    else:
        c.run('bash -c "echo TODO: install for linux ...')


@task
def dist(c):
    '''dist'''
    c.run('scala-cli package --native -S 2.13 main.scala')
