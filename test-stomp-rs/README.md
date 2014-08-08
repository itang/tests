https://github.com/itang/todo.itang.me/issues/36

## RabbitMQ stomp plugin

    $ sudo rabbitmq-plugins enable rabbitmq_stomp
    $ sudo rabbitmqctl stop
    $ sudo rabbitmq-server &

## test

    $ cargo run

    // new console
    $ ruby src/test.rb
    