import { connect } from "https://denopkg.com/keroxp/deno-redis/redis.ts";

function assertEq<T>(a: T, b: T) {
  if (a !== b) {
    throw new Error(`b is expected ${a}, but is ${b}`);
  }
}

const redis = await connect({
  hostname: "127.0.0.1",
  port: 6379
});

const ok = await redis.set("hoge", "fuga");
const fuga: string = await redis.get("hoge");
console.log(fuga);

assertEq(fuga, "fuga");
