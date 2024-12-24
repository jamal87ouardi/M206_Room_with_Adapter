package com.example.m206_room_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView

class SmartphoneAdapter(
    private val context: Context,
    private val smartphones: List<Smartphone>
) : ArrayAdapter<Smartphone>(context, 0, smartphones) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.list_item, parent, false
        )

        val smartphone = getItem(position)

        val imageView = view.findViewById<ImageView>(R.id.smartphone_image)
        val nameView = view.findViewById<TextView>(R.id.smartphone_name)
        val priceView = view.findViewById<TextView>(R.id.smartphone_price)

        smartphone?.let {
            // Charger l'image avec Glide
            Glide.with(context)
                .load(it.image) // URL de l'image
                .into(imageView)

            nameView.text = it.nom
            priceView.text = "${it.prix} MAD"
        }

        return view
    }
}
