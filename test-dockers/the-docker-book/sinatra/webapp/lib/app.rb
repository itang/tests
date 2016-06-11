require "rubygems"
require "sinatra"
require "json"
require "uri"
require 'redis'

$redis = Redis.new(:host => 'db', :port => '6379')

class App < Sinatra::Application

  set :bind, '0.0.0.0'

  get '/' do
    "<h1>DockerBook Test Sinatra app</h1>"
  end

  post '/json/?' do
    json = params.to_json
    #uri = URI.parse(ENV['DB_PORT'])
    #redis = Redis.new(:host => uri.host, :port => uri.port)
    $redis.set 'json', json
    json
  end

  get '/json' do
    $redis.get 'json'
  end

end
