require "./util"

include Util

foo_or_bar = /foo|bar/
heeello = /h(e+)llo/
integer = /\d+/

#i: ignore case (PCRE_CASELESS)
#m: multiline (PCRE_MULTILINE)
#x: extended (PCRE_EXTENDED)
r = /foo/imx
