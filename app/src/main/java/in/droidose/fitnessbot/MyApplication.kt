package `in`.droidose.fitnessbot

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.lang.ref.WeakReference

/**
 * Created by rajatdhamija on 23/01/18.
 */
class MyApplication : Application() {

    override fun onCreate() {

        super.onCreate()
        mWeakReference = WeakReference(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    companion object {

        private var mWeakReference: WeakReference<Context>? = null
        val appContext: Context?
            get() = mWeakReference!!.get()
    }
}