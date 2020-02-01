import ky from 'https://unpkg.com/ky/index.js';

const parsed = await ky['post']('http://mockbin.com/request', { json: { foo: true } }).json();

console.log(JSON.stringify(parsed, null, 2));
