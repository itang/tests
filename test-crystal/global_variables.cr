require "./util"
include Util

# Global variables start with a dollar sign ($). They are declared when you first assign them a value.
$year = 2014
assert_equal $year.class, Int32

# Their type is the combined type of all expressions that were assigned to them. Additionally, if your program reads a global variable before it was ever assigned a value it will also have the Nil type.
p $notdeclare
assert_equal $notdeclare.class, Nil

$year = %w[1 2 3 4]
p $year.class
p $year
assert_equal $year.class, Array(String)
