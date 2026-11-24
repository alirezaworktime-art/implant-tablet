# implant-tablet

Android app for the clinic tablets and phones: a thin **WebView** shell around the implant treatment-plan website.
Package `ir.yazd.implantplan`, minSdk 21, targetSdk 34, Java, no third-party libraries.

- Opens `HOME` (see `MainActivity.java`) and keeps navigation inside that host; other links open in the browser.
- Offline / no network: a retry page.
- `window.ImplantApp.saveFile(base64, name, mime)`: the page calls it to save a PDF into the device's Download folder and open it.
- Site updates show up in the app at once (nothing is bundled into the APK).

## Build
GitHub → **Actions → «Android app» → Run workflow** (about 2 minutes). The workflow writes the project
(the same files that are in `android/` here), builds `assembleRelease`, commits `implant-plan.apk` and publishes a release.
Signing uses the GitHub debug key, so installing a new APK may need the old one removed first.

`dist/implant-plan.apk` is the build that the website serves today (`/implant-plan.apk`).

## Note
`HOME` still points to the GitHub Pages address of the first version (`alirezaworktime-art.github.io/implant-plan/`).
The main site now runs on the Iran server; check that this address still leads there, or change `HOME`/`HOST` and rebuild.
