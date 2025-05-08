package com.sunil.dioneappsinstapract.domain.model

data class Post(
    val RepostPostId: Any,
    val TotalLike: Int,
    val _id: String,
    val allowDownloadPost: Boolean,
    val canReply: Boolean,
    val canShareCirclePost: Boolean,
    val circleId: String,
    val comments: Int,
    val countRepost: Int,
    val createAt: String,
    val description: String,
    val group: List<Group>,
    val hideLikeCount: Boolean,
    val isPin: Boolean,
    val likeUser: List<LikeUser>,
    val location: Location,
    val media: List<Media>,
    val postType: String,
    val privacy: String,
    val savePost: Boolean,
    val selfLike: Boolean,
    val settingRepost: Boolean,
    val shareCount: Int,
    val turnOffComment: Boolean,
    val userId: UserIdX
)