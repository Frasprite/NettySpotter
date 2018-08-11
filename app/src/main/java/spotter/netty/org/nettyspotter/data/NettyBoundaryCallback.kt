package spotter.netty.org.nettyspotter.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList

import spotter.netty.org.nettyspotter.api.SearchService
import spotter.netty.org.nettyspotter.api.searchNetties
import spotter.netty.org.nettyspotter.db.NettyLocalCache
import spotter.netty.org.nettyspotter.model.Netty


/**
 * Class which take care of loading new data.
 */
class NettyBoundaryCallback(
        private val service: SearchService,
        private val cache: NettyLocalCache
) : PagedList.BoundaryCallback<Netty>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }

    // Keep the last requested page.
    // When the request is successful, increment the page number.
    private var lastRequestedPage = 0

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String> get() = _networkErrors

    // Avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchNetties(service, lastRequestedPage, NETWORK_PAGE_SIZE, { dataList ->
            cache.insert(dataList) {
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

    override fun onZeroItemsLoaded() {
        requestAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Netty) {
        requestAndSaveData()
    }
}
