# impyar-tablet

Android app for the clinic tablets and phones: a thin **WebView** shell around the implant treatment-plan website.
Package `ir.yazd.implantplan`, minSdk 21, targetSdk 34, Java, no third-party libraries.

- Opens `HOME` (see `MainActivity.java`) and keeps navigation inside that host; other links open in the browser.
- Offline / no network: a retry page.
- `window.ImplantApp.saveFile(base64, name, mime)`: the page calls it to save a PDF into the device's Download folder and open it.
- Site updates show up in the app at once (nothing is bundled into the APK).

## Build
GitHub → **Actions → «Android app» → Run workflow** (about 2 minutes). The workflow writes the whole Android project
from its own text (`.github/workflows/android.yml`) over `android/` and builds that, so **editing `android/` alone does not
change the APK** (today the two match except `app/build.gradle`, whose versionCode/versionName come from the run; the launcher
icons are made from `icon-512.png` during the build). It builds `assembleRelease`, commits `dist/implant-plan.apk` and publishes a release.
Signing uses a **new random debug key on every run**, so every new APK needs the installed app removed first.
Making `android/` the real source and a permanent signing key are planned, not done: see impyar-backend `docs/PLAN-devops.md`.

`dist/implant-plan.apk` (app 2.0) is what the next release built from the repos will serve at `/implant-plan.apk`.
The live site still serves app 1.0 (from the old patch release m61a) until then.

## Address
Version 2.0 opens the main site on the Iran server, `https://alavi.itpelc.ir/` (`HOME` / `HOST` in `MainActivity.java`),
and keeps every page of that host inside the app (patient link `/p`, intake `/f`, downloads through the bridge).

Version 1.x (built in the old repo `implant-plan`) opened the GitHub Pages copy of the October site
(`alirezaworktime-art.github.io/implant-plan/`), which talks to the Cloudflare copy, not to the Iran server.
That GitHub Pages site was switched off on 2026-11-26, so 1.x shows an error page now.
Tablets with 1.x must **remove 1.x first, then install 2.0** (same package, different signing key: Android refuses an update).
