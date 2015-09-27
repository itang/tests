#include "mruby.h"
#include "mruby/compile.h"

int
main(void)
{
  mrb_state *mrb = mrb_open();
  if (!mrb) { /* handle error */ }
  // mrb_load_nstring() for strings without null terminator or with known length
  mrb_load_string(mrb, "puts 'hello world'");
  mrb_close(mrb);
}
