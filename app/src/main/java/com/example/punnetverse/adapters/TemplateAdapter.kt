package com.example.punnetverse.adapters

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.punnetverse.api.MemeApiService
import com.example.punnetverse.data.Template
import com.example.punnetverse.databinding.ItemTemp2Binding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TemplateAdapter(private var mList: ArrayList<Template>) : RecyclerView.Adapter<TemplateAdapter.TempViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TempViewHolder {
        val binding = ItemTemp2Binding.inflate(LayoutInflater.from(parent.context))
        return TempViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TempViewHolder, position: Int) {
        holder.setData(mList[position])
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    inner class TempViewHolder(private val binding: ItemTemp2Binding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(tempData: Template) {
            //download
            binding.btnDownload.setOnClickListener {
                Toast.makeText(binding.root.context, "Download will start shortly, check notification!", Toast.LENGTH_SHORT).show()
                startDownload( tempData.url, binding.root.context, "punnet-meme-temp${Math.random()}")
            }
            //code for exoplayer
            binding.caption.text = tempData.captions
            val simpleExoPlayer = SimpleExoPlayer.Builder(binding.exoPlayerView.context).build()
            binding.exoPlayerView.player = simpleExoPlayer
            val mediaItem = MediaItem.fromUri(tempData.url)
            simpleExoPlayer.addMediaItem(mediaItem)
            simpleExoPlayer.prepare()
            simpleExoPlayer.play()
            simpleExoPlayer.pause()
            var firstTimePlayed = true
            simpleExoPlayer.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    //if video is playing and this the first time
                    // we are playing it the just take it as view count
                    if(isPlaying && firstTimePlayed) {
                        MemeApiService.memesInstance.addView(tempData.id)
                        firstTimePlayed = false
                    }
                }
            })
        }
    }

    fun updateList(list: ArrayList<Template>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    private fun startDownload(url: String, context: Context, fileName: String) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val request = DownloadManager.Request(Uri.parse(url))
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(true)
                    .setTitle(fileName)
                    .setDescription("Downloading $fileName")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                request.setMimeType("video/mp4")
                request.setTitle("Downloading video...")
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$fileName-video.mp4")
                downloadManager.enqueue(request)
            } catch (e: Exception) {
                Toast.makeText(context, "Please try again!!", Toast.LENGTH_SHORT)
            }
        }
    }
}
