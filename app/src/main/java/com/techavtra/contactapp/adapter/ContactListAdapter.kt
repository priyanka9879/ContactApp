package com.techavtra.contactapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techavtra.contactapp.R
import com.techavtra.contactapp.model.ContactClass

class ContactListAdapter(
    val listofContact: ArrayList<ContactClass>?,

) : RecyclerView.Adapter<ContactListAdapter.MyViewHolderDr>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderDr {

        val ItemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_row, parent, false)
        return MyViewHolderDr(ItemView)
    }

    override fun getItemCount(): Int {
        return listofContact!!.size
    }


    class MyViewHolderDr(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtNumber: TextView=itemView.findViewById(R.id.txtNumber)



    }

    override fun onBindViewHolder(holder: MyViewHolderDr, position: Int) {

        holder.txtName.text= listofContact?.get(position)?.name ?: ""
        holder.txtNumber.text=listofContact?.get(position)?.number ?: ""
    }


}