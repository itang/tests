from invoke import task


@task
def version(c, cmd=None):
    "println version"
    cmd = 'invoke' if cmd == None else cmd
    v = ('-version' if cmd == 'java' else
         'version' if cmd == 'go' else
         '--version'
         )

    c.run('{} {}'.format(cmd, v))


@task(default=True)
def usage(c):
    """Usage"""
    c.run('invoke -l')


@task
def build(c):
    """build"""
    c.run('sbt nativeLink')


@task
def run(c):
    """run"""
    build(c)
    c.run('LD_LIBRARY_PATH=./myrust/target/debug/:$LD_LIBRARY_PATH sbt run')
