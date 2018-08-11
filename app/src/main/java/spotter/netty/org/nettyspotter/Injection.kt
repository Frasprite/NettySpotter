package spotter.netty.org.nettyspotter

import android.arch.lifecycle.ViewModelProvider
import android.content.Context

import spotter.netty.org.nettyspotter.api.SearchService
import spotter.netty.org.nettyspotter.data.DataRepository
import spotter.netty.org.nettyspotter.db.NettyDatabase
import spotter.netty.org.nettyspotter.db.NettyLocalCache
import spotter.netty.org.nettyspotter.viewmodel.ViewModelFactory

import java.util.concurrent.Executors

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [NettyLocalCache] based on the database DAO.
     */
    private fun provideCache(context: Context): NettyLocalCache {
        val database = NettyDatabase.getInstance(context)
        return NettyLocalCache(database.nettysDao(), Executors.newSingleThreadExecutor())
    }

    /**
     * Creates an instance of [DataRepository] based on the [SearchService] and a
     * [NettyLocalCache]
     */
    private fun provideDataRepository(context: Context): DataRepository {
        return DataRepository(SearchService.create(), provideCache(context))
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideDataRepository(context))
    }

}
