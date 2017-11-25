print("lua基础类型")
print(type("hello"))
print(type(1))
print(type(3.1415))
print(type(false))
print(type(nil))
print(type(print))
print("\n")

print("nil 空")
local num
print(num) -- nil 是一种类型，Lua 将 nil 用于表示“无效值”。一个变量在第一次赋值前的默认值是 nil，将 nil 赋予给一个全局变量就等同于删除它。
num = 100
print(num)
num = nil
print(num)

print("\n")

print("boolean 布尔")
-- true/false; Lua中的nil和false为"假", 其他所有值为"真"
local a = true
local b = 0
local c = nil
if a then
    print("a")
else
    print("not a")
end
if b then
    print("b")
else
    print("not b")
end
if c then
    print("c")
else
    print("not c")
end

print("number 数字")
-- 表示实数和double类似.可以使用数学函数math.floor(向下取整)和math.ceil(向上取整)进行取整操作.

local order = 3.99
local score = 98.01
print(math.floor(order))
print(math.ceil(score))

print("\n")
print("字符串")
-- 使用匹配的单引号, 'hello'
-- 使用匹配的双引号, "abc"
-- raw: [[abc\ndn]], 插入n个等号为第n级长括号
local str1 = "hello world"
local str2 = "hello lua"
local str3 = [["add\name",'hello']]
local str4 = [=[string have a [[]].]=]

print(str1) -->output:hello world
print(str2) -->output:hello lua
print(str3) -->output:"add\name",'hello'
print(str4) -->output:string have a [[]].

-- 字符串内化 "intern", 每一个Lua字符串在创建时都会插入到Lua虚拟机内部的一个全局的哈希表中.
-- 创建相同的 Lua 字符串并不会引入新的动态内存分配操作，所以相对便宜（但仍有全局哈希表查询的开销），
--内容相同的 Lua 字符串不会占用多份存储空间，
-- 已经创建好的 Lua 字符串之间进行相等性比较时是 O(1) 时间度的开销，而不是通常见到的 O(n).
print("\n")
print("table 表")
-- table 类型实现了一个抽象的"关联数组", "关联数组"是一种具有特殊索引方式的数组, 索引通常是字符串(string)或者number类型,
-- 但也可以是除nil 以外的任意类型的值.
local corp = {
    web = "www.google.com", -- 索引为字符串, key = "web", value = "www.google.com"
    telephone = "12121212", -- 索引为字符串
    staff = {"jack", "scott"}, -- 索引为字符串, 值也是一个表
    100872, -- 相当于 [1] = 100872, 此时索引为数字, key = 1, value = 100872
    100191, --相当于 [2] = 100191
    [10] = 360, -- 直接把数字索引给出
    ["city"] = "Beijing" --索引为字符串
}
print(corp.web) -->output:www.google.com
print(corp["telephone"]) -->output:12345678
print(corp[2]) -->output:100191
print(corp["city"]) -->output:"Beijing"
print(corp.staff[1]) -->output:Jack
print(corp[10]) -->output:360

-- 在内部实现上, table通常实现为一个哈希表, 一个数组或者两者的混合.具体的实现何种形式, 动态以来于具体的table的键分布特点.
print("\n")
print("函数")
-- 在Lua中, 函数也是一种数据类型,函数可以存储在变量中,可以通过参数传递给其他函数, 还可以作为其他函数的返回值.
local function foo()
    print("in the function")
    local x = 10
    local y = 20
    return x + y
end

local a = foo
print(a())

-- function foo()
-- end
-- 等价
-- foo = function ()
-- end

-- local function foo()
-- end
-- 类似
-- local foo = function()
-- end

print("\n")
print("表达式")

-- +	加法
-- -	减法
-- *	乘法
-- /	除法
-- ^	指数
-- %	取模
print(1 + 2)
print(5 / 10)
print(5.0 / 10)
print(2 ^ 10)
local num = 1357
print(num % 2)
print((num % 2) == 1)
print((num % 5) == 0)

-- <	小于
-- >	大于
-- <=	小于等于
-- >=	大于等于
-- ==	等于
-- ~=	不等于
print(1 < 2) -->打印 true
print(1 == 2) -->打印 false
print(1 ~= 2) -->打印 true
local a, b = true, false
print(a == b) -->打印 false

local a = {x = 1, y = 0}
local b = {x = 1, y = 0}
if a == b then
    print("a==b")
else
    print("a~=b")
end

print("逻辑运算符")
-- and	逻辑与
-- or	逻辑或
-- not
-- a and b 如果 a 为 nil，则返回 a，否则返回 b;
-- a or b 如果 a 为 nil，则返回 b，否则返回 a。
local c = nil
local d = 0
local e = 100
print(c and d) -->打印 nil
print(c and e) -->打印 nil
print(d and e) -->打印 100
print(c or d) -->打印 0
print(c or e) -->打印 100
print(not c) -->打印 true
print(not d) -->打印 false
print(false and "hello")
print(true and "hello")
print(true or "world")
print(false or "world")
-- 所有逻辑操作符将 false 和 nil 视作假，其他任何值视作真，对于 and 和 or，“短路求值”，对于 not，永远只返回 true 或者 false。

print("\n字符串连接")
-- 可以使用'..'连接字符串, 如果其中任意一个操作数是数字, lua会将这个数字转换为字符串. 连接操作只会创建一个新的字符串
-- 而不会改变原操作数. 也可以使用string库函数 string.format连接字符串
print("Hello " .. "World")
print(0 .. 1)
print(table.concat({1, 2, 3}))

print("\n控制结构")
print("if else")

x = 10
if x > 0 then
    print("x is a postive number")
end

if x > 0 then
    print("> 0")
else
    print("<=0")
end

score = 90
if score >= 100 then
    print(">=100")
elseif score >= 60 then
    print(">= 60 and <100")
else
    print("< 60")
end

print("\n while")
-- while 表达式 do
--   --body
-- end
x = 1
sum = 0
while x <= 5 do
    sum = sum + x
    x = x + 1
end
print(sum)

local t = {1, 3, 5, 8, 11, 18, 21}
local i
for i, v in ipairs(t) do
    if 11 == v then
        print("index[" .. i .. "] have right value[11]")
        break
    end
end

x = 10
repeat
    print(x)
    x = x + 1
until x > 20
print("for")
-- for var = begin, finish, step do
--   --body
-- end
for i = 1, 5 do
    print(i)
end

for i = 1, 10, 2 do
    print(i)
end
for i = 1, 10, 2 do
    print(i)
end

for i = 10, 1, -1 do
    print(i)
end

for i = 1, math.huge do
    if (0.3 * i ^ 3 - 20 * i ^ 2 - 500 >= 0) then
        print(i)
        break
    end
end
-- for 泛型
local a = {"a", "b", "c", "d"}
for i, v in ipairs(a) do
    print("index: ", i, " value:", v)
end

-- 打印table t中所有的key
for k in pairs(a) do
    print(k)
end
-- 打印table t中所有的key
for k in pairs({a = 100, b = 200}) do
    print(k)
end

local days = {
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
    "Sunday"
}

local revDays = {}
for k, v in pairs(days) do
    revDays[v] = k
end

-- print value
for k, v in pairs(revDays) do
    print("k:", k, " v:", v)
end

local function add(x, y)
    return x + y
    --print("add: I will return the result " .. (x + y))
    --因为前面有个return，若不注释该语句，则会报错
end

local function is_positive(x)
    if x > 0 then
        return x .. " is positive"
    else
        return x .. " is non-positive"
    end

    --由于return只出现在前面显式的语句块，所以此语句不注释也不会报错
    --，但是不会被执行，此处不会产生输出
    print("function end!")
end

sum = add(10, 20)
print("The sum is " .. sum) -->output:The sum is 30
answer = is_positive(-10)
print(answer)
local function foo()
    print("before")
    do
        return
    end
    print("after") -- 这一行语句永远不会执行到
end
foo()

local function test_scope_do()
    local a = 100
    do
        local a = 200
        print("nest", a)
    end
    print("outer", a)
end
test_scope_do()

print("\n function")
print("hello world")
local m = math.max(1, 5)
print("max is ", m)

-- global function
-- function function_name (arc)  -- arc 表示参数列表，函数的参数列表可以为空
--     -- body
-- end

-- local function
-- local function function_name (arc)
-- -- body
-- end

local function max(a, b)
    local temp = nil
    if (a > b) then
        temp = a
    else
        temp = b
    end
    return temp
end
local m = max(-12, 20)
print(m)

local function func()
    print("no parameter")
end
func()

local foo = {}
function foo.bar(a, b, c)
    return a + b + c
end
local ret = foo.bar(1, 2, 3)
print("ret", ret)

foo.bar2 = function(a, b, c)
    print(a, b, c)
end
foo.bar2()
foo.bar2(1)
foo.bar2(1, 2)
foo.bar2(1, 2, 3)
foo.bar2(1, 2, 3, 4) -- output: 1,2,3
foo.bar2(1, 2, 3, 4, 5)

-- lua函数的参数大部分是按传值传递的.值传递就是调用函数时,,实参把它的值通过赋值运算传递给形参,然后
-- 形参的改变和实参就没有关系了.在这个过程中, 实参是通过它在参数中的位置与形参匹配起来的.

local function swap(a, b)
    local temp = a
    a = b
    b = temp
    print(a, b)
end
local x = "hello"
local y = 20
print(x, y)
swap(x, y) --调用swap函数
print(x, y) --调用swap函数后，x和y的值并没有交换

-- 在调用函数的时候，若形参个数和实参个数不同时，Lua 会自动调整实参个数。
-- 调整规则：若实参个数大于形参个数，从左向右，多余的实参被忽略；
-- 若实参个数小于形参个数，从左向右，没有被实参初始化的形参会被初始化为 nil。
print("\n 变长参数")
local function func(...)
    local temp = {...}
    local ans = table.concat(temp, " ")

    print(ans)
end
func(1, 2, 3, 4, 5, 6)
print("具名参数")
-- Lua还支持通过名称来指定实参,这是偶要把所有的实参组织到一个table中, 并将这个table作为唯一的实参传给函数
local function change(arg)
    arg.width = arg.width * 2
    arg.height = arg.height * 2
    return arg
end
local rect = {width = 20, height = 15}
print(type(rect))
print("before change:", "width =", rect.width, "height =", rect.height)
rect = change(rect)
print("after  change:", "width  =", rect.width, "height =", rect.height)

-- 当函数参数是 table 类型时，传递进来的是 实际参数的引用，此时在函数内部对该 table 所做的修改，会直接对调用者所传递的实际参数生效，而无需自己返回结果和让调用者进行赋值。 我们把上面改变长方形长和宽的例子修改一下

-- 在常用基本类型中,除了table是按址传递类型外, 其他的都是按值传递参数.

print("函数返回值")
print("Lua具有一项与众不同的特性,允许函数返回多个值.Lua的库函数中")
local s, e = string.find("hello world", "llo")
print(s, e)

local function swap(a, b)
    return b, a
end
local x = 1
local y = 20
x, y = swap(x, y)
print(x, y)
print(
    "当函数返回值的个数和接收返回值的变量的个数不一致时，Lua 也会自动调整参数个数。调整规则： 若返回值个数大于接收变量的个数，多余的返回值会被忽略掉； 若返回值个数小于参数个数，从左向右，没有被返回值初始化的变量会被初始化为 nil。"
)
function init()
    return 1, "lua"
end
x = init()
print(x)
x, y, z = init()
print(x, y, z)

print("当一个函数有一个以上返回值，且函数调用不是一个列表表达式的最后一个元素，那么函数调用只会产生一个返回值, 也就是第一个返回值。")
local x, y, z = init(), 2 -- init 函数的位置不在最后，此时只返回 1
print(x, y, z)
local a, b, c = 2, init() -- init 函数的位置在最后，此时返回 1 和 "lua"
print(a, b, c) -->output  2  1  lua

print("不是最后参数位置,只取第一个值")
print(init(), 2) -->output  1  2
print("是最后一个值,取所有进行匹配")
print(2, init()) -->output  2  1  lua
local function f2(a, b)
    print(a, b)
end
f2(2, init())
print("确保只取函数返回值的第一个值, 可以使用括号运算符")
print((init()), 2)
print(2, (init()))

print("调用回调函数, 并把一个数组参数作为回调函数的参数")
-- local args = {...} or {}
-- method_name(unpack(args, 1, table.maxn(args)))
print("实参table中确定没有nil空洞,则可以简化为: method_name(unpack(args))")

-- add_task(end_time, callback, params)
-- if os.time() >= endTime then
--     callback(unpack(params, 1, table.maxn(params)))
-- end
--

local function run(x, y)
    print("run", x, y)
end

local function attack(targetId)
    print("targetId", targetId)
end

local function do_action(method, ...)
    local args = {...}
    method(unpack(args, 1, table.maxn(args)))
end
do_action(run, 1, 2)
do_action(attack, 1111)

print("\n 模块")
print("从lua 5.1 语言添加了对模块和包的支持. 一个lua模块的数据结构是用一个lua值(通常是一个Lua表或者lua函数")
print("一个Lua模块代码就是一个会返回这个Lua值的代码块.可以使用内建函数require()来加载和缓存模块")
print("一个代码模块,就是一个程序库,可以通过require来加载.模块加载的结果通过是一个Lua table, 这个表就像一个命名空间, 其内容就是模块中导出的所有东西,比如函数和变量")
print("require函数会返回Lua模块加载后的结果,即用于表示该Lua模块的Lua值")
local my = require("my")
print(my.greeting())
require("my")

dofile("my.lua")
dofile("my.lua")
local my2_file = loadfile("my.lua")
local my2 = my2_file()
my2.greeting()
g = loadstring("print(343)")
g()

print("string 模块")
print(string.upper("hello"))
print(string.char(96, 97, 98))
print(string.char()) -- 参数为空，默认是一个0，
-- 你可以用string.byte(string.char())测试一下
print(string.char(65, 66))
print(string.byte(string.char()))
print(string.char() == "")
print(string.lower("Hello"))
print(string.len("hello"))
print(string.len("你好"))
print("# 运算符来获取 Lua 字符串的长度")
print(#"hello")
print(#"你好")
print(string.match("hello lua", "lua"))
s = "hello world from Lua"
for w in string.gmatch(s, "%a+") do --匹配最长连续且只含字母的字符串
    print(w)
end
t = {}
s = "from=world, to=Lua"
for k, v in string.gmatch(s, "(%a+)=(%a+)") do --匹配两个最长连续且只含字母的
    t[k] = v --字符串，它们之间用等号连接
end
for k, v in pairs(t) do
    print(k, v)
end
print(string.rep("abc", 3)) --拷贝3次"abc"
print(string.sub("Hello Lua", 4, 7))
print(string.gsub("Lua Lua Lua", "Lua", "hello"))
print(string.reverse("Hello Lua")) --> output: auL olleH

print("\ntable 模块")
print(table.concat({12, 23}, ", "))
print(table.maxn({1, 2, 3}))
print(table.maxn({1, nil, 23, ["name"] = 11}))
print(#{1, nil, 23})

print("\nos模块")
local a = {year = 1970, month = 1, day = 1, hour = 8, min = 1}
print(os.time(a))
local day1 = {year = 2015, month = 7, day = 30}
local t1 = os.time(day1)

local day2 = {year = 2015, month = 7, day = 31}
local t2 = os.time(day2)
print(os.difftime(t2, t1)) -->output  86400
print(os.date("now is %Y-%m-%d %H:%M:%S"))

print("\n 数学函数")

math.randomseed(os.time())
print(math.random(100))

print("\n metatable")
metafraction = {}
function metafraction.__add(f1, f2)
    local sum = {}
    sum.b = f1.b * f2.b
    sum.a = f1.a * f2.b + f2.a * f1.b
    return sum
end

f1 = {a = 1, b = 2} -- Represents the fraction a/b.
f2 = {a = 2, b = 3}
setmetatable(f1, metafraction)
setmetatable(f2, metafraction)

s = f1 + f2
print(s.a, s.b)

defaultFavs = {animal = "gru", food = "donuts"}
myFavs = {food = "pizza"}
setmetatable(myFavs, {__index = defaultFavs})
eatenBy = myFavs.animal
print(eatenBy)
print(myFavs.food)
print("-- Direct table lookups that fail will retry using the metatable's  value, and this recurses.")

-- An __index value can also be a function(tbl, key) for more customized
-- lookups.
local m = {name = "itang"}
setmetatable(
    m,
    {
        __index = function(tbl, key)
            return "no found :" .. key
        end
    }
)
print(m.name)
print(m.age)

-- __add(a, b)                     for a + b
-- __sub(a, b)                     for a - b
-- __mul(a, b)                     for a * b
-- __div(a, b)                     for a / b
-- __mod(a, b)                     for a % b
-- __pow(a, b)                     for a ^ b
-- __unm(a)                        for -a
-- __concat(a, b)                  for a .. b
-- __len(a)                        for #a
-- __eq(a, b)                      for a == b
-- __lt(a, b)                      for a < b
-- __le(a, b)                      for a <= b
-- __index(a, b)  <fn or a table>  for a.b
-- __newindex(a, b, c)             for a.b = c
-- __call(a, ...)                  for a(...)
local mt = getmetatable(m)
mt.__call = function(a, ...)
    local aa = {...}
    print(table.concat(aa, ", "))
end
-- m.setmetatable
m(1, 2)

print("\n Class-like tables and inheritance.")
Dog = {}
function Dog:new()
    local newObj = {sound = "woof"}
    self.__index = self
    return setmetatable(newObj, self)
end

function Dog:makeSound()
    print("I say " .. self.sound)
end

mrDog = Dog:new()
mrDog:makeSound()

-- 1. Dog acts like a class; it's really a table.
-- 2. "function tablename:fn(...)" is the same as
--    "function tablename.fn(self, ...)", The : just adds a first arg called
--    self. Read 7 & 8 below for how self gets its value.
-- 3. newObj will be an instance of class Dog.
-- 4. "self" is the class being instantiated. Often self = Dog, but inheritance
--    can change it. newObj gets self's functions when we set both newObj's
--    metatable and self's __index to self.
-- 5. Reminder: setmetatable returns its first arg.
-- 6. The : works as in 2, but this time we expect self to be an instance
--    instead of a class.
-- 7. Same as Dog.new(Dog), so self = Dog in new().
-- 8. Same as mrDog.makeSound(mrDog); self = mrDog.

-- Inheritance example:

LoudDog = Dog:new() -- 1.

function LoudDog:makeSound()
    local s = self.sound .. " " -- 2.
    print(s .. s .. s)
end

seymour = LoudDog:new() -- 3.
seymour:makeSound() -- 'woof woof woof'      -- 4.

-- LoudDog gets Dog's methods and variables.
-- self has a 'sound' key from new()
-- Same as "LoudDog.new(LoudDog)", and converted to "Dog.new(LoudDog)" as
-- LoudDog has no 'new' key, but does have "__index = Dog" on its metatable.
-- Result: seymour's metatable is LoudDog, and "LoudDog.__index = Dog"
-- seymour.key will equal seymour.key, LoudDog.key, Dog.key, whichever
-- table is the first with the given key.
-- 4. The 'makeSound' key is found in LoudDog; this is same as
-- "LoudDog.makeSound(seymour)".
