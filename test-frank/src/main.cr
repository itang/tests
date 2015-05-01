require "frank"

get "/" do
  "Hello, World (#{Time.now})"
end

get "/bench" do
  "Hello,World!"
end
