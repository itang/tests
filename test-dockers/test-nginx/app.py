from flask import Flask
from redis import Redis
from flask import request

app = Flask(__name__)
redis = Redis(host='redis', port=6379)

@app.route('/')
def hello():
    redis.incr('hits')
    return 'Hello World3! I have been seen {} times. {}'.format( redis.get('hits'), repr(request.headers))

if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
