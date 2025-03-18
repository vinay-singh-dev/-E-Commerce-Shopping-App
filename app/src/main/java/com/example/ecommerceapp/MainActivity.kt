package com.example.ecommerceapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var productList: MutableList<Product>
    private lateinit var adapter: ProductAdapter
    private lateinit var rvProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        val btnUpload = findViewById<FloatingActionButton>(R.id.btnUpload)
        btnUpload.setOnClickListener {
            startActivity(Intent(this, UploadActivity::class.java))
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvProducts = findViewById(R.id.rvProducts)
        rvProducts.layoutManager = LinearLayoutManager(this)

        productList = mutableListOf()
        adapter = ProductAdapter(productList)
        rvProducts.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("products")
        loadProducts()
    }

    private fun loadProducts() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (productSnapshot in snapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    product?.let { productList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error loading products!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
