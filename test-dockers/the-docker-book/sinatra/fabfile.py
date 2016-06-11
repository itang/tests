from fabric.api import *


def build():
    local('docker build -t itang/sinatra .')


def run():
    local('docker run -p 4567:4567 -v $PWD/webapp:/opt/webapp --name webapp --link redis:db itang/sinatra')


def dl():
    local('wget --cut-dirs=3 -nH -r --no-parent http://dockerbook.com/code/5/sinatra/webapp/')
    local('chmod +x webapp/bin/webapp')


def test():
    local('http -f post http://localhost:4567/json name=Foo status=Bar')
    local('http http://localhost:4567/json')
