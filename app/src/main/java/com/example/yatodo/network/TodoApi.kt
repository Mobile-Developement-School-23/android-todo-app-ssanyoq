package com.example.yatodo.network

import android.util.Log
import com.example.yatodo.data.TodoItem
import com.example.yatodo.network.ApiConstants.BASE_URL
import com.example.yatodo.network.ApiConstants.LAST_KNOWN_REVISION
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    suspend fun deleteItem(
        @Header(LAST_KNOWN_REVISION) revision: Int,
        @Path("id") id: String
    ): ItemResponse
}

/**
 * Implementation of [TodoApi] that uses retrofit.
 *
 * catches [HttpException] in case
 */
object TodoApiImpl {

    // debug
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(logging)
        .build()

    private val retrofit = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
    private val service = retrofit.create(TodoApi::class.java)

    private var revision = 0

    private suspend fun updateRevision() {
        val response = withContext(Dispatchers.IO) {
            service.getList()
        }
        revision = response.revision
    }

    suspend fun getList(): List<TodoItem> {
        val response = withContext(Dispatchers.IO) {
            service.getList()
        }
        revision = response.revision // same as updateRevision(), but here this is faster
        return response.list.map { TodoItem(it) }
    }

    suspend fun updateList(list: List<TodoItem>) {
        val request = list.map { SerializedTodoItem(it) }
        withContext(Dispatchers.IO) {
            service.updateList(revision, ListRequest(request))
        }
    }

    suspend fun addItem(todoItem: TodoItem) {
        updateRevision()
        try {
            withContext(Dispatchers.IO) {
                service.addItem(
                    revision,
                    ItemRequest(SerializedTodoItem(todoItem))
                )
            }
        } catch (exception: HttpException) {
            Log.e("TodoApiImpl", exception.message())
        }
    }

    suspend fun updateItem(todoItem: TodoItem) {
        updateRevision()
        try {
            withContext(Dispatchers.IO) {
                service.updateItem(
                    revision,
                    todoItem.taskId,
                    ItemRequest(SerializedTodoItem(todoItem))
                )
            }
        } catch (exception: HttpException) {
            Log.e("TodoApiImpl", exception.message())
        }
    }

    suspend fun deleteItem(id: String) {
        updateRevision()
        try {
            withContext(Dispatchers.IO) {
                service.deleteItem(
                    revision,
                    id
                )
            }
        } catch (exception: HttpException) {
            Log.e("TodoApiImpl", exception.message())
        }
    }

    /**
     * Made solely for easier checking of an item with **`TodoItemsRepository.checkItemById()`**
     */
    suspend fun checkItemById(id: String) {
        updateRevision()
        try {withContext(Dispatchers.IO) {
            val response = service.getItem(id)
            response.element.done = !response.element.done
            service.updateItem(revision, id, ItemRequest(response.element))
        }} catch (exception: HttpException) {
            Log.e("TodoApiImpl", exception.message())
        }

    }
}

