var ffi = require('ffi');

var lib = ffi.Library('embed/target/release/libembed', {
  'process': ['void', []]
});

lib.process();

console.log("done!");
