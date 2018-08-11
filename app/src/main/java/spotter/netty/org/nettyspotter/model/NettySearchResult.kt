package spotter.netty.org.nettyspotter.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList

/**
 * Support class which contains LiveData<List<Netty>> holding query data,
 * and a LiveData<String> of network error state.
 * Used by UI to observe both search results data and network errors.
 */
data class NettySearchResult(
        val data: LiveData<PagedList<Netty>>,
        val networkErrors: LiveData<String>
)