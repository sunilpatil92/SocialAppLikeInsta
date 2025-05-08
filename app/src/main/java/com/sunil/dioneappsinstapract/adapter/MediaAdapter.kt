package com.sunil.dioneappsinstapract.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sunil.dioneappsinstapract.constants.ExoplayerCache
import com.sunil.dioneappsinstapract.constants.constant
import com.sunil.dioneappsinstapract.databinding.ListDesignMediaImageBinding
import com.sunil.dioneappsinstapract.databinding.ListDesignMediaVideoBinding
import com.sunil.dioneappsinstapract.domain.model.Media

class MediaAdapter(context: Context, private val media: List<Media>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val mContext = context

    class ImageVH(private val binding: ListDesignMediaImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media) {
            val mPic = media.thumbnail
            var base = constant.pastImgBase

            binding.ivMediaPic.load(base + mPic) {
                crossfade(true)
            }
        }

        fun releasePlayer() {
            //nothing to do here
        }
    }

    class VideoVH(private val binding: ListDesignMediaVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var player: ExoPlayer? = null

        @OptIn(UnstableApi::class)
        fun bind(media: Media) {

            val thumbUrl = constant.vdoThumbBase + media.thumbnail
            val vdoUrl = constant.vdoBase + media.url.split(".")[0] + "/" + media.url
            Log.e("Dione : ", " Url : ${vdoUrl}")

            player?.release()
            player = ExoPlayer.Builder(binding.root.context)
                .setMediaSourceFactory(
                    DefaultMediaSourceFactory(binding.root.context).setDataSourceFactory(
                        ExoplayerCache.getCacheDataSourceFactory(binding.root.context)
                    )
                )
                .build().also { exoPlayer ->
                    binding.vdoPlayer.player = exoPlayer

                    val mediaItem = MediaItem.fromUri(vdoUrl)
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    //exoPlayer.playWhenReady = true
                }
        }

        fun play(){
            player?.playWhenReady=true
        }
        fun pause(){
            player?.playWhenReady=false
        }
        fun releasePlayer() {
            player?.pause()
            player?.release()
            player = null
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is VideoVH -> (holder as VideoVH).releasePlayer()
            is ImageVH -> (holder as ImageVH).releasePlayer()
        }
        super.onViewRecycled(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_IMAGE) {
            val binding = ListDesignMediaImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ImageVH(binding)
        } else {
            val binding = ListDesignMediaVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            VideoVH(binding)
        }
    }

    override fun getItemCount(): Int {
        return media.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageVH -> holder.bind(media[position])
            is VideoVH -> holder.bind(media[position])
        }
    }

    companion object {
        const val TYPE_IMAGE = 1
        const val TYPE_VIDEO = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (media[position].type == "Image") TYPE_IMAGE else TYPE_VIDEO
    }
}