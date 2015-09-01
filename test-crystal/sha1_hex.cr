require "openssl/sha1"
include OpenSSL

bytes = (SHA1.hash "1").to_slice
puts bytes, bytes.length

ret = bytes.map {|x| sprintf("%x", x)}.join("")
puts ret, ret.length

## output
#[53, 106, 25, 43, 121, 19, 176, 76, 84, 87, 77, 24, 194, 141, 70, 230, 57, 84, 40, 171]
#20
#356a192b7913b04c54574d18c28d46e6395428ab
#40
