const { soliditySha3 } = require("web3-utils");

const first = soliditySha3("Anki");

let results = [first];
let prev = first;
const argv = process.argv;
for (var i=2; i<argv.length; i++) {
    var v = soliditySha3(prev, argv[i]);
    prev = v;
    results.push(v);
}

console.log(JSON.stringify(results));