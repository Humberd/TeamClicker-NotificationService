const fs = require('fs-extra');

fs.copySync("./mail_templates/html", "../notification-service/src/main/resources/mail_templates", {
  errorOnExist: true,
  filter: (src, dest) => {
    console.log(`Src: ${src} | Dest: ${dest}`);
    return true
  }
});
