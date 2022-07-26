// let pidusage = require('pidusage');
// const cp = require("child_process");

// const child = cp.spawn('dir');
// pidusage(child.pid, function (err, stats) {
//   console.log(stats);
// });

var pre_time = Date.now();
// var run = await exec(`a.exe < ${inFile}`);
setTimeout(()=>{
  var cur_time = Date.now();
  console.log(cur_time - pre_time);
},200);