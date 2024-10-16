package com.example.distractionblocker

import android.content.Context
import android.content.pm.PackageInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(private val context: Context, private val appList: List<PackageInfo>) :
    RecyclerView.Adapter<AppAdapter.AppViewHolder>() {

    inner class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appIcon: ImageView = view.findViewById(R.id.appIcon)
        val appName: TextView = view.findViewById(R.id.appName)
        val gearIcon: ImageButton = view.findViewById(R.id.appSettings)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false)
        return AppViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        val packageInfo = appList[position]
        val packageManager = context.packageManager

        holder.appIcon.setImageDrawable(packageManager.getApplicationIcon(packageInfo.applicationInfo))
        holder.appName.text = packageManager.getApplicationLabel(packageInfo.applicationInfo)

        holder.gearIcon.setOnClickListener {
            showPopupMenu(holder.gearIcon)
        }
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    private fun showPopupMenu(view: View) {
        // Show fragment
        val dialog = AppSettingsDialogFragment()
        dialog.show((context as MainActivity).supportFragmentManager, "AppSettingsDialogFragment")
    }
}