package spotter.netty.org.nettyspotter.api

import android.util.Log

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

import spotter.netty.org.nettyspotter.model.Netty
import spotter.netty.org.nettyspotter.rawmodel.RawData


private const val TAG = "SearchService"

/**
 * Search public bathroom based on a query.
 * Trigger a request to the RATP search API with the following params:
 * @param page request page index
 * @param itemsPerPage number of data to be returned by the RATP API per page
 *
 * The result of the request is handled by the implementation of the functions passed as params
 * @param onSuccess function that defines how to handle the list of entries received
 * @param onError function that defines how to handle request failure
 */
fun searchNetties(
        service: SearchService,
        page: Int,
        itemsPerPage: Int,
        onSuccess: (nettyList: List<Netty>) -> Unit,
        onError: (error: String) -> Unit) {
    Log.d(TAG, "page: $page, itemsPerPage: $itemsPerPage")

    service.searchNetties(page, itemsPerPage).enqueue(
            object : Callback<RawData> {
                override fun onFailure(call: Call<RawData>?, t: Throwable) {
                    Log.d(TAG, "onFailure - Failed to get data ${call.toString()} \n${t.message}")
                    onError(t.message ?: "Unknown error")
                }

                override fun onResponse(
                        call: Call<RawData>?,
                        response: Response<RawData>
                ) {
                    Log.d(TAG, "onResponse - Got a response $response")
                    if (response.isSuccessful) {
                        val rawData = response.body()
                        if (rawData != null) {
                            // Create a list of netties
                            val nettyList = ArrayList<Netty>()
                            val records = rawData.records
                            Log.v(TAG, "onResponse - Found ${records.size} records!")
                            for (record in rawData.records) {
                                var streetNumber = record.fields.numeroVoie
                                if (streetNumber == null) {
                                    streetNumber = "N.D."
                                }

                                nettyList.add(Netty(record.datasetid, record.recordid,
                                        record.geometry.type, record.geometry.coordinates[1],
                                        record.geometry.coordinates[0], record.recordTimestamp,
                                        record.fields.objectid, record.fields.source,
                                        record.fields.arrondissement, record.fields.nomVoie,
                                        record.fields.gestionnaire, streetNumber,
                                        record.fields.identifiant, record.fields.horairesOuverture))
                            }

                            Log.v(TAG, "onResponse - Adding ${nettyList.size} entries")

                            onSuccess(nettyList)
                        } else {
                            onError("Null response!")
                        }
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
    )
}

/**
 * RATP API communication setup via Retrofit.
 */
interface SearchService {
    /**
     * Get data function.
     */
    @GET("search/?dataset=sanisettesparis2011")
    fun searchNetties(@Query("start") page: Int,
                      @Query("rows") itemsPerPage: Int): Call<RawData>


    companion object {
        private const val BASE_URL = "https://data.ratp.fr/api/records/1.0/"

        fun create(): SearchService {
            val logger = HttpLoggingInterceptor()
            logger.level = Level.BASIC

            val client = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .build()

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SearchService::class.java)
        }
    }
}
