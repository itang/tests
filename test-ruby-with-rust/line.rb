require "fiddle"  
require "fiddle/import"

module RustPoint  
  extend Fiddle::Importer

  dlload "./libline.so"

  extern "Point* make_point(int, int)"
  extern "double get_distance(Point*, Point*)"
end  