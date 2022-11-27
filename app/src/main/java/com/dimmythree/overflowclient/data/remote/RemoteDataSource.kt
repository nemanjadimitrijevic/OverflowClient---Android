package com.dimmythree.overflowclient.data.remote

import com.dimmythree.overflowclient.data.MyResult
import com.dimmythree.overflowclient.data.models.Questions
import com.dimmythree.overflowclient.data.models.Tags
import com.dimmythree.overflowclient.data.network.ApiService
import com.dimmythree.overflowclient.util.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    suspend fun getTags(page: Int, count: Int): MyResult<Tags> {
        val service = retrofit.create(ApiService::class.java)
        return getResponse(
            request = { service.getTags(page, count) },
            defaultErrorMessage = "Error fetching Tags"
        )
    }

    suspend fun getQuestionsForTag(tag: String, page: Int, count: Int): MyResult<Questions> {
        val service = retrofit.create(ApiService::class.java)
        return getResponse(
            request = { service.getQuestionsForTag(tag, page, count) },
            defaultErrorMessage = "Error fetching Tags"
        )
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): MyResult<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return MyResult.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                MyResult.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            MyResult.error("Unknown Error!", null)
        }
    }

}