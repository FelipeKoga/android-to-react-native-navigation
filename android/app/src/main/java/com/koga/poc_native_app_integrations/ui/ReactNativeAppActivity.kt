package com.koga.poc_native_app_integrations.ui


import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler
import com.facebook.soloader.SoLoader
import com.koga.poc_native_app_integrations.App
import com.koga.poc_native_app_integrations.util.ReactNativeAppLoader
import com.koga.poc_native_app_integrations.util.ReactNativeAppLoader.getReactRootView
import com.koga.poc_native_app_integrations.util.ReactNativeAppLoader.startReactApplication


class ReactNativeAppActivity : AppCompatActivity(), DefaultHardwareBackBtnHandler {
    private lateinit var reactRootView: ReactRootView

    private val reactInstanceManager: ReactInstanceManager by lazy {
        (application as App).reactNativeHost.reactInstanceManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SoLoader.init(this, false)

        getReactRootView(this, ReactNativeAppLoader.IDENTIFIER)?.let {
            reactRootView = it
        } ?: run {
            reactRootView = startReactApplication(
                this,
                reactInstanceManager,
                ReactNativeAppLoader.IDENTIFIER,
                bundleOf()
            )
        }

        ReactNativeAppLoader.detachView(ReactNativeAppLoader.IDENTIFIER)
        (reactRootView.parent as? ViewGroup)?.removeView(reactRootView)
        setContentView(reactRootView)
    }

    override fun invokeDefaultOnBackPressed() {
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        reactInstanceManager.onHostPause(this)
    }

    override fun onResume() {
        super.onResume()
        reactInstanceManager.onHostResume(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ReactNativeAppLoader.detachView(ReactNativeAppLoader.IDENTIFIER)
        reactInstanceManager.onHostDestroy(this)
        reactRootView.unmountReactApplication()
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        reactInstanceManager.onBackPressed()
        super.onBackPressed()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            reactInstanceManager.showDevOptionsDialog()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }
}