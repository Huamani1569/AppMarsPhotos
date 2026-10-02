package com.jeanpiero.marsphotos.network

import com.jeanpiero.marsphotos.model.MarsPhoto
import retrofit2.http.GET

// Interface del servicio web REST: GET https://android-kotlin-fun-mars-server.appspot.com/photos
interface MarsApiService {

    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}
