package com.example.distractionblocker;

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent

class AppLockAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            println("Package Name: $packageName")
            if (packageName != null && isAppLocked(packageName)) {
                Log.d("AppLocker", "Locked app opened: $packageName")
                showLockScreen()
            }
        }
    }

    override fun onInterrupt() {
        // Handle interrupt
    }

    private fun isAppLocked(packageName: String): Boolean {
        // Add logic to check if the app is locked
        // This could be a list of locked apps stored in SharedPreferences or database
        val lockedApps = listOf("org.fdroid.fdroid")
        return lockedApps.contains(packageName)
    }

    private fun showLockScreen() {
        // Add logic to show the lock screen
        // This might involve starting an activity with a specific intent flag
        val intent = Intent(this, LockScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}