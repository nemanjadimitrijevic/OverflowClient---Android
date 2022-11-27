package com.dimmythree.overflowclient.data.models

import com.google.gson.annotations.SerializedName

data class Question(

    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("owner") var owner: Owner? = Owner(),
    @SerializedName("is_answered") var isAnswered: Boolean? = null,
    @SerializedName("view_count") var viewCount: Int? = null,
    @SerializedName("accepted_answer_id") var acceptedAnswerId: Int? = null,
    @SerializedName("answer_count") var answerCount: Int? = null,
    @SerializedName("score") var score: Int? = null,
    @SerializedName("last_activity_date") var lastActivityDate: Long? = null,
    @SerializedName("creation_date") var creationDate: Long? = null,
    @SerializedName("last_edit_date") var lastEditDate: Long? = null,
    @SerializedName("question_id") var questionId: Int? = null,
    @SerializedName("content_license") var contentLicense: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("title") var title: String? = null

)