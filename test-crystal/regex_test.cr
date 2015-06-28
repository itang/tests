require "./util"

include Util

foo_or_bar = /foo|bar/
heeello = /h(e+)llo/
integer = /\d+/

#i: ignore case (PCRE_CASELESS)
#m: multiline (PCRE_MULTILINE)
#x: extended (PCRE_EXTENDED)
r = /foo/imx

assert (foo_or_bar =~ "foo") == 0
assert ("foo" =~ foo_or_bar) == 0

assert (foo_or_bar =~ "bar") == 0
assert ("bar" =~ foo_or_bar) == 0

unless foo_or_bar =~ "hello"
  assert true
else
  assert false
end

assert (heeello =~ "hello")
assert (heeello =~ "heello")
assert !(heeello =~ "hllo")

puts /(\d+)/.match "100-200-300"
puts "100-200-300".match /(\d+)/
md = ("100-200-300".match /(\d+)/)
if md
  puts "regex:#{md.regex}, length: #{md.length}, string: #{md.string}"
  puts md[0]
  (0...md.length).each do |x|
    puts md[x]
  end
end

md = ("100-200-300".match /(\d+)-(\d+)-(\d+)/)
if md
  puts "regex:#{md.regex}, length: #{md.length}, string: #{md.string}"
  puts md[0]
  p (0..md.length)
  (0..md.length).each do |x|
    puts md[x]
  end
end
