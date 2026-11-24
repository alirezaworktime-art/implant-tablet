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

## Address
Version 2.0 opens the main site on the Iran server, `https://alavi.itpelc.ir/` (`HOME` / `HOST` in `MainActivity.java`),
and keeps every page of that host inside the app (patient link `/p`, intake `/f`, downloads through the bridge).

Version 1.x (built in the old repo `implant-plan`) opened the GitHub Pages copy of the October site
(`alirezaworktime-art.github.io/implant-plan/`), which talks to the Cloudflare copy, not to the Iran server.
Tablets with 1.x should install 2.0 (remove 1.x first if Android refuses the update: same package, debug signing).
