package id.ac.unpas.showroom.networks

import id.ac.unpas.showroom.model.Motor
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MotorApi {
    @GET("sepeda-motor")
    suspend fun all(): ApiResponse<MotorGetResponse>
    @GET("sepeda-motor/{id}")
    suspend fun find(@Path("id") id: String):
            ApiResponse<MotorSingleGetResponse>
    @POST("sepeda-motor")
    @Headers("Content-Type: application/json")
    suspend fun insert(@Body item: Motor):
            ApiResponse<MotorGetResponse>
    @PUT("sepeda-motor/{id}")
    @Headers("Content-Type: application/json")
    suspend fun update(@Path("id") pathId: String,
                       @Body item: Motor):
            ApiResponse<MotorSingleGetResponse>
    @DELETE("sepeda-motor/{id}")
    suspend fun delete(@Path("id") id: String):
            ApiResponse<MotorSingleGetResponse>
}