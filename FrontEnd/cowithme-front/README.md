# CowithMe (cowithme)

CowithMe

## .vscode 설정 (settings.json) 아래 내용을 포함해 주시기 바랍니다.
```
{
  "eslint.validate": [
      "javascript",
      "javascriptreact",
      "typescript",
      "typescriptreact",
      "html",
      "vue",
      "markdown"
  ],
  "editor.codeActionsOnSave": {
      "source.fixAll.eslint": true
  },
  "editor.tabSize": 2,
}
```

## Install the dependencies
```bash
yarn
# or
npm install
```

### Start the app in development mode (hot-code reloading, error reporting, etc.)
```bash
quasar dev
```


### Build the app for production
```bash
quasar build
```

### Customize the configuration
See [Configuring quasar.config.js](https://v2.quasar.dev/quasar-cli-vite/quasar-config-js).
