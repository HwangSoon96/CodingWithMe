const bodyParser = require('body-parser');
const express = require("express");
const app = express();

app.use(bodyParser.json());
app.use(express.static('.'));

app.post("/exec", async (req,res) => {
  var problemNum = req.body.problemNum;
  var caseNum = req.body.caseNum;
  var lang = req.body.lang;
  var code = req.body.code;

  // console.log(lang);
  const compileRunCheck = require(`./compile/${lang}.js`);
  var result = await compileRunCheck.check(
    code,
    `./compile/problem/${problemNum}/${caseNum}.in`,
    `./compile/problem/${problemNum}/${caseNum}.out`
  );
  res.send(result);
  // res.send({
  //   time: 200,
  //   output: "output",
  //   success: true
  // });
});

// app.get('/',(req,res) => res.sendFile(__dirname + '/editor/index.html'));

app.listen(4443,() => {
  console.log("server is running");
});