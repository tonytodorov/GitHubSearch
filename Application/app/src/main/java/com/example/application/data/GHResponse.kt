package com.example.application.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GHResponse (
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    val items: List<UserInfo>
)