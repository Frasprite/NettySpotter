package spotter.netty.org.nettyspotter

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry

import junit.framework.Assert

import org.junit.After
import org.junit.Before
import org.junit.Test

import spotter.netty.org.nettyspotter.db.NettyDatabase
import spotter.netty.org.nettyspotter.model.Netty

class ComicDAOTest {

    private lateinit var appDatabase: NettyDatabase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, NettyDatabase::class.java).build()
    }

    @Test
    fun openDAOTest() {
        val dao = appDatabase.nettysDao()

        Assert.assertNotNull(dao)
    }

    @Test
    fun loadNettyTest() {
        val dao = appDatabase.nettysDao()
        val entry = dao.loadAllNetties()
        Assert.assertNotNull(entry)

        entry.map { Assert.assertNull(it) }
    }

    @Test
    fun insertNettyTest() {
        // Simulating entry insertion
        val netty = Netty("asd", "asd45", "poere", 0.0, 0.0,
                "asdfasd", 666, "typto", "portrt", "poerew",
                "dpfoere", "epriep", "po12", "poqwqw")
        val toilets: MutableList<Netty> = mutableListOf(netty)
        val data: List<Netty> = toilets
        appDatabase.nettysDao().insert(data)
    }

    @Test
    fun loadCloserNettiesTest() {
        // Default location : LatLng(48.856667, 2.351944)
        // NorthEast is Lat 48.865659395343435 Lng 2.36561508124438
        // SouthWest is Lat 48.847672988997694 Lng 2.338277829983431
        val dao = appDatabase.nettysDao()
        val entry = dao.loadCloserNetties(2.36561508124438, 48.865659395343435,
                2.338277829983431, 48.847672988997694)
        Assert.assertNotNull(entry)

        entry.map { Assert.assertNull(it) }
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}