package spotter.netty.org.nettyspotter.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_maps.*

import spotter.netty.org.nettyspotter.Injection
import spotter.netty.org.nettyspotter.R
import spotter.netty.org.nettyspotter.model.Netty
import spotter.netty.org.nettyspotter.viewmodel.SearchNettiesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchNettiesViewModel
    private val adapter = CustomAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get the view model
        viewModel = ViewModelProviders.of(this, Injection.provideViewModelFactory(this))
                .get(SearchNettiesViewModel::class.java)

        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", "onOptionsItemSelected - ${item.title} pressed")
        return when (item.itemId) {
            R.id.openMap -> {
                // Opening activity with map
                val mapIntent = Intent(this@MainActivity, MapsActivity::class.java)
                startActivity(mapIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAdapter() {
        list.adapter = adapter

        viewModel.netties.observe(this, Observer<PagedList<Netty>> {
            Log.d("MainActivity", "list: ${it?.size}")
            showEmptyList(it?.size == 0)
            adapter.submitList(it)
        })

        viewModel.networkErrors.observe(this, Observer<String> {
            Toast.makeText(this, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })

        viewModel.loadNetties()
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            list.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            list.visibility = View.VISIBLE
        }
    }
}
