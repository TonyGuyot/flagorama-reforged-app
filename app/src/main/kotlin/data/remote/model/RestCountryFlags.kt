package io.github.tonyguyot.flagorama.data.remote.model

import com.google.gson.annotations.SerializedName

data class RestCountryFlags(
    @field:SerializedName("svg") val svgImageUrl: String
)