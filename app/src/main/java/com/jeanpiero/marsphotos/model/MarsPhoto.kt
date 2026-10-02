package com.jeanpiero.marsphotos.model

import com.google.gson.annotations.SerializedName

// MODELO: cada objeto del JSON {"id": "424905", "img_src": "https://..."}
data class MarsPhoto(
    val id: String,
    // @SerializedName enlaza el nombre del JSON (img_src) con el nombre en Kotlin (imgSrc)
    @SerializedName("img_src")
    val imgSrc: String
)
