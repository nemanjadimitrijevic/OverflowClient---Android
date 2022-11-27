package com.dimmythree.overflowclient.data

import com.dimmythree.overflowclient.data.models.Questions
import com.dimmythree.overflowclient.data.models.Tags
import com.dimmythree.overflowclient.data.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getTags(page: Int, count: Int): Flow<MyResult<Tags>?> {
        // TODO - load tags from database
        return flow {
            emit(MyResult.loading())
            val result = remoteDataSource.getTags(page, count)
            // TODO - cache tags into database
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getQuestionsForTag(tag: String, page: Int, count: Int): Flow<MyResult<Questions>?> {
        // TODO - load questions from the database
        return flow {
            emit(MyResult.loading())
            val result = remoteDataSource.getQuestionsForTag(tag, page, count)
            // TODO - cache questions for the tag as a key in the database
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}