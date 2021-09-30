package com.example.productssamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productssamples.Adapter.DrugListAdapter
import com.example.productssamples.Model.DrugsModel

class DrugsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_list)

        val drugs = mutableListOf<DrugsModel>().apply {
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


        }
        val adapter = DrugListAdapter(drugs , this)
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        //recycler.layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = GridLayoutManager(this,2)
        recycler.adapter = adapter
    }
}