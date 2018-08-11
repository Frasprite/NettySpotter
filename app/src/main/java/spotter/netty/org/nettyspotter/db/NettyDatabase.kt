package spotter.netty.org.nettyspotter.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import spotter.netty.org.nettyspotter.model.Netty

/**
 * Database schema that holds the list of public toilets, AKA netty.
 */
@Database(
        entities = [Netty::class],
        version = 1,
        exportSchema = false
)
abstract class NettyDatabase : RoomDatabase() {

    abstract fun nettysDao(): NettyDao

    companion object {

        @Volatile
        private var INSTANCE: NettyDatabase? = null

        fun getInstance(context: Context): NettyDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        NettyDatabase::class.java, "Netty.db")
                        .build()
    }
}