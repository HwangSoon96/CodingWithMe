const bodyParser = require('body-parser');
const express = require("express");
const app = express();

app.use(bodyParser.json());
app.use(express.static('.'));

app.post("/exec", async (req,res) => {
  let lang = req.body.lang;
  let code = req.body.code;
  let testCase = req.body.testcase;

  // console.log(lang);
  try{
    const compileRunCheck = require(`./compile/${lang}.js`);

    let executeResult = [];
    for(tc of testCase){
      let res = await compileRunCheck.check(
        code,
        tc.input,
        tc.output
      );
      executeResult.push(res);
    }

    res.send({
      status : "success",
      result : executeResult
    })

  }catch(error){
    console.log(error);
    res.send({
      status : "invalid request",
      result : []
    });
  }

});

// app.get('/',(req,res) => res.sendFile(__dirname + '/editor/index.html'));

app.listen(4443,() => {
  console.log("server is running");
});