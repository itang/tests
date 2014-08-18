rack_app = lambda { |env| [200, {}, ["hello, world from rack"]] }

run rack_app
