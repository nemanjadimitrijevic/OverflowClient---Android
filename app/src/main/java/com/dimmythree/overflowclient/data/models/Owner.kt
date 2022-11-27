package com.dimmythree.overflowclient.data.models

import com.google.gson.annotations.SerializedName

data class Owner(

    @SerializedName("account_id") var accountId: Int? = null,
    @SerializedName("reputation") var reputation: Int? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("user_type") var userType: String? = null,
    @SerializedName("accept_rate") var acceptRate: Int? = null,
    @SerializedName("profile_image") var profileImage: String? = null,
    @SerializedName("display_name") var displayName: String? = null,
    @SerializedName("link") var link: String? = null

)