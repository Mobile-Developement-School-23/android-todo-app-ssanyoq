package com.example.yatodo.network

import com.example.yatodo.network.ApiConstants.LAST_KNOWN_REVISION
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {

    /**
     * Recieves list from a server
     * @return List of items with metadata: revision and status code
     */
    @GET("list")
    suspend fun getList(): ListResponse

    /**
     * Recieves an item from a server by its [id]
     * @return Requested item(if exists) with metadata: revision and status code
     */
    @GET("list/{id}")
    suspend fun getItem(@Path("id") id: String): ItemResponse

    /**
     * Sends request to update list on server to one that is given in [item].
     * @return Server response
     */
    @PATCH("list")
    suspend fun updateList(
        @Header(LAST_KNOWN_REVISION) revision: Int,
        @Body item: ListRequest
    ): ListResponse

    /**
     * Updates item on server with id == [id] to an [item]
     * @return Server response
     */
    @PUT("list/{id}")
    suspend fun updateItem(
        @Header(LAST_KNOWN_REVISION) revision: Int,
        @Path("id") id: String,
        @Body item: ItemRequest
    ): ItemResponse

    /**
     * Uploads [item] to the server
     * @return Server response
     */
    @POST("list")
    suspend fun addItem(
        @Header(LAST_KNOWN_REVISION) revision: Int,
        @Body item: ItemRequest
    ): ItemResponse

    /**
     * Requests server to delete item with id == [id]
     * @return Server response
     */
    @DELETE("list/{id}")
    suspend fun delete(
        @Header(LAST_KNOWN_REVISION) revision: Int,
        @Path("id") id: String
    ): ItemResponse
}
