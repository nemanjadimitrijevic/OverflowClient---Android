package com.dimmythree.overflowclient.data

/**
 * Generic class for holding success response, error response and loading status
 */
data class MyResult<out T>(val status: Status, val data: T?, val myError: MyError?, val message: String?, val hasMore: Boolean = false) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?, hasMore: Boolean = false): MyResult<T> {
            return MyResult(Status.SUCCESS, data, null, null, hasMore)
        }

        fun <T> error(message: String, myError: MyError?): MyResult<T> {
            return MyResult(Status.ERROR, null, myError, message)
        }

        fun <T> loading(data: T? = null): MyResult<T> {
            return MyResult(Status.LOADING, data, null, null)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$myError, message=$message)"
    }
}