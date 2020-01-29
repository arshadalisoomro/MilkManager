/*
 *    Copyright 2020 In-Lab Inc. Pakistan
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package pk.edu.usindh.im.milkmanager.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.textfield.TextInputEditText

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import pk.edu.usindh.im.milkmanager.R
import pk.edu.usindh.im.milkmanager.model.Milk
import pk.inlab.app.expenselogger.adapter.MilkAdapter
import pk.edu.usindh.im.milkmanager.util.DateUtils
import pk.inlab.app.expenselogger.view.model.MilkViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mMilkViewModel: MilkViewModel
    private lateinit var mMilkAdapter: MilkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        mMilkAdapter = MilkAdapter(this)

        mMilkViewModel = ViewModelProviders.of(this).get(MilkViewModel::class.java)
        mMilkViewModel.allMilk.observe(
            this, Observer {
                Log.e("MAIN_ACTIVITY", "Called...")
                mMilkAdapter.addMilk(it)
                mMilkAdapter.notifyDataSetChanged()
            }
        )

        tv_milk_month_year.text = DateUtils.today

        mMilkViewModel.getSumOfAllWeight().observe(
            this,
            Observer {
                setDataToTextView(it)
            }
        )

        // Animate Items of RV
        val controller: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(this, R.anim.gridlayout_animation_from_bottom)

        rv_log.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        rv_log.layoutAnimation = controller

        rv_log.adapter = mMilkAdapter
        rv_log.scheduleLayoutAnimation()

        fab.setOnClickListener { view ->
            showInputDialog(view)
        }

    }

    private fun setDataToTextView(it: Int) {
        // Set Values to top TextViews
        tv_milk_month_paos.text = String.format(getString(R.string.pao_with_value), "$it")
        val kgs: Double = it / 4.0
        tv_milk_month_kilos.text = String.format(getString(R.string.kilo_with_value), "$kgs")
        val totalPrice: Double = it * (100 / 4.0)
        tv_milk_month_expense.text = String.format(getString(R.string.total_price), "$totalPrice")
    }

    @SuppressLint("InflateParams")
    private fun showInputDialog(view: View) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setTitle(getString(R.string.add_details))
        builder.setCancelable(false)
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        val viewInflated: View =
            LayoutInflater.from(view.context).inflate(R.layout.layout_add_milk, null)

        // Init Input Views
        val inputWeight = viewInflated.findViewById<TextInputEditText>(R.id.tie_weight)
        val inputDescription = viewInflated.findViewById<TextInputEditText>(R.id.tie_description)

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated)
        // Set up the buttons
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialog, _ ->
            dialog.dismiss()
            val milkWeight = inputWeight.text.toString()
            val milkDescription = inputDescription.text.toString()
            addNewMilk(Integer.valueOf(milkWeight), milkDescription)
        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    // Save new Milk
    private fun addNewMilk(milkWeight: Int, milkDescription: String) {
        mMilkViewModel.save(Milk(milkWeight, Date().time,  milkDescription))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
