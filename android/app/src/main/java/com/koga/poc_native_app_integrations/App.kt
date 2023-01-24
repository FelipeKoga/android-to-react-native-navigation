package com.koga.poc_native_app_integrations

import android.app.Application
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.soloader.SoLoader

class App : Application(), ReactApplication {
    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
    }

    private val reactNativeHost =
        object : ReactNativeHost(this) {
            override fun getUseDeveloperSupport() = BuildConfig.DEBUG
            override fun getJSMainModuleName(): String {
                return "react-native/index"
            }

            override fun getPackages(): List<ReactPackage> {
                return PackageList(this).packages.toMutableList()
            }
        }

    override fun getReactNativeHost(): ReactNativeHost = reactNativeHost
}