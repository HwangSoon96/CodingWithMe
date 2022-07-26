const util = require('util');
const exec = util.promisify(require('child_process').exec);
const fs = require('fs').promises;

// do it(error check);
exports.check = async (code,inputData,answerData) => {
  let sourceFile;
  let inputFile;
  let answerFile;
  try {
    sourceFile = await fs.writeFile('main.c',code);
    await fs.writeFile('input.in', inputData);
    await fs.writeFile('output.out', answerData);

    inputFile = await fs.readFile('input.in','utf-8');
    answerFile = await fs.readFile('output.out','utf-8');
  } catch (error) {
    return {
      time : 0,
      output : error.stderr,
      success : false
    };
  }

  try {
    await exec("gcc main.c");
    let pre_time = Date.now();
    let run = await exec(`./main.out < ${inputFile}`);
    let cur_time = Date.now();

    try {
      await fs.unlink('main.c');
      await fs.unlink('main.out');
    } catch (error) { }

    return {
      time : cur_time - pre_time,
      output : run.stdout,
      success : (run.stdout.trim() == answerFile)
    };

  } catch (error) {
    
    try {
      await fs.unlink('main.c');
      await fs.unlink('main.out');
    } catch (error) { }

    return {
      time : 0,
      output : error.stderr,
      success : false
    };

  }
}