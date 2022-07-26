const util = require('util');
const exec = util.promisify(require('child_process').exec);
const fs = require('fs').promises;

// do it(error check);
exports.check = async (code,inFile,resultFile) => {
  let answer;
  let sourceFile;
  try {
    answer = await fs.readFile(resultFile,'utf-8');
    sourceFile = await fs.writeFile('a.c',code);
  } catch (error) {
    return {
      time : 0,
      output : error.stderr,
      success : false
    };
  }

  try {
    await exec("gcc a.c");
    let pre_time = Date.now();
    let run = await exec(`./a.out < ${inFile}`);
    let cur_time = Date.now();

    try {
      await fs.unlink('a.c');
      await fs.unlink('a.out');
    } catch (error) { }

    return {
      time : cur_time - pre_time,
      output : run.stdout,
      success : (run.stdout == answer)
    };

  } catch (error) {
    
    try {
      await fs.unlink('a.c');
      await fs.unlink('a.out');
    } catch (error) { }

    return {
      time : 0,
      output : error.stderr,
      success : false
    };

  }
}