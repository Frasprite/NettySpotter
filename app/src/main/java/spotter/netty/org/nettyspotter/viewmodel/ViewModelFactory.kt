package spotter.netty.org.nettyspotter.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import spotter.netty.org.nettyspotter.data.DataRepository

/**
 * Factory for ViewModels.
 */
class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchNettiesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchNettiesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}