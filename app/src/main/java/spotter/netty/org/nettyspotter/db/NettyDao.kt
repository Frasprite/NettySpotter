package spotter.netty.org.nettyspotter.db

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import spotter.netty.org.nettyspotter.model.Netty

/**
 * Room data access object for accessing the [Netty] table.
 */
@Dao
interface NettyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Netty>)

    // Do a similar query as the search API:
    // Look for public toilets that contain the query string in the name or in the description
    // and order those results descending, by the number of stars and then by name
    @Query("SELECT * FROM nettys WHERE (datasetId LIKE :queryString) OR (recordId LIKE " +
            ":queryString)")
    fun nettysByName(queryString: String): DataSource.Factory<Int, Netty>

}