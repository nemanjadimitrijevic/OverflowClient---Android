package com.dimmythree.overflowclient.data.models

import com.google.gson.annotations.SerializedName

data class Tag(

    @SerializedName("has_synonyms") var hasSynonyms: Boolean? = null,
    @SerializedName("is_moderator_only") var isModeratorOnly: Boolean? = null,
    @SerializedName("is_required") var isRequired: Boolean? = null,
    @SerializedName("count") var count: Int? = null,
    @SerializedName("name") var name: String? = null

)