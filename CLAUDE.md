# قوانین کار روی این مخزن (برای Claude Code)

این مخزن یکی از سه مخزن سامانه‌ی طرح درمان ایمپلنت است: `impyar-backend`، `impyar-frontend`، `impyar-tablet`. هر سه باید کنار هم در یک پوشه باشند (روی لپ‌تاپ مالک: `C:\Users\Admin\Documents\Projects\impyar\`).

## اول این را بخوان
- روی لپ‌تاپ مالک، فایل `C:\Users\Admin\Desktop\implant-cowork\CLAUDE.md` را کامل بخوان. قوانین ثابت، وضعیت نسخه‌ی زنده و کارهای باز آن‌جاست و بر این فایل مقدم است.
- فهرست کامل تصمیم‌های مالک: `C:\Users\Admin\Desktop\implant-cowork\project-docs\memory\implant-plan-site.md`.

## قوانین ثابت مالک (خلاصه)
- پاسخ همیشه فارسی، کوتاه و رک.
- قبل از هر تغییر، هر کامیت، هر push و هر انتشار، سؤال چندگزینه‌ای فارسی بپرس (گزینه‌ی پیشنهادی اول).
- رمز، کلید و توکن هرگز تایپ، چاپ، کپی یا کامیت نشود. پوشه‌ی `secrets/` فقط محلی است.
- این مخزن عمومی است: داده‌ی بیمار هرگز در کد، تست، لاگ یا کامیت نرود. قبل از هر push اجرا کن: `node C:\Users\Admin\Desktop\implant-cowork\repos\leak-check.mjs`.
- حذف، جابه‌جایی یا بازنویسی فایل فقط با اجازه.
- از ۲۰۲۶-۱۱-۲۷ (تصمیم مالک) برنامه‌نویسی دیگر با وصله نیست: هر تغییر مستقیم در همین مخزن‌ها (صفحه: `impyar-frontend/src/`، بعد `node tools/build.mjs`). انتشار: main → staging (خودکار روی demo.impyar.ir) → PR از staging به production (فقط همین؛ push مستقیم بسته است) → GitHub خودکار روی سرور یزد و Cloudflare منتشر می‌کند (`impyar-backend/ops/production/`، با بکاپ پایگاه، بررسی داده و برگشت خودکار). ادغام به production فقط با تصمیم مالک.
- بعد از هر تغییر `C:\Users\Admin\Desktop\implant-cowork\claude-code-kit\desktop-backup.ps1` را اجرا کن و مسیر بکاپ را بگو.
- hani.itpelc.ir فقط خواندنی است. هرگز وارد سایت زنده نشو و رمز تایپ نکن؛ بررسی زنده فقط شمارشی.
- در سایت‌های impyar.ir مبلغ‌ها هرگز نباید گم شوند. هیچ نقص ظاهری کوچکی پذیرفته نیست.
- نمودار دندان: سمت چپ تصویر = راست بیمار.
- اگر افزونه‌ی cc-plugin-privacy خواندن کد را رد کرد، دورش نزن و همان موقع به مالک بگو.

## ساخت و تست (در پوشه‌ی impyar-backend)
- نصب یک‌باره: `npm install`
- ساخت: `npm run build` (فرانت و تبلت را از پوشه‌های کناری می‌خواند و `dist/worker.mjs` می‌سازد)
- تست: `npm test` (در PowerShell و Git Bash هر دو کار می‌کند)
- تست کامل رگرسیون: `npm run test:all` (PowerShell، cmd، Git Bash و لینوکس؛ نتیجه در patches/reg-all.out؛ روی GitHub: Actions → Full regression)
- سرور محلی: `npm run dev`
