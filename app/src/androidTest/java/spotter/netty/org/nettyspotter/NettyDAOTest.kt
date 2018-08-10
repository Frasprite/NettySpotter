package spotter.netty.org.nettyspotter

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry

import junit.framework.Assert

import org.junit.After
import org.junit.Before
import org.junit.Test

import spotter.netty.org.nettyspotter.db.NettyDatabase
import spotter.netty.org.nettyspotter.model.Coordinates
import spotter.netty.org.nettyspotter.model.Fields
import spotter.netty.org.nettyspotter.model.Geometry
import spotter.netty.org.nettyspotter.model.Netty

class ComicDAOTest {

    private lateinit var appDatabase: NettyDatabase
    private lateinit var comic: LiveData<Netty>

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
        val entry = dao.nettysByName("qwerty")
        Assert.assertNotNull(entry)

        entry.map { Assert.assertNull(it) }
    }

    @Test
    fun insertNettyTest() {
        // Simulating entry insertion
        val fields = Fields(123, "dasd", "asdf", "asdf",
                "asdf", "er", "asdf", "poer")
        val coordinates = Coordinates(2234.0, 1243.0)
        val geometry = Geometry("asdf", coordinates)
        val netty = Netty("asd", "asd45", fields, geometry, "poere")
        val toilets: MutableList<Netty> = mutableListOf(netty)
        val data: List<Netty> = toilets
        appDatabase.nettysDao().insert(data)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}