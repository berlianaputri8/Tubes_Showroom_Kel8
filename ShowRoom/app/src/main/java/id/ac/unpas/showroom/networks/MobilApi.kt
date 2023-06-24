package id.ac.unpas.showroom.networks

import id.ac.unpas.showroom.model.Mobil
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface MobilApi {
    @GET("mobil")
    suspend fun all(): ApiResponse<MobilGetResponse>
    @GET("mobil/{id}")
    suspend fun find(@Path("id") id: String):
            ApiResponse<MobilSingleGetResponse>
    @POST("mobil")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body item: Mobil):
            ApiResponse<MobilGetResponse>
    @PUT("mobil/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") pathId: String,
                       @Body item: Mobil
    ):
            ApiResponse<MobilSingleGetResponse>
    @DELETE("mobil/{id}")
    suspend fun delete(@Path("id") id: String):
            ApiResponse<MobilSingleGetResponse>
}