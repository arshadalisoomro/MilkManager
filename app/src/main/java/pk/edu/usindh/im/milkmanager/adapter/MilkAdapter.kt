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

package pk.inlab.app.expenselogger.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import pk.edu.usindh.im.milkmanager.MilkApplication
import pk.edu.usindh.im.milkmanager.R
import pk.edu.usindh.im.milkmanager.model.Milk

import pk.edu.usindh.im.milkmanager.util.DateUtils
import pk.inlab.app.expenselogger.view.custom.LabelTextView
import pk.inlab.app.expenselogger.view.model.MilkViewModel
import java.util.*
import kotlin.collections.ArrayList


class MilkAdapter(val context: Context): RecyclerView.Adapter<MilkAdapter.MilkHolder>() {

    private var allMilk: List<Milk>
    private var mMilkViewModel: MilkViewModel

    init {
        allMilk = ArrayList()
        mMilkViewModel = MilkViewModel(MilkApplication.instance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilkHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.milk_item, parent, false)
        return MilkHolder(itemView)
    }

    override fun onBindViewHolder(holder: MilkHolder, position: Int) {
        val currentMilk = allMilk[position]
        holder.weight.text = "${currentMilk.weight}"
        holder.date.text = DateUtils.getDate(Date(currentMilk.date))

        holder.item.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.see_details))
            builder.setCancelable(false)
            // Set message
            builder.setMessage(
                String.format(context.getString(R.string.details_message),
                    currentMilk.weight,
                    DateUtils.getDate(Date(currentMilk.date)),
                    currentMilk.description))
            // Set up the buttons
            builder.setPositiveButton(
                android.R.string.ok
            ) { dialog, _ ->
                dialog.dismiss()
            }
            // show the dialog
            builder.show()

        }

        holder.item.setOnLongClickListener{
            val popup = PopupMenu(context, it)
            // Inflate the menu from xml
            // Inflate the menu from xml
            popup.inflate(R.menu.menu_item_popup)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_edit -> {
                        editMilk(currentMilk)
                        return@setOnMenuItemClickListener true
                    }

                    R.id.menu_delete -> {
                        deleteMilk(currentMilk)
                        return@setOnMenuItemClickListener true
                    }
                    else -> {
                        return@setOnMenuItemClickListener false
                    }
                }
            }

            // Show Popup menu
            val menuHelper =
                MenuPopupHelper(context, popup.menu as MenuBuilder, it)
            menuHelper.setForceShowIcon(true)
            menuHelper.show()

            return@setOnLongClickListener true

        }
    }

    @SuppressLint("InflateParams")
    private fun editMilk(currentMilk: Milk) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.edit_details))
        builder.setCancelable(false)
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        val viewInflated: View =
            LayoutInflater.from(context).inflate(R.layout.layout_add_milk, null)

        // Init Input Views
        val inputWeight = viewInflated.findViewById<TextInputEditText>(R.id.tie_weight)
        val inputDescription = viewInflated.findViewById<TextInputEditText>(R.id.tie_description)

        // Now set values to each EditText
        inputWeight.setText("${currentMilk.weight}")
        inputDescription.setText(currentMilk.description)

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated)
        // Set up the buttons
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialog, _ ->

            currentMilk.weight  = inputWeight.text.toString().toInt()
            currentMilk.description = inputDescription.text.toString()
            mMilkViewModel.update(currentMilk)

            dialog.dismiss()

        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, _ -> dialog.cancel() }

        // Show dialog
        builder.show()
    }

    private fun deleteMilk(currentMilk: Milk) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.delete_title))
        builder.setCancelable(false)
        // Set message
        builder.setMessage(
            String.format(
                context.getString(R.string.delete_message),
                currentMilk.weight,
                DateUtils.getDate(Date(currentMilk.date)),
                currentMilk.description
            )
        )
        // Set up the buttons
        builder.setPositiveButton(
            android.R.string.ok
        ) { dialog, _ ->
            run {
                mMilkViewModel.delete(currentMilk)
                dialog.dismiss()
            }
        }
        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, _ -> dialog.cancel() }

        // show the dialog
        builder.show()
    }

    override fun getItemCount(): Int {
        return allMilk.size
    }

    fun addMilk(allMilk: List<Milk>) {
        this.allMilk = allMilk
        notifyDataSetChanged()
    }

    inner class MilkHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: LinearLayout = itemView.findViewById(R.id.item_layout)
        var weight: LabelTextView = itemView.findViewById(R.id.item_weight)
        var date: LabelTextView = itemView.findViewById(R.id.item_date)
    }

}