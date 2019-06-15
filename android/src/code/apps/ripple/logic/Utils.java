package code.apps.ripple.logic;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.Toast;

public class Utils {
    public static void rateThisApp(Context context) {
        if (isNetConnected(context)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("market://details?id=");
            stringBuilder.append(context.getPackageName());
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())).addFlags(268435456));
            return;
        }
        Toast.makeText(context, "Please enable wifi or data from settings", 1).show();
    }

    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isAvailable() && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static void share(Context context, String str) {
        if (isNetConnected(context)) {
            context.getResources();
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", str);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("You should check this out, it's great - ");
            stringBuilder.append("https://play.google.com/store/apps/details?id=");
            stringBuilder.append(context.getPackageName());
            intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
            Intent createChooser = Intent.createChooser(intent, "Share via");
            createChooser.addFlags(268435456);
            context.startActivity(createChooser);
            return;
        }
        Toast.makeText(context, "Please enable wifi or data from settings", 1).show();
    }

    public static void openMarketLink(Context context, String str) {
        if (isNetConnected(context)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("market://details?id=");
            stringBuilder.append(str);
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString())).addFlags(268435456));
            return;
        }
        Toast.makeText(context, "Please enable wifi or data from settings", 1).show();
    }

    public static void moreapps(Context context, String str) {
        if (isNetConnected(context)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("market://search?q=pub:");
            stringBuilder.append(str);
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
            intent.addFlags(268435456);
            context.startActivity(intent);
            return;
        }
        Toast.makeText(context, "Please enable wifi or data from settings", 1).show();
    }

    public static void feedback(Context context, String str, String str2) {
        if (isNetConnected(context)) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("plain/text");
            intent.addFlags(268435456);
            intent.putExtra("android.intent.extra.EMAIL", new String[]{str2});
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Feedback for ");
            stringBuilder.append(str);
            intent.putExtra("android.intent.extra.SUBJECT", stringBuilder.toString());
            intent.putExtra("android.intent.extra.TEXT", "Write your feedback");
            context.startActivity(Intent.createChooser(intent, "Send mail...").addFlags(268435456));
            return;
        }
        Toast.makeText(context, "Please enable wifi or data from settings", 1).show();
    }
}
