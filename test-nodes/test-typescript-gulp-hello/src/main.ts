import {sayHello} from "./greet"

interface Person {
    name: string,
    age: number,
}

function hello(p: Person) {
    console.log(`${p.name}: ${p.age}`);
}

hello({ name: "itang", age: 18 });

console.log(sayHello("TypeScript"));
