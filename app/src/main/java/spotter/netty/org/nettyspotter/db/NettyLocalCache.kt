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
    fun insert(nettyList: List<Netty>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            Log.d("NettyLocalCache", "inserting ${nettyList.size} toilets")
            nettyDao.insert(nettyList)
            insertFinished()
        }
    }

    /**
     * Request a LiveData<List<Netty>> from the DAO.
     */
    fun loadAllNetties(): DataSource.Factory<Int, Netty> {
        return nettyDao.loadAllNetties()
    }
}