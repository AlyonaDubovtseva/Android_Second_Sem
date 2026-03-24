package my.study.network

import my.study.network.pojo.Breed
import retrofit2.http.GET
import retrofit2.http.Path

interface CatApi {

    @GET("breeds")
    suspend fun getAllBreeds(): List<Breed>

    @GET("breeds/{breed_id}")
    suspend fun getBreedById(
        @Path(value = "breed_id") breedId: String
    ): Breed
}