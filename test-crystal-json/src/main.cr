require "frank"
require "./api/**"

get "/" do
  user = API::Person.new("Hello", "World")
  user.to_json()
end

get "/info" do
  Time.now
end
