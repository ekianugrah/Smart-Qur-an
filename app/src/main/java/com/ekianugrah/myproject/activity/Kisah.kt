package com.ekianugrah.myproject.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ekianugrah.myproject.R
import com.ekianugrah.myproject.adapter.KisahAdapter
import com.ekianugrah.myproject.model.ResponseMain
import com.ekianugrah.myproject.viewmodel.ViewModelMain
import kotlinx.android.synthetic.main.kisah.*

class Kisah : AppCompatActivity() {
    private lateinit var viewModel: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kisah)
        val ToolBarAtas2: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(ToolBarAtas2)
        ToolBarAtas2.setLogoDescription(resources.getString(R.string.app_name))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initView()
        attachObserve()
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(ViewModelMain::class.java)
        viewModel.getDataView()
    }

    private fun attachObserve() {
        viewModel.responGetData.observe(this, Observer { showData(it) })
        viewModel.isError.observe(this, Observer { showError(it) })
        viewModel.isLoading.observe(this, Observer { showLoading(it) })
    }

    private fun showData(it: List<ResponseMain>) {
        val adapter = KisahAdapter(it, object : KisahAdapter.OnClickListener {
            override fun detail(item: ResponseMain) {
                val intent = Intent(this@Kisah, KisahActivity::class.java)
                intent.putExtra("data", item)
                startActivity(intent)
            }

        })
        recycler_main.adapter = adapter
    }

    private fun showError(it: Throwable?) {
        Toast.makeText(this, it?.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(it: Boolean?) {
        if (it == true) {
            progress_main.visibility = View.VISIBLE
        } else {
            progress_main.visibility = View.GONE
        }
    }
}