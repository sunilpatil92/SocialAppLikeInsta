package com.sunil.dioneappsinstapract.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.size.Precision
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.sunil.dioneappsinstapract.R
import com.sunil.dioneappsinstapract.constants.constant
import com.sunil.dioneappsinstapract.constants.constant.Companion.getCurrentViewHolder
import com.sunil.dioneappsinstapract.databinding.ListDesignPostBinding
import com.sunil.dioneappsinstapract.databinding.ListDesignSponserBinding
import com.sunil.dioneappsinstapract.domain.model.Data
import com.sunil.dioneappsinstapract.domain.model.LikeUser
import com.sunil.dioneappsinstapract.domain.model.Post
import com.sunil.dioneappsinstapract.domain.model.Sponsor

class PostAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Data>()

    class ViewHolderSponer(private val binding: ListDesignSponserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sponsor: List<Sponsor>) {}
    }

    class ViewHolderMedia(val binding: ListDesignPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {

            if (post != null) {

                binding.tvPostDate.text = constant.convertDateTime(post.createAt)
                val pUserPic = post.userId.profile
                binding.tvPuserName.text = post.userId.name
                binding.tvDesc.text = post.description

                binding.ivPuserPic.load(constant.profileBase + pUserPic) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.iv_profile_circle)
                    error(R.drawable.iv_profile_circle)
                    size(100, 100)
                    scale(Scale.FIT)
                    precision(Precision.EXACT)
                }
                binding.pagerMedia.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        binding.tvCounter.text = "${position + 1}/${post.media.size}"
                    }
                })

                val mediaAdapter = MediaAdapter(binding.root.context, post.media)
                binding.pagerMedia.adapter = mediaAdapter
                binding.dotIndicator.attachTo(binding.pagerMedia)




                try {
                    val likeData = post.likeUser
                    binding.tvLikePerson.text = getLikePersonName(likeData)

                    val multiUser = listOf(
                        binding.avatar1,binding.avatar2,binding.avatar3
                    )

                    likeData.take(3).forEachIndexed{index, url->
                        multiUser[index].load("${constant.profileBase}/${url.userId.profile}"){
                            transformations(CircleCropTransformation())
                            crossfade(true)
                        }
                    }

                }catch (e:Exception){}



            } else {
                binding.tvPuserName.text = "Sponser"
            }


        }

        private fun getLikePersonName(likeUser: List<LikeUser>): CharSequence? {
            var str = ""

            if (likeUser.size>=3){
                str = "${likeUser.get(0).userId.name} - ${likeUser.get(1).userId.name} and ${likeUser.size-1} others Teps this"
            }else if (likeUser.size==2){
                str = "${likeUser.get(0).userId.name} - ${likeUser.get(1).userId.name} Teps this"
            }else if (likeUser.size==1){
                str = "${likeUser.get(0).userId.name} Teps this"
            }else{
                str=""
            }

            return str;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val bindingPost =
            ListDesignPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val bindingSponser =
            ListDesignSponserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return when (viewType) {
            1 -> ViewHolderMedia(bindingPost)
            2 -> ViewHolderSponer(bindingSponser)
            else -> throw IllegalArgumentException("Unknow viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val data: Data = items[position]
        if (data.post == null) {
            (holder as ViewHolderSponer).bind(items[position].sponsor)
        } else {
            (holder as ViewHolderMedia).bind(items[position].post)
        }

    }

    override fun getItemViewType(position: Int): Int {
        //Log.e("Adapter ","Type : "+items[position])
        val data: Data = items[position]
        if (data.post == null) {
            return 2
        } else {
            return 1
        }
        //return super.getItemViewType(position)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        if (holder is ViewHolderMedia) {
            val pagerAdapter = holder.binding.pagerMedia.adapter as? MediaAdapter
            pagerAdapter?.let {
                for (i in 0 until it.itemCount) {
                    val child = holder.binding.pagerMedia.getCurrentViewHolder()
                    if (child is MediaAdapter.VideoVH) {
                        child.releasePlayer()
                    }
                }
            }

        }
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addPost(newPost: List<Data>) {
        val start = items.size
        items.addAll(newPost)
        notifyItemRangeInserted(start, newPost.size)
    }

    fun clearPost() {
        items.clear()
        notifyDataSetChanged()
    }
}