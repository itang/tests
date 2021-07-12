from invoke import task


@task(default=True)
def usage(c):
    """Usage"""
    c.run('invoke -l')


@task
def do_compile(c):
    """compile"""
    c.run('mvn clean compile')


@task
def do_copy_package(c):
    """copy"""
    c.run('mvn clean dependency:copy-dependencies package')


@task(do_compile, do_copy_package)
def run(c):
    """run"""
    with c.cd("start"):
        c.run('java -cp target/dependency/*:target/classes hello.HelloWorld')
