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
        c.run('cmd /C "echo TODO: install for windows" ...')
    else:
        c.run('bash -c "echo TODO: install for linux" ...')


def _replace(file):
    fin = open(file, "rt")
    data = fin.read()
    fin.close()

    fout = open(file, "wt")
    data = data.replace(".fable_modules", "fable_modules")
    fout.write(data)
    fout.close()


@task
def build(c):
    '''build'''
    c.run('dotnet tool restore')
    c.run('fable-py src')
    _replace('src/app.py')
    c.run('python src/app.py')
