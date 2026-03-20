package my.study.network.pojo

import com.google.gson.annotations.SerializedName

data class Breed(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("origin")
    val origin: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("life_span")
    val lifeSpan: String?
)
