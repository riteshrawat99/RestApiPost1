package com.pupup.restapipost1

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserInterfaceApi {
    @POST("posts")
    fun postData(@Body requestBody: UserDataModel): Call<UserDataModel>
}