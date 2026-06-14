package com.manipai.data.api

import com.manipai.data.models.Settings
import com.manipai.data.models.User
import com.manipai.data.models.UserStats
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @GET("api/user/profile")
    suspend fun getProfile(): Response<User>

    @GET("api/user/settings")
    suspend fun getSettings(): Response<Settings>

    @PUT("api/user/settings")
    suspend fun updateSettings(@Body settings: Settings): Response<UpdateSettingsResponse>

    @GET("api/user/stats")
    suspend fun getStats(): Response<UserStats>
}

data class UpdateSettingsResponse(val message: String)
