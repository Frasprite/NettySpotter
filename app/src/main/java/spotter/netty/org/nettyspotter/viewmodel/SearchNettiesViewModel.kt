package spotter.netty.org.nettyspotter.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList

import spotter.netty.org.nettyspotter.data.DataRepository
import spotter.netty.org.nettyspotter.model.Netty
import spotter.netty.org.nettyspotter.model.NettySearchResult

/**
 * ViewModel for the main activity screen.
 * The ViewModel works with the [DataRepository] to get the data.
 */
class SearchNettiesViewModel(private val repository: DataRepository) : ViewModel() {

    private val queryLiveData = MutableLiveData<String>()
    private val result: LiveData<NettySearchResult> = Transformations.map(queryLiveData) {
        repository.search()
    }

    val netties: LiveData<PagedList<Netty>> = Transformations.switchMap(result) {
        it -> it.data
    }
    val networkErrors: LiveData<String> = Transformations.switchMap(result) {
        it -> it.networkErrors
    }

    fun loadNetties() {
        queryLiveData.postValue("NettiesList")
    }
}
