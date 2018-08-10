package spotter.netty.org.nettyspotter.db

import android.arch.paging.DataSource
import android.util.Log
import spotter.netty.org.nettyspotter.model.Netty
import java.util.concurrent.Executor

/**
 * Class that handles the DAO local data source.
 * This ensures that methods are triggered on the correct executor.
 */
class NettyLocalCache(
        private val nettyDao: NettyDao,
        private val ioExecutor: Executor
) {

    /**
     * Insert a list of public toilets in the database, on a background thread.
     */
    fun insert(nettys: List<Netty>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            Log.d("NettyLocalCache", "inserting ${nettys.size} toilets")
            nettyDao.insert(nettys)
            insertFinished()
        }
    }

    /**
     * Request a LiveData<List<Netty>> from the DAO, based on a specific field TODO.
     *
     * @param name the search we are looking for
     */
    fun nettysByName(name: String): DataSource.Factory<Int, Netty> {
        // appending '%' so we can allow other characters to be before and after the query string
        val query = "%${name.replace(' ', '%')}%"
        return nettyDao.nettysByName(query)
    }
}