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

    @Query("SELECT * FROM netties")
    fun loadAllNetties(): DataSource.Factory<Int, Netty>

    @Query("SELECT * FROM netties WHERE (longitude AND latitude) BETWEEN (:northLongitude AND :southLongitude) AND (:northLatitude AND :southLatitude)")
    fun loadCloserNetties(northLongitude: Double, northLatitude: Double,
                          southLongitude: Double, southLatitude: Double): List<Netty>

}