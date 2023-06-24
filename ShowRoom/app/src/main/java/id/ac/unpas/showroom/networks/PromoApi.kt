package id.ac.unpas.showroom.networks

import id.ac.unpas.showroom.model.Promo
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PromoApi {
    @GET("promo")
    suspend fun all(): ApiResponse<PromoGetResponse>
    @GET("promo/{id}")
    suspend fun find(@Path("id") id: String):
            ApiResponse<PromoSingleGetResponse>
    @POST("promo")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body item: Promo):
            ApiResponse<PromoGetResponse>
    @PUT("promo/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") pathId: String,
                       @Body item: Promo):
            ApiResponse<PromoSingleGetResponse>
    @DELETE("promo/{id}")
    suspend fun delete(@Path("id") id: String):
            ApiResponse<PromoSingleGetResponse>
}