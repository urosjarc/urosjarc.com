import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppMain } from './app/app.main';


platformBrowserDynamic().bootstrapModule(AppMain)
  .catch(err => console.error(err));
