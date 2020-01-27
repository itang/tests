from invoke import task

@task
def consumer(c):
    'run consumer'
    c.run('go run cmd/consumer/main.go')

@task
def producer(c):
    'run producer'
    c.run('go run cmd/producer/main.go')
