from fabric.api import *


def build():
    local('docker build -t itang/nginx .')


def run():
    local('docker run -d -p 8000:80 --name website -v $PWD/website:/var/www/html/website itang/nginx nginx')
