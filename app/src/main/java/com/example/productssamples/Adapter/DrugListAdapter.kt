package com.example.productssamples.Adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productssamples.DbQuery
import com.example.productssamples.Model.DrugsModel
import com.example.productssamples.R

class DrugListAdapter (private var drugs: MutableList<DrugsModel> ,private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var DB  = DbQuery()







    /*companion object {
        //var adapter: DrugListAdapter? = null
        fun updateData(newDrugs: DrugsModel , postion : Int){

            drugs.set(postion , newDrugs)
            notifyDataSetChanged()
        //drugs.clear()
       // drugs.addAll(newDrugs)
       // notifyDataSetChanged()
    }


    }*/




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.drug_item, parent, false)
        return DrugsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is DrugsViewHolder -> {
                DB!!.Instance_Of_DbQuery(context)


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
                            .setTitle("Delete " + drugs[position].Drug_Name)
                            .setMessage(
                                "Do you want to delete " + drugs[position].Drug_Name + " ?"
                            )
                            .setPositiveButton(
                                "Yes"
                            ) { dialog: DialogInterface?, which: Int ->

                                // code for delete item
                                DB!!.deleteDrugItem(drugs.get(position))
                                drugs.removeAt(position)
                                notifyDataSetChanged()
                               // DrugListAdapter.adapter?.notifyDataSetChanged()
                                Toast.makeText(
                                    context,
                                    "deleted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.setNegativeButton("cancel", null).create()
                    alertDialog.show()
                })

                holder.edit_item.setOnClickListener {

                    val dialog = Dialog(context)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(false)
                    dialog.setContentView(R.layout.activity_update_drugs)
                    val ok = dialog.findViewById(R.id.ok_id) as TextView
                    val cancel = dialog.findViewById(R.id.cancel_id) as TextView
                    val drugName = dialog.findViewById(R.id.drug_name_ET) as EditText
                    val drugPrice = dialog.findViewById(R.id.drug_price_ET) as EditText
                    val drugImage = dialog.findViewById(R.id.drug_img_link_ET) as EditText
                    drugName.setText(drugs[position].Drug_Name)
                    drugPrice.setText(drugs[position].Drug_Price)
                    drugImage.setText(drugs[position].Drug_Image)

                    ok.setOnClickListener {
                        var TempDrugName = drugName.text.toString()
                        var TempDrugPrice = drugPrice.text.toString()
                        var TempDrugImage = drugImage.text.toString()

                        if (TempDrugName.contentEquals(""))
                        {
                            drugName.setError("Drug name is Required")
                        }

                        if (TempDrugPrice.contentEquals(""))
                        {
                            drugPrice.setError("Drug Price is Required")
                        }

                        if (TempDrugImage.contentEquals(""))
                        {
                            drugImage.setError("Drug Image is Required")
                        }

                        else if (!TempDrugName.contentEquals("") && !TempDrugPrice.contentEquals("") && !TempDrugImage.contentEquals(""))
                        {
                            var edttedModel = DrugsModel(Drug_Id = drugs[position].Drug_Id , Drug_Name = TempDrugName, Drug_Price = TempDrugPrice ,Drug_Image = TempDrugImage)
                            DB!!.updateDrug(edttedModel)
                            drugs.set(position,edttedModel)
                            Toast.makeText(context , "Edited Successfully" , Toast.LENGTH_LONG).show()
                            notifyDataSetChanged()
                            dialog.dismiss()

                            // update database


                        }


                    }
                    cancel.setOnClickListener { dialog.dismiss() }
                    dialog.show()











                    /*val update_intent = Intent(context, UpdateDrugsActivity::class.java)
                    update_intent.putExtra("list_position", position)
                    val args = Bundle()
                    args.putSerializable("ARRAYLIST", drugs as Serializable?)
                    update_intent.putExtra("BUNDLE", args)
                    context.startActivity(update_intent)*/
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