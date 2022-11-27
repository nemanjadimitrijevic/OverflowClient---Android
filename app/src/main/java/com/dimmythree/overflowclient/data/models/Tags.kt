package com.dimmythree.overflowclient.data.models

import com.google.gson.annotations.SerializedName

data class Tags(

    @SerializedName("items") var tags: ArrayList<Tag> = arrayListOf(),
    @SerializedName("has_more") var hasMore: Boolean? = null,
    @SerializedName("quota_max") var quotaMax: Int? = null,
    @SerializedName("quota_remaining") var quotaRemaining: Int? = null

)