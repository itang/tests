# gem install psych

require 'psych'

def load
  Psych.load(File.read('demo.yml'))
end

puts load.length
puts load['date']
puts load['set']
