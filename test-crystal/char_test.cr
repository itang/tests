require "./util"

include Util

puts 'a'
puts "a.class: #{'a'.class}"
puts 'あ'

puts '\''
#puts 'a'.class.methods

## Char#ord
puts "'A'.ord: #{'A'.ord}, 'A'.ord.class: #{'A'.ord.class}"

## Int#chr
puts 'a'.ord.upto('z'.ord) {|x| puts x.chr }

## Char#to_i
puts '1'.to_i
assert '1'.to_i == 1 

struct Int 
  def first_char
    self.to_s[0]
  end
end

def i_to_first_char (i: Int)
 i.to_s[0]
end
puts "i_to_first_char(100):#{i_to_first_char(100)}"
puts "200.to_char: #{200.first_char}"

assert 900.first_char == '9'
