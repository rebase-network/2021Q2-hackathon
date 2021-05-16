/*
 * @Author: 33357
 * @Date: 2021-05-14 14:00:53
 * @LastEditTime: 2021-05-15 14:49:51
 * @LastEditors: 33357
 */
// http://eslint.org/docs/user-guide/configuring

module.exports = {
  root: true,
  parser: 'babel-eslint',
  parserOptions: {
    sourceType: 'module'
  },
  env: {
    browser: true,
  },
  // https://github.com/feross/standard/blob/master/RULES.md#javascript-standard-style
  extends: 'standard',
  // required to lint *.vue files
  plugins: [
    'html'
  ],
  // add your custom rules here
  'rules': {
    // allow paren-less arrow functions
    'arrow-parens': 0,
    // allow async-await
    'generator-star-spacing': 0,
    // allow debugger during development
    'no-debugger': process.env.NODE_ENV === 'production' ? 2 : 0,
    'semi':0,
    "space-before-function-paren":0,
    'space-before-blocks':0,
    'spaced-comment':0,
    "indent": 0,
    "one-var": 0,
    // "quotes": 0,
    // 'eol-last': 0,
    // 'semi': ['error', 'always'],
  }
}
