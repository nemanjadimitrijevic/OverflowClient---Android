package com.dimmythree.overflowclient.data.network

import com.dimmythree.overflowclient.data.models.Questions
import com.dimmythree.overflowclient.data.models.Tags
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("tags?order=desc&sort=popular")
    suspend fun getTags(@Query("page") page: Int, @Query("pagesize") count: Int): Response<Tags>

    @GET("tags/{tagName}/faq")
    suspend fun getQuestionsForTag(@Path("tagName") tag: String, @Query("page") page: Int, @Query("pagesize") count: Int): Response<Questions>
}