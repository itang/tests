const plugin = Deno.openPlugin("./native/debug/libplugin_native.dylib");

const { hello: rawHello } = plugin.ops;

export function hello(name?: string): string {
  const nameBuf = new TextEncoder().encode(name);
  return new TextDecoder().decode(rawHello.dispatch(nameBuf));
}

console.log(hello("itang"));