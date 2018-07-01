const nodemon = require('nodemon');
const exec = require('child_process').exec;
const fs = require('fs-extra');

fs.ensureDirSync("./mail_templates/html");

nodemon({
  exec: 'echo @@ Build triggered @@',
  ext: 'mjml',
  ignore: [
    '.git',
    'node_modules',
  ],
  watch: [
    './mail_templates/',
  ],
});

const buildAllCmd = () =>
    `mjml ./mail_templates/*.mjml -o ./mail_templates/html/`;
const buildOneCmd = filePath =>
    `mjml ${filePath} -o ./mail_templates/html/`;


async function sh(cmd, log = false) {
  console.log(`Executing: ${cmd}`);
  return new Promise(function(resolve, reject) {
    exec(cmd, (err, stdout, stderr) => {
      if (err) {
        reject(err);
        console.log(err);
      } else {
        resolve({stdout, stderr});
        if (log && stdout.trim()) {
          console.log(stdout);
        }
      }
    });
  });
}
let initiated = false;

nodemon.on('start', async () => {
  if (!initiated) {
    console.log('-- App has started --');
    console.log("Building all files...");
    await sh(buildAllCmd(), true);
    initiated = true;
    console.log("Successfully built all files");
  }
}).on('quit', () => {
  console.log('App has quit');
  process.exit();
}).on('restart', async filePaths => {
  console.log(`-- App restarted --`);
  for (const filePath of filePaths) {
    console.log(`Building '${filePath}'...`);
    await sh(buildOneCmd(filePath), true);
    console.log(`Successfully built '${filePath}'`);
  }
});