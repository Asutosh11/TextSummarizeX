package com.thoughtleaf.textsummarizex.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import com.thoughtleaf.textsummarizex.R
import com.thoughtleaf.textsummarizex.model.UrlRequest
import com.thoughtleaf.textsummarizex.model.UrlRequestDAO
import com.thoughtleaf.textsummarizex.ui.activity.MainActivity
import com.thoughtleaf.textsummarizex.ui.repository.ApiRepository
import com.thoughtleaf.textsummarizex.util.AppConstantsUtil
import com.thoughtleaf.textsummarizex.util.AppConstantsUtil.Companion.API_TYPE_SUMMARIZE_FILE
import com.thoughtleaf.textsummarizex.util.AppConstantsUtil.Companion.API_TYPE_SUMMARIZE_TEXT
import com.thoughtleaf.textsummarizex.util.AppConstantsUtil.Companion.API_TYPE_SUMMARIZE_URL


class ForegroundService : LifecycleService(), ApiRepository.callback  {

    private val CHANNEL_ID = "TextSummarizeX"
    private val context: Context? = null

    companion object {

        var type_of_api_being_fired: String? = null

        fun startService(context: Context, message: String, type_of_api_being_fired: String) {
            this.type_of_api_being_fired = type_of_api_being_fired
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        //do heavy work on a background thread

        startForeground(1, createNotification("Summarization in progress"))

        type_of_api_being_fired?.let {
            fireAPI(ForegroundService?.type_of_api_being_fired!!)
        }

        //stopSelf();
        return START_STICKY
    }

    fun createNotification(message: String) : Notification{
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("TextSummarizeX")
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()

        return notification
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "TextSummarizeX",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    fun fireAPI(type_of_api_being_fired: String){

        val apiRepository: ApiRepository = ApiRepository(this)

        when (type_of_api_being_fired) {

            API_TYPE_SUMMARIZE_FILE -> {
                /*apiRepository.summarizeFile()
                apiRepository.summarizeTextStatus?.observe(this) { status: Boolean ? ->

                }*/
            }

            API_TYPE_SUMMARIZE_TEXT -> {
               /* apiRepository.summarizeText(textRequest)
                apiRepository.summarizeFileStatus?.observe(this) {
                        status: Boolean ? ->

                }*/
            }

            API_TYPE_SUMMARIZE_URL -> {
                val urlRequestList = UrlRequestDAO.getAllSavedURLs()
                if (urlRequestList != null && urlRequestList.size > 0) {
                    val urlRequestObject =
                        urlRequestList.get(UrlRequestDAO.getAllSavedURLs().size - 1)
                    apiRepository.summarizeUrl(UrlRequest(urlRequestObject?.url ?: "", AppConstantsUtil.FRACTION_OF_ORIGINAL_TEXT_IN_SUMMARY))
                }
            }

            else -> return
        }
    }

    override fun closeForgroundService(message: String) {
        stopForeground(false)
        stopSelf()
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(0, createNotification(message))
    }
}