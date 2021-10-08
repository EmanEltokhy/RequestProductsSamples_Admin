package com.example.productssamples

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.productssamples.Model.DrugsModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class DbQuery() {
    var firebaseFirestore: FirebaseFirestore? = null
    var context: Context? = null
    fun Instance_Of_DbQuery(con: Context?) {
        firebaseFirestore = FirebaseFirestore.getInstance()
        this.context = con
    }
    fun addDrug(drug: DrugsModel) {
        firebaseFirestore!!.collection("AllDrugs"!!).add(drug)
            .addOnSuccessListener { documentReference: DocumentReference ->
                Toast.makeText(
                    context,
                    "You add drug \"" + drug.Drug_Name.toString() + "\" successfully",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(
                    "addTag Success",
                    "DocumentSnapshot added with ID: " + documentReference.id
                )

            }.addOnFailureListener { e: Exception? ->
                Toast.makeText(
                    context,
                    """Failed to add drug "${drug.Drug_Name.toString()}" 
 Check your connection""",
                    Toast.LENGTH_LONG
                ).show()
                Log.w("addDrug Failed", "Error adding document", e)
            }
    }
    fun deleteDrugItem(drug: DrugsModel){
        firebaseFirestore!!.collection("AllDrugs")
            .document(drug.Drug_Id).delete()
    }
    fun updateDrug(drug: DrugsModel){
        val data: MutableMap<String, Any> = HashMap()
        data["drug_Name"] = drug.Drug_Name
        data["drug_Price"] = drug.Drug_Price
        data["drug_Image"] = drug.Drug_Image
        firebaseFirestore!!.collection("AllDrugs")
            .document(drug.Drug_Id)
            .update(data)
    }


}