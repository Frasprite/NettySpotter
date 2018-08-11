package spotter.netty.org.nettyspotter

import junit.framework.Assert

import org.junit.Test

import spotter.netty.org.nettyspotter.api.SearchService
import spotter.netty.org.nettyspotter.api.searchNetties

class SearchServiceTest {

    @Test
    fun searchNettiesTest() {

        searchNetties(SearchService.create(), 0, 30, { dataList ->
            Assert.assertNotNull(dataList)
            Assert.assertEquals(30, dataList.size)
        }, { _ ->
        })
    }
}