package com.example.productssamples

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.productssamples.Model.DrugsModel

class UpdateDrugsActivity : AppCompatActivity() {
    /*var drugsList: List<DrugsModel>? = null
    var drugModel: DrugsModel? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_drugs)

        /*val browse_image = findViewById<TextView>(R.id.drug_browse_image_TV)
        val upload_image = findViewById<TextView>(R.id.image_upload_btn)
        val ok = findViewById<TextView>(R.id.ok_id)
        val cancel = findViewById<TextView>(R.id.cancel_id)
        var drugName = findViewById<EditText>(R.id.drug_name_ET)
        var drugPrice = findViewById<EditText>(R.id.drug_price_ET)
        var drugImage = findViewById<EditText>(R.id.drug_img_link_ET)
        val get_intent = intent
        val index = get_intent.getIntExtra("list_position", -1)
        val args = get_intent.getBundleExtra("BUNDLE")
        drugsList = args?.getSerializable("ARRAYLIST") as List<DrugsModel>
        drugModel = drugsList?.get(index)





        drugName.setText(drugModel?.Drug_Name.toString())
        drugPrice.setText(drugModel?.Drug_Price.toString())
        drugImage.setText(drugModel?.Drug_Image.toString())

        cancel.setOnClickListener { view: View? -> finish() }
        ok.setOnClickListener { view: View? ->
            // CODE FOR EDIT ITEM HERE
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
                var edttedModel = DrugsModel(Drug_Name = TempDrugName, Drug_Price = TempDrugPrice ,Drug_Image = TempDrugImage)
                // update database

                //drugsList.add(index, edttedModel)

                Toast.makeText(this , "Edited Successfully" , Toast.LENGTH_LONG).show()
                finish()

            }


        }
*/
    }
}