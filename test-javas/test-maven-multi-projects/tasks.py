from invoke import task


@task(default=True)
def usage(c):
    """Usage"""
    c.run('invoke -l')


@task
def run(c):
    """run"""
    c.run('mvn exec:java -Dexec.mainClass=hello.HelloWorld')