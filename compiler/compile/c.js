const util = require('util');
const exec = util.promisify(require('child_process').exec);
const fs = require('fs').promises;

// do it(error check);
exports.check = async (code,inFile,outFile) => {
  var readFile = await fs.readFile(outFile,'utf-8');
  var writefile = await fs.writeFile('a.c',code);
  // var compile = await exec("gcc a.c -o a.exe");
  var compile = await exec("gcc a.c");

  var pre_time = Date.now();
  var run = await exec(`./a.out < ${inFile}`);
  var cur_time = Date.now();
  
  
  await fs.unlink('a.c');
  await fs.unlink('a.out');
  return {
    time : cur_time - pre_time,
    output : run.stdout,
    success : (run.stdout == readFile)
  };
}