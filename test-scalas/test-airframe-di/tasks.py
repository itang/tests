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
def run(c):
    '''run'''
    c.run('scala-cli run -S 2.13 main.scala')


@task
def dev(c):
    '''dev'''
    c.run('scala-cli run -S 2.13 -w main.scala')


@task
def build_execute_jar(c):
    '''build native'''
    c.run('scala-cli package --force main.scala')


@task
def build_fat_jar(c):
    '''build fat jar'''
    c.run('scala-cli package --assembly -f main_2_13.scala -o dist/main_2_13.jar')


@task(build_fat_jar)
def build_native_image(c):
    '''build native image'''
    c.run('native-image -H:-CheckToolchain -H:+ReportExceptionStackTraces -jar main_2_13.jar')


@task
def build_scala_native(c):
    '''build native'''
    c.run('scala-cli package --native main_2_13.scala -S 2.13.7')


@task
def format(c):
    '''format'''
    c.run('scala-cli -S 3.1.0 scripts/scalafmt_version.scala')
