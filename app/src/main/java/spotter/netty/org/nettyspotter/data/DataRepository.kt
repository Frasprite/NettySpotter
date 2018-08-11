package spotter.netty.org.nettyspotter.data

import android.arch.paging.LivePagedListBuilder

import android.util.Log

import spotter.netty.org.nettyspotter.api.SearchService
import spotter.netty.org.nettyspotter.db.NettyLocalCache
import spotter.netty.org.nettyspotter.model.NettySearchResult


/**
 * Repository class that works with local and remote data sources.
 * This class is responsible for triggering API requests and saving the response into database.
 */
class DataRepository(
        private val service: SearchService,
        private val cache: NettyLocalCache
) {

    /**
     * Search new data.
     */
    fun search(): NettySearchResult {
        Log.d("DataRepository", "start")

        // Get data source factory from the local cache
        val dataSourceFactory = cache.loadAllNetties()

        // Construct the boundary callback
        val boundaryCallback = NettyBoundaryCallback(service, cache)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()

        // Get the network errors exposed by the boundary callback
        return NettySearchResult(data, networkErrors)
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }
}
