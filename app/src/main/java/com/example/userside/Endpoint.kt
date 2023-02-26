package com.example.userside

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface Endpoint {

    @Multipart
    @POST("uploadMedia")
    suspend fun uploadMedia(@Part image: MultipartBody.Part,): Response<String?>


}