package com.example.test.mvvmsampleapp.global

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.example.test.mvvmsampleapp.R
import com.example.test.mvvmsampleapp.core.MyApplication
import java.io.File
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

class GlobalFunction private constructor() {
    private var calculatedStaggerItemWidth = 0f

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetworkInfo: NetworkInfo? = null
            activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    /**
     * Now time in second
     *
     * @return long timestamp
     */
    val currentTime: Long
        get() = System.currentTimeMillis() / 1000

    //Timestamp : 23/11/18 16:37:13 , 9210
    //Timestamp : 2311181637139210
    val currentTimeStamp: String
        get() {
            var str = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS", Locale.ENGLISH).format(System.currentTimeMillis())
            str = str.replace("-", "")
            str = str.replace(" ", "")
            str = str.replace(":", "")
            str = str.replace(".", "")

            return str
        }

    fun getDimenValue(mContext: Context, dimenId: Int): Float {
        return mContext.resources.getDimension(dimenId) / mContext.resources.displayMetrics.density
    }

    fun openFile(mcontext: Context, fileName: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.fromFile(File(fileName)),
                "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mcontext.startActivity(intent)
    }


    private fun encodeString(s: String): String {
        var data = ByteArray(0)

        try {
            data = s.toByteArray(StandardCharsets.UTF_8)

        } finally {

            return Base64.encodeToString(data, Base64.DEFAULT)

        }
    }

    fun getAppName(context: Context): String {
        val packagemanager: PackageManager
        packagemanager = context.packageManager
        try {
            val applicationinfo = packagemanager.getApplicationInfo(
                    context.packageName, 0)
            return packagemanager.getApplicationLabel(applicationinfo).toString()
        } catch (e: Exception) {
            return "can't get app porduct"
        }

    }

    @SuppressLint("NewApi")
    fun getDeviceSerial(mContext: Context): String {
        var serial = "~#"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            serial = Build.SERIAL
        return serial
    }

    fun getAndroidId(mContext: Context): String {
        return Settings.Secure.getString(mContext.contentResolver,
                Settings.Secure.ANDROID_ID)
    }

    fun isGpsOn(mContext: Context): Boolean {
        val mLocationManager = mContext
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun turnGPSOn(activity: Activity) {
        val provider = Settings.Secure.getString(activity.contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)

        if (!provider.contains("gps")) { //if gps is disabled
            val poke = Intent()
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider")
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
            poke.data = Uri.parse("3")
            activity.sendBroadcast(poke)
        }
    }

    fun openNetworkSetting(mContext: Context) {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    }

    fun shareText(mcontext: Context, text: String) {
        val intent: Intent
        intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (mcontext.packageManager.resolveActivity(intent,
                        PackageManager.MATCH_DEFAULT_ONLY) != null)
            mcontext.startActivity(intent)
    }


    fun deleteFile(fileName: String) {
        val file = File(fileName)
        file.delete()
    }

    fun copyTextIntoCliboard(mContext: Context, label: String, text: String) {
        val clipboard = mContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.primaryClip = clip
    }

    fun openBrowser(mContext: Context, url: String) {
        var url = url
        if (!url.startsWith(Constants.HTTP) && !url.startsWith(Constants.HTTPS))
            url = Constants.HTTP + url
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        mContext.startActivity(intent)
    }

    fun openMap(mContext: Context, address: String) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        /*    mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null)*/
        mContext.startActivity(mapIntent)
    }

    fun openDialPad(mContext: Context, phone: String) {
        val intent = Intent(Intent.ACTION_CALL_BUTTON)
        intent.data = Uri.parse(Constants.TEL + phone)
        mContext.startActivity(intent)
    }

    fun openEmail(mContext: Context, email: String) {
        val uri = Uri.parse("mailto:$email")
                .buildUpon()
                .build()

        val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
        mContext.startActivity(Intent.createChooser(emailIntent, "Send Email"))
    }
    /*

    public void composeEmail(String addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
*/

    fun changeStatusBarColor(activity: Activity, colorId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            // finally change the color
            window.statusBarColor = activity.resources.getColor(colorId)
        }
    }

    fun getWidthScreenSize(mContext: Context?): Int {
        var mContext = mContext
        if (mContext == null)
            mContext = MyApplication.getInstance().applicationContextt
        val displayMetrics = DisplayMetrics()
        val windowManager = mContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        /*   int height = displayMetrics.heightPixels;
         */
        return displayMetrics.widthPixels
    }

    fun getHeightScreenSize(mContext: Context?): Int {
        var mContext = mContext
        if (mContext == null)
            mContext = MyApplication.getInstance().applicationContextt
        val displayMetrics = DisplayMetrics()
        val windowManager = mContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        /*   int height = displayMetrics.heightPixels;
         */
        return displayMetrics.heightPixels
    }

    fun md5(input: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest(input.toByteArray())
            val number = BigInteger(1, messageDigest)
            val hashtext = StringBuilder(number.toString(16))
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length < 32) {
                hashtext.insert(0, "0")
            }
            return hashtext.toString()
        } catch (e: NoSuchAlgorithmException) {
            return input
        }

    }

    fun locateView(v: View?): Rect? {
        val loc_int = IntArray(2)
        if (v == null)
            return null
        try {
            v.getLocationOnScreen(loc_int)
        } catch (npe: NullPointerException) {
            return null
        }

        val location = Rect()
        location.left = loc_int[0]
        location.top = loc_int[1]
        location.right = loc_int[0] + v.width
        location.bottom = loc_int[1] + v.height
        return location
    }

    fun isActivityRunning(ctx: Context): Boolean {
        val activityManager = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = activityManager.getRunningTasks(Integer.MAX_VALUE)

        for (task in tasks) {
            if (ctx.packageName.equals(task.baseActivity.packageName, ignoreCase = true))
                return true
        }

        return false
    }

    /**
     * The method get the bitmap resource  and it produce new scaled bitmap
     *
     * @param bitmap
     * @param itemWidth
     * @return
     */
    fun scaleBitmapFromWidth(bitmap: Bitmap, itemWidth: Int): Bitmap {
        val w = bitmap.width
        val h = bitmap.height

        val resHeight = scaleImageFromWidth(w, h, itemWidth)
        return Bitmap.createScaledBitmap(bitmap, itemWidth, resHeight, false)
    }

    fun scaleFromWidth(sourceWidth: Int, sourceHeight: Int, itemWidth: Int): Pair<Int, Int> {
        val resHeight = scaleImageFromWidth(sourceWidth, sourceHeight, itemWidth)
        return Pair(itemWidth, resHeight)
    }

    /**
     * Adjusted width = <user-chosen height> * original width / original height
     * --- Below algorithm is in use ---
     * Adjusted height = <user-chosen width * original height></user-chosen>/ original width
     *
     * @param originalBitmapWidth
     * @param originalBitmapHeight
     * @param userChosenWidth
     * @return
    </user-chosen> */
    fun scaleImageFromWidth(originalBitmapWidth: Int, originalBitmapHeight: Int, userChosenWidth: Int): Int {
        return userChosenWidth * originalBitmapHeight / originalBitmapWidth
    }


    /**
     * The method get the bitmap resource  and it produce new scaled bitmap
     *
     * @param bitmap
     * @param itemHeight
     * @return
     */
    fun scaleBitmapFromHeight(bitmap: Bitmap, itemHeight: Int): Bitmap {
        val w = bitmap.width
        val h = bitmap.height

        val resHeight = scaleImageFromHeight(w, h, itemHeight)
        return Bitmap.createScaledBitmap(bitmap, itemHeight, resHeight, false)
    }

    /**
     * Adjusted width = <ic_user_avatar-chosen height> * original width / original height
     * --- Below algorithm is in use ---
     * Adjusted height = <ic_user_avatar-chosen width * original height></ic_user_avatar-chosen>/ original width
     *
     * @param originalBitmapWidth
     * @param originalBitmapHeight
     * @param userChosenWidth
     * @return
    </ic_user_avatar-chosen> */
    fun scaleImageFromHeight(originalBitmapWidth: Int, originalBitmapHeight: Int, userChosenHeight: Int): Int {

        return userChosenHeight * originalBitmapWidth / originalBitmapHeight
    }

    fun getWidthItemStagger(mContext: Context): Float {
        if (calculatedStaggerItemWidth == 0f) {
            val halfWidth = getWidthScreenSize(mContext).toFloat() / 2
            val padding = getDimenValue(mContext, R.dimen._21sdp)

            calculatedStaggerItemWidth = halfWidth - padding
        }

        return calculatedStaggerItemWidth
    }

    val isAppRunning: Boolean
        get() {
            val activityManager = MyApplication.getInstance().applicationContextt.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            val procInfos = activityManager.runningAppProcesses
            if (procInfos != null) {
                for (processInfo in procInfos) {
                    if (processInfo.processName == MyApplication.getInstance().packageName) {
                        return true
                    }
                }
            }
            return false
        }

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager = activity.getSystemService(
                    Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.message
        }
    }

    companion object {
        private var myInstance: GlobalFunction? = null

        val instance: GlobalFunction
            @Synchronized get() {
                if (myInstance == null) {
                    myInstance = GlobalFunction()
                }
                return myInstance!!
            }

        /**
         * If activity and service is running return true
         */

    }
}
