package com.example.distractionblocker

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class UsageStatsService : AppCompatActivity() {
    private class UsageStatsStruct(
        val packageName: String,
        val totalTime: Long,
        val timesOpened: Int
    )

    private fun requestPermissionsIfNecessary() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }

    private fun queryUsageStats(packageName: String): UsageStatsStruct? {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager;

        val calendar = Calendar.getInstance();
        val endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, -1); // 1 day before now
        val startTime = calendar.getTimeInMillis();

        val usageStatsList =
            usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        if (usageStatsList == null || usageStatsList.isEmpty()) {
            println("The user may not have granted access to app usage.");
            requestPermissionsIfNecessary(); // Guide user to enable usage access
            return null;
        }

        val usageStats = usageStatsList.find { it.packageName == packageName }
        if (usageStats != null) {
            val totalTime = usageStats.totalTimeInForeground;
            val timesOpened =
                usageStats.javaClass.getDeclaredField("mLaunchCount").getInt(usageStats);
            return UsageStatsStruct(usageStats.packageName, totalTime, timesOpened)
        } else {
            return null
        }
    }

    private fun queryUsageStatsAll(): List<UsageStatsStruct> {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager;

        val calendar = Calendar.getInstance();
        val endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, -1); // 1 day before now
        val startTime = calendar.getTimeInMillis();

        val usageStatsList =
            usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        if (usageStatsList == null || usageStatsList.isEmpty()) {
            println("The user may not have granted access to app usage.");
            requestPermissionsIfNecessary(); // Guide user to enable usage access
            return emptyList();
        }

        val usageStatsStructList = mutableListOf<UsageStatsStruct>()
        usageStatsList.forEach { usageStats: UsageStats ->
            val totalTime = usageStats.totalTimeInForeground;

            // Note: This might not be available on all devices
            val timesOpened =
                usageStats.javaClass.getDeclaredField("mLaunchCount").getInt(usageStats);
            usageStatsStructList.add(
                UsageStatsStruct(
                    usageStats.packageName,
                    totalTime,
                    timesOpened
                )
            )
        }
        return usageStatsStructList;
    }
}