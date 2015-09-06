require A

A.f "itang", name: "itang", age: 30

a = true
A.unless a do
  IO.puts "false"
else
  IO.puts "true"
end

A.unless false do
  IO.puts "false"
else
  IO.puts "true"
end
