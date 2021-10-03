package com.example.productssamples

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productssamples.Adapter.DrugListAdapter
import com.example.productssamples.Model.DrugsModel
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.*
import java.util.*
import java.util.jar.Manifest
private const val FILE_REQUEST_CODE=5
private const val READ_REQUEST_CODE=10
private const val MANAGE_REQUEST_CODE=15
class DrugsListActivity : AppCompatActivity() {
    var drugs =  mutableListOf<DrugsModel>()
    var displaydrugs =  mutableListOf<DrugsModel>()
    var filepath:String=""
    private lateinit var recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_list)

         drugs = mutableListOf<DrugsModel>().apply {
            add(DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png"))
            add(DrugsModel(Drug_Name = "Panadol", Drug_Price = "EGP 12",Drug_Image = "https://i2.wp.com/wikivera.com/wp-content/uploads/2021/03/MGK5158-GSK-Panadol-Tablets-455x455-1.png"))
            add(DrugsModel(Drug_Name = "Panadol Red", Drug_Price = "EGP 26",Drug_Image = "https://i-cf65ch.gskstatic.com/content/dam/global/panadol/ar_ae/allproducts/300x300/4.1.A.1_300x300_Panadol%20Extra.png?auto=format"))
            add(DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png"))
            add(DrugsModel(Drug_Name = "Panadol", Drug_Price = "EGP 12",Drug_Image = "https://i2.wp.com/wikivera.com/wp-content/uploads/2021/03/MGK5158-GSK-Panadol-Tablets-455x455-1.png"))
            add(DrugsModel(Drug_Name = "Panadol Red", Drug_Price = "EGP 26",Drug_Image = "https://i-cf65ch.gskstatic.com/content/dam/global/panadol/ar_ae/allproducts/300x300/4.1.A.1_300x300_Panadol%20Extra.png?auto=format"))
            add(DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png"))
            add(DrugsModel(Drug_Name = "Panadol", Drug_Price = "EGP 12",Drug_Image = "https://i2.wp.com/wikivera.com/wp-content/uploads/2021/03/MGK5158-GSK-Panadol-Tablets-455x455-1.png"))
            add(DrugsModel(Drug_Name = "Panadol Red", Drug_Price = "EGP 26",Drug_Image = "https://i-cf65ch.gskstatic.com/content/dam/global/panadol/ar_ae/allproducts/300x300/4.1.A.1_300x300_Panadol%20Extra.png?auto=format"))
            add(DrugsModel(Drug_Name = "Royal Vit", Drug_Price = "EGP 30",Drug_Image = "https://www.sedico.net/english/products/webpages/RoyalVitG/RoyalVitG.png"))
            add(DrugsModel(Drug_Name = "Panadol", Drug_Price = "EGP 12",Drug_Image = "https://i2.wp.com/wikivera.com/wp-content/uploads/2021/03/MGK5158-GSK-Panadol-Tablets-455x455-1.png"))
            add(DrugsModel(Drug_Name = "Panadol Red", Drug_Price = "EGP 26",Drug_Image = "https://i-cf65ch.gskstatic.com/content/dam/global/panadol/ar_ae/allproducts/300x300/4.1.A.1_300x300_Panadol%20Extra.png?auto=format"))
        }
        displaydrugs.addAll(drugs)
        val adapter = DrugListAdapter(displaydrugs , this)
        recycler = findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = GridLayoutManager(this,2)
        recycler.adapter = adapter
        findViewById<Button>(R.id.upload_excel).setOnClickListener {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            var i = Intent()
            i.type = "text/*"
            i.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(i,"Open CSV"), FILE_REQUEST_CODE)
            } else {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),READ_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== FILE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            data?.let {
                var path = data.data.toString()
                filepath = path.substring(path.lastIndexOf("%")+3)
                accessFIle()
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