# test invoke

## install

```
pip install invoke
pip install fabric
```

## Usage

```
➜  testinvoke invoke -l
Available tasks:

  version   print python version

➜  testinvoke invoke version
Python 3.8.1
➜  testinvoke invoke version -d
Python 3.8.1
make docs...
➜  testinvoke invoke version --docs
Python 3.8.1
make docs...
```


upgrade

http://www.fabfile.org/upgrading.html#shell-command-execution-local-run-sudo


```python
from invocations.console import confirm

from fabric import task

my_hosts = ["my-server"]

@task
def test(c):
    result = c.local("./manage.py test my_app", warn=True)
    if not result and not confirm("Tests failed. Continue anyway?"):
        raise Exit("Aborting at user request.")

@task
def commit(c):
    c.local("git add -p && git commit")

@task
def push(c):
    c.local("git push")

@task
def prepare_deploy(c):
    test(c)
    commit(c)
    push(c)

@task(hosts=my_hosts)
def deploy(c):
    code_dir = "/srv/django/myproject"
    if not c.run("test -d {}".format(code_dir), warn=True):
        cmd = "git clone user@vcshost:/path/to/repo/.git {}"
        c.run(cmd.format(code_dir))
    c.run("cd {} && git pull".format(code_dir))
    c.run("cd {} && touch app.wsgi".format(code_dir))
```