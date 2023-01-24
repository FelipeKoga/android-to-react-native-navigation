package com.koga.poc_native_app_integrations.util

import android.app.Activity
import android.content.Context
import android.content.MutableContextWrapper
import android.os.Bundle
import android.view.ViewGroup
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactRootView
import com.koga.poc_native_app_integrations.App
import java.util.*


object ReactNativeAppLoader {
    const val IDENTIFIER = "reactnative-app"
    private val CACHE: MutableMap<String, ReactRootView?> = WeakHashMap()

    fun preLoad(context: Context, componentName: String) {
        if (CACHE[componentName] != null) {
            return
        }
        val rootView = ReactRootView(MutableContextWrapper(context.applicationContext))

        rootView.startReactApplication(
            (context.applicationContext as App).reactNativeHost.reactInstanceManager,
            componentName,
            null
        )

        CACHE[componentName] = rootView
    }

    fun getReactRootView(activity: Activity?, componentName: String): ReactRootView? {
        val rootView = CACHE[componentName] ?: return null
        if (rootView.context is MutableContextWrapper) {
            (rootView.context as MutableContextWrapper).baseContext = activity
        }
        return rootView
    }

    fun startReactApplication(
        plainActivity: Activity?,
        reactInstanceManager: ReactInstanceManager?,
        componentName: String,
        launchOptions: Bundle?
    ): ReactRootView {
        val rootView = ReactRootView(plainActivity)
        rootView.startReactApplication(
            reactInstanceManager,
            componentName,
            launchOptions
        )
        CACHE[componentName] = rootView
        return rootView
    }

    fun detachView(componentName: String) {
        try {
            val rootView = CACHE[componentName] ?: return
            val parent = rootView.parent as ViewGroup
            parent.removeView(rootView)
            if (rootView.context is MutableContextWrapper) {
                (rootView.context as MutableContextWrapper).baseContext =
                    rootView.context.applicationContext
            }
            CACHE[componentName] = null
        } catch (e: Throwable) {
        }
    }
}
