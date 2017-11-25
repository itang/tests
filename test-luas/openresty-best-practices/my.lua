print("in my module")
local foo = {}
local function getname()
    return "Lucy"
end
function foo.greeting()
    print("hello " .. getname())
end

return foo
