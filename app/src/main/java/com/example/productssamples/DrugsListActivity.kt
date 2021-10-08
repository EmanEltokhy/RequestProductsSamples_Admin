package com.example.productssamples

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productssamples.Adapter.DrugListAdapter
import com.example.productssamples.Model.DrugsModel
import com.google.firebase.firestore.Query
import java.util.*

class DrugsListActivity : AppCompatActivity() {
    var DB: DbQuery? = null
    var drugs =  mutableListOf<DrugsModel>()
    var progressBar: ProgressBar? = null
    var displaydrugs =  mutableListOf<DrugsModel>()
    var path:String="h"
    private lateinit var recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_list)
        progressBar = findViewById(R.id.progressbar)
        DB = DbQuery()
        DB!!.Instance_Of_DbQuery(this@DrugsListActivity)
        //var temp =  DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png")
       // DB!!.addDrug(temp)
       // Toast.makeText(this@DrugsListActivity, "data added seccessfully", Toast.LENGTH_SHORT).show()

        /* drugs = mutableListOf<DrugsModel>().apply {
            add(DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png"))
            add(DrugsModel(Drug_Name = "Panadol", Drug_Price = "EGP 12",Drug_Image = "http://egyptiandrugstore.com/image/cache/data/manar5/panadol-500x500.png"))
            add(DrugsModel(Drug_Name = "Panadol Red", Drug_Price = "EGP 26",Drug_Image = "http://egyptiandrugstore.com/image/cache/data/manar5/panadol%20extra-500x500.png"))
            add(DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png"))
            add(DrugsModel(Drug_Name = "Panadol", Drug_Price = "EGP 12",Drug_Image = "http://egyptiandrugstore.com/image/cache/data/manar5/panadol-500x500.png"))
            add(DrugsModel(Drug_Name = "Panadol Red", Drug_Price = "EGP 26",Drug_Image = "http://egyptiandrugstore.com/image/cache/data/manar5/panadol%20extra-500x500.png"))
            add(DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png"))
            add(DrugsModel(Drug_Name = "Panadol", Drug_Price = "EGP 12",Drug_Image = "http://egyptiandrugstore.com/image/cache/data/manar5/panadol-500x500.png"))
            add(DrugsModel(Drug_Name = "Panadol Red", Drug_Price = "EGP 26",Drug_Image = "http://egyptiandrugstore.com/image/cache/data/manar5/panadol%20extra-500x500.png"))
            add(DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png"))
            add(DrugsModel(Drug_Name = "Panadol", Drug_Price = "EGP 12",Drug_Image = "http://egyptiandrugstore.com/image/cache/data/manar5/panadol-500x500.png"))
            add(DrugsModel(Drug_Name = "Panadol Red", Drug_Price = "EGP 26",Drug_Image = "http://egyptiandrugstore.com/image/cache/data/manar5/panadol%20extra-500x500.png"))
        }*/
        //displaydrugs.addAll(drugs)
       // val adapter = DrugListAdapter(displaydrugs , this)
        loadDrugs()
        recycler = findViewById<RecyclerView>(R.id.recycler)
        //recycler.layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = GridLayoutManager(this,2)
        //recycler.adapter = adapter
        findViewById<Button>(R.id.upload_excel).setOnClickListener {
            var i = Intent()
            i.type = "text/*"
            i.setAction(Intent.ACTION_GET_CONTENT)
            /*var i: Intent = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "text/csv"*/
            startActivityForResult(Intent.createChooser(i,"Open CSV"),5)

/*            var file:File = File(path)
            val lines:List<List<String>> = csvReader().readAll(file)
            Toast.makeText(this@DrugsListActivity, lines.toString(), Toast.LENGTH_SHORT).show()*/


        }
    }
    fun loadDrugs() {
        drugs = mutableListOf<DrugsModel>()
        drugs.clear()
        DB!!.firebaseFirestore!!.collection("AllDrugs"!!)
            .get()
            .addOnCompleteListener { task ->
                var tempDrug: DrugsModel
                if (task.isSuccessful) {
                    for ( document in task.getResult()!!) {

                        tempDrug =  DrugsModel(Drug_Id = document.id , Drug_Name = document.getString("drug_Name")!!,
                            Drug_Price = document.getString("drug_Price")!!,
                            Drug_Image = document.getString("drug_Image")!!
                        )

                        drugs.add(tempDrug)
                    }
                    progressBar?.setVisibility(View.GONE)
                    val adapter = DrugListAdapter(drugs , this)
                    recycler.adapter = adapter

                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    applicationContext,
                    "Failed to load your Drugs \n Check your connection",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==5 && resultCode == Activity.RESULT_OK)
        {
            data?.let {
                path = data.data.toString()
                Toast.makeText(this@DrugsListActivity, path, Toast.LENGTH_LONG).show()

            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drugslist_menu,menu)
        menu?.findItem(R.id.search)?.let {
            var searchview = it.actionView as SearchView
            searchview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    if(text!!.isNotEmpty()){
                        displaydrugs.clear()
                        var search= text.lowercase(Locale.getDefault())
                        for(i in 0 until drugs.size)
                        {
                            if(drugs[i].Drug_Name.lowercase(Locale.getDefault()).contains(search))
                            {
                                displaydrugs.add(drugs[i])
                            }
                            recycler.adapter!!.notifyDataSetChanged()
                        }
                    }
                    else
                    {
                        displaydrugs.clear()
                        displaydrugs.addAll(drugs)
                        recycler.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }


        return super.onCreateOptionsMenu(menu)
    }

}