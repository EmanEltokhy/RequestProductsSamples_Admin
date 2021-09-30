package com.example.productssamples.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productssamples.Model.DrugsModel
import com.example.productssamples.R
import com.example.productssamples.UpdateDrugsActivity
import java.io.Serializable

class DrugListAdapter (private val drugs: MutableList<DrugsModel> , private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun updateData(newDrugs: List<DrugsModel>){
        drugs.clear()
        drugs.addAll(newDrugs)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.drug_item, parent, false)
        return DrugsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is DrugsViewHolder -> {
                val drug_item: DrugsModel = drugs[position]
                holder.drugName.setText(drug_item.Drug_Name)
                holder.drugPrice.setText(drug_item.Drug_Price)
                Glide
                    .with(holder.drugImg.rootView)
                    .load(drug_item.Drug_Image)
                    .into(holder.drugImg)

                holder.delete_item.setOnClickListener(View.OnClickListener { v: View? ->
                    val alertDialog =
                        AlertDialog.Builder(context)
                            .setTitle("Delete " + drugs.get(position).Drug_Name)
                            .setMessage(
                                "Do you want to delete " + drugs.get(position).Drug_Name
                                    .toString() + " ?"
                            )
                            .setPositiveButton(
                                "Yes"
                            ) { dialog: DialogInterface?, which: Int ->

                                // code for delete item
                                Toast.makeText(
                                    context,
                                    "deleted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.setNegativeButton("cancel", null).create()
                    alertDialog.show()
                })

                holder.edit_item.setOnClickListener {
                    val update_intent = Intent(context, UpdateDrugsActivity::class.java)
                    update_intent.putExtra("list_position", position)
                    val args = Bundle()
                    args.putSerializable("ARRAYLIST", drugs as Serializable?)
                    update_intent.putExtra("BUNDLE", args)
                    context.startActivity(update_intent)
                }


            }

        }
    }

    override fun getItemCount(): Int {
        return drugs.size
    }

}
class DrugsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var drugImg: ImageView = view.findViewById(R.id.drug_img_id)
    var drugName: TextView = view.findViewById(R.id.drug_name_id)
    var drugPrice: TextView = view.findViewById(R.id.drug_price_id)
    var delete_item: ImageView = view.findViewById(R.id.delete_item)
    var edit_item: ImageView = view.findViewById(R.id.edit_item)
}