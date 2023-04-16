package com.fahmiproduction.githubuserapplication.api

import com.fahmiproduction.githubuserapplication.datamodel.UserDetailsResponse
import com.fahmiproduction.githubuserapplication.datamodel.UserResponse
import com.fahmiproduction.githubuserapplication.datamodel.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("users")
    fun getUsers(@Query("page") page: String): Call<List<UserResponse>>

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun findUser(@Query("q") username: String): Call<UserSearchResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailsResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserResponse>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowings(@Path("username") username: String): Call<List<UserResponse>>
}
