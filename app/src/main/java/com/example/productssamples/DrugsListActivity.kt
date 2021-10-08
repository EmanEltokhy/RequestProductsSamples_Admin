package com.example.productssamples

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Environment
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
import android.provider.Settings
import com.google.firebase.firestore.Query
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

private const val FILE_REQUEST_CODE = 5
private const val READ_REQUEST_CODE = 10
private const val MANAGE_REQUEST_CODE = 15

class DrugsListActivity : AppCompatActivity() {
    var DB: DbQuery? = null
    var drugs = mutableListOf<DrugsModel>()
    var progressBar: ProgressBar? = null
    var displaydrugs = mutableListOf<DrugsModel>()
    var filepath: String = ""
    private lateinit var recycler: RecyclerView
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
        recycler.layoutManager = GridLayoutManager(this, 2)
        //recycler.adapter = adapter
        findViewById<Button>(R.id.upload_excel).setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                var i = Intent()
                i.type = "text/*"
                i.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(Intent.createChooser(i, "Open CSV"), FILE_REQUEST_CODE)
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_REQUEST_CODE
                )
                var i = Intent()
            }

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
                    for (document in task.getResult()!!) {

                        tempDrug = DrugsModel(
                            Drug_Id = document.id, Drug_Name = document.getString("drug_Name")!!,
                            Drug_Price = document.getString("drug_Price")!!,
                            Drug_Image = document.getString("drug_Image")!!
                        )

                        drugs.add(tempDrug)
                    }
                    progressBar?.setVisibility(View.GONE)
                    val adapter = DrugListAdapter(drugs, this)
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
        if (requestCode == 5 && resultCode == Activity.RESULT_OK) {
            if (requestCode == FILE_REQUEST_CODE && resultCode == RESULT_OK) {
                data?.let {
                    var path = data.data.toString()
                    filepath = path.substring(path.lastIndexOf("%") + 3)
                    accessFIle()
                }
            }
        }
    }

    private fun accessFIle() {

        val sb = StringBuilder()
        var textfile = File(Environment.getExternalStorageDirectory(), filepath)
        var fis = FileInputStream(textfile)

        if (fis != null) {
            var isr = InputStreamReader(fis)
            var buffer = BufferedReader(isr)

            var line: String? = null
            while (buffer.readLine()?.also { line = it } != null) {
                sb.append(line + "\n")
            }
            fis.close()
        }
        Toast.makeText(this@DrugsListActivity, sb.toString(), Toast.LENGTH_SHORT).show()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode== READ_REQUEST_CODE && grantResults[0]==PackageManager.PERMISSION_GRANTED) {

            val i = Intent()
            i.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
            startActivityForResult(i,MANAGE_REQUEST_CODE)

        }else if(requestCode==MANAGE_REQUEST_CODE && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            accessFIle()
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.drugslist_menu, menu)
        menu?.findItem(R.id.search)?.let {
            var searchview = it.actionView as SearchView
            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {
                    if (text!!.isNotEmpty()) {
                        displaydrugs.clear()
                        var search = text.lowercase(Locale.getDefault())
                        for (i in 0 until drugs.size) {
                            if (drugs[i].Drug_Name.lowercase(Locale.getDefault())
                                    .contains(search)
                            ) {
                                displaydrugs.add(drugs[i])
                            }
                            recycler.adapter!!.notifyDataSetChanged()
                        }
                    } else {
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