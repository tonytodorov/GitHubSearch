package com.example.application.repositories

import com.example.application.data.GHResponse
import com.example.application.data.UserInfo
import com.example.application.utils.jsonInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import kotlin.coroutines.resume

class MainRepository @Inject constructor(private val httpClient: OkHttpClient) {

    fun getData(): List<String> {
        return listOf("1", "2", "3")
    }

    suspend fun getItems(query: String, page: Int, perPage:Int = 10): List<UserInfo> {

        return withContext(Dispatchers.IO) {
            suspendCancellableCoroutine { continuation ->

                val token = "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"
                val request = Request.Builder()
                    .url("https://api.github.com/search/users?q=$query&page=$page&per_page=$perPage")
                    .header("Authorization", "Bearer $token")
                    .get()
                    .build()

                val response = httpClient.newCall(request).execute()

                val responseBody: String = response.body?.string()!!
                val ghResponse: GHResponse = jsonInstance.decodeFromString(responseBody)

                continuation.resume(ghResponse.items)
            }
        }
    }
}

