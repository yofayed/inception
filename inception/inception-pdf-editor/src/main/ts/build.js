/*
 * Licensed to the Technische Universität Darmstadt under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The Technische Universität Darmstadt
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
const yargs = require('yargs/yargs')
const { hideBin } = require('yargs/helpers')
const esbuild = require('esbuild')
const fs = require('fs-extra')

const argv = yargs(hideBin(process.argv)).argv

let outbase = '../../../target/js/de/tudarmstadt/ukp/inception/pdfeditor/resources/'

if (!argv.live) {
  fs.emptyDirSync(outbase)
}
fs.mkdirsSync(`${outbase}`)

const defaults = {
  bundle: true,
  sourcemap: true,
  minify: !argv.live,
  target: 'es6',
  loader: { '.ts': 'ts' },
  logLevel: 'info'
}

if (argv.live) {
  defaults.watch = {
    onRebuild (error, result) {
      if (error) console.error('watch build failed:', error)
      else console.log('watch build succeeded:', result)
    }
  }
  outbase = '../../../target/classes/de/tudarmstadt/ukp/inception/pdfeditor/resources/'
}

esbuild.build(Object.assign({
  entryPoints: ['src/main.ts'],
  outfile: `${outbase}/PdfAnnotationEditor.min.js`,
  globalName: 'PdfAnnotationEditor'
}, defaults))

fs.copySync('pdfjs-web', `${outbase}`)
fs.copySync('node_modules/pdfjs-dist/build', `${outbase}`)
fs.copySync('node_modules/pdfjs-dist/cmaps', `${outbase}/cmaps`)
fs.copySync('node_modules/pdfjs-dist/standard_fonts', `${outbase}/standard_fonts`)
