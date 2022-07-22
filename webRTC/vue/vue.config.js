const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {
    // https 적용을 위한 코드
      https: true,
  },
})
