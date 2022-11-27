package com.dimmythree.overflowclient.util

import com.dimmythree.overflowclient.data.MyError
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

object ErrorUtils {

    fun parseError(response: Response<*>, retrofit: Retrofit): MyError? {
        val converter = retrofit.responseBodyConverter<MyError>(MyError::class.java, arrayOfNulls(0))
        return try {
            converter.convert(response.errorBody()!!)
        } catch (e: IOException) {
            MyError()
        }
    }
}