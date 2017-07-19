# gem install toml

require 'toml'

parser1 = TOML::Parser.new(File.read('demo.toml')).parsed

parser2 = TOML.load(File.read('demo.toml'))
parser3 = TOML.load_file('demo.toml')

def assert_eq(a, b)
  raise 'Assert Error' if a != b
end

assert_eq parser1, parser2
assert_eq parser2, parser3

toml = parser3
puts toml
