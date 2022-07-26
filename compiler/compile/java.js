const util = require('util');
const exec = util.promisify(require('child_process').exec);
const fs = require('fs').promises;

// do it(error check);
exports.check = async (code,inFile,outFile) => {
  var readFile = await fs.readFile(outFile,'utf-8');
  var writefile = await fs.writeFile('Main.java',code);
  var compile = await exec("javac -d . Main.java");

  var pre_time = Date.now();
  var run = await exec(`java -cp . Main < ${inFile}`);
  var cur_time = Date.now();
  
  
  await fs.unlink('Main.java');
  await fs.unlink('Main.class');
  return {
    time : cur_time - pre_time,
    output : run.stdout,
    success : (run.stdout == readFile)
  };
}