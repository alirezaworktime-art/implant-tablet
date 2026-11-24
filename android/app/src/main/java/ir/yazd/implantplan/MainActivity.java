package ir.yazd.implantplan;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends Activity {
  static final String HOME = "https://alirezaworktime-art.github.io/implant-plan/";
  static final String HOST = "alirezaworktime-art.github.io";
  WebView web;

  @Override protected void onCreate(Bundle state) {
    super.onCreate(state);
    Window w = getWindow();
    w.setStatusBarColor(Color.parseColor("#17624f"));
    web = new WebView(this);
    setContentView(web);
    WebSettings s = web.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    s.setDatabaseEnabled(true);
    s.setLoadWithOverviewMode(true);
    s.setUseWideViewPort(true);
    s.setSupportZoom(false);
    s.setUserAgentString(s.getUserAgentString() + " ImplantPlanApp/1");
    web.addJavascriptInterface(new Bridge(), "ImplantApp");
    web.setWebChromeClient(new WebChromeClient());
    web.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
        Uri u = r.getUrl();
        if (HOST.equals(u.getHost()) && u.getPath() != null && u.getPath().startsWith("/implant-plan")) return false;
        try { startActivity(new Intent(Intent.ACTION_VIEW, u)); } catch (Exception e) { }
        return true;
      }
      @Override public void onReceivedError(WebView v, WebResourceRequest r, WebResourceError e) {
        if (!r.isForMainFrame()) return;
        String html = "<html dir='rtl'><body style='font-family:sans-serif;text-align:center;padding:40px 20px;color:#222'>"
            + "<h3>اتصال به اینترنت برقرار نشد</h3><p>اینترنت گوشی را چک کنید.</p>"
            + "<button style='font-size:18px;padding:10px 28px;border:0;border-radius:10px;background:#17624f;color:#fff' "
            + "onclick=\"location.href='" + HOME + "'\">تلاش دوباره</button></body></html>";
        v.loadDataWithBaseURL(HOME, html, "text/html", "utf-8", HOME);
      }
    });
    if (state != null) web.restoreState(state); else web.loadUrl(HOME);
  }

  @Override protected void onSaveInstanceState(Bundle out) { super.onSaveInstanceState(out); web.saveState(out); }
  @Override public void onBackPressed() { if (web.canGoBack()) web.goBack(); else super.onBackPressed(); }

  void toast(final String t) { runOnUiThread(() -> Toast.makeText(MainActivity.this, t, Toast.LENGTH_LONG).show()); }

  /** called from the page: window.ImplantApp.saveFile(base64, fileName, mime) */
  class Bridge {
    @JavascriptInterface public void saveFile(String b64, String name, String mime) {
      try {
        byte[] data = Base64.decode(b64, Base64.DEFAULT);
        if (name == null || name.trim().isEmpty()) name = "plan.pdf";
        name = name.replaceAll("[\\\\/:*?\"<>|]+", " ");
        if (mime == null || mime.isEmpty()) mime = "application/pdf";
        if (Build.VERSION.SDK_INT >= 29) {
          ContentResolver cr = getContentResolver();
          ContentValues cv = new ContentValues();
          cv.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
          cv.put(MediaStore.MediaColumns.MIME_TYPE, mime);
          cv.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
          Uri uri = cr.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cv);
          if (uri == null) throw new Exception("no uri");
          try (OutputStream os = cr.openOutputStream(uri)) { os.write(data); }
          toast("در پوشه‌ی Download ذخیره شد: " + name);
          final Uri open = uri; final String m = mime;
          runOnUiThread(() -> {
            try {
              Intent i = new Intent(Intent.ACTION_VIEW);
              i.setDataAndType(open, m);
              i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(i);
            } catch (Exception e) { }
          });
        } else {
          File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
          if (dir == null) dir = getFilesDir();
          File f = new File(dir, name);
          try (FileOutputStream fo = new FileOutputStream(f)) { fo.write(data); }
          DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
          if (dm != null) dm.addCompletedDownload(name, name, true, mime, f.getAbsolutePath(), data.length, true);
          toast("ذخیره شد: " + name);
        }
      } catch (Exception e) {
        toast("ذخیره‌ی فایل ممکن نشد: " + e.getMessage());
      }
    }
  }
}
