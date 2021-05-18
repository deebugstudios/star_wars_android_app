package org.deebug.starwarscharacters.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.deebug.starwarscharacters.R
import org.deebug.starwarscharacters.models.SWCharacter

/**
 * Author: John N. Mote
 * Date Created: 17/05/2021
 * */

class SWCharacterListAdapter(var context: Context): RecyclerView.Adapter<SWCharacterListAdapter.SWCharacterListViewHolder>() {

    // declare a mutable list which will hold all the SWCharacters objects
    private var listOfSWCharacters = mutableListOf<SWCharacter>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SWCharacterListViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context);
        val inflatedView: View = inflater.inflate(R.layout.recycler_view_item_row, parent, false)
        return SWCharacterListViewHolder(inflatedView, context)
    }

    override fun getItemCount(): Int {
        return listOfSWCharacters.size
    }

    override fun onBindViewHolder(holder: SWCharacterListViewHolder, position: Int) {
        holder.bindView(listOfSWCharacters[position])

        holder.itemView.setOnClickListener {
            println("Tapped")
            val builder = AlertDialog.Builder(context)
            val dialogView: View = LayoutInflater.from(holder.itemView.context).inflate(R.layout.custom_dialog_view, null)

            val tvName : TextView = dialogView.findViewById(R.id.txt_dialog_name)
            val tvGender : TextView = dialogView.findViewById(R.id.txt_dialog_gender)
            val tvHeight : TextView = dialogView.findViewById(R.id.txt_dialog_height)

            tvName.text = context.getString(R.string.name) + " " + listOfSWCharacters[position].name
            tvGender.text = context.getString(R.string.gender) + " " + listOfSWCharacters[position].gender
            tvHeight.text = context.getString(R.string.height) + " " + listOfSWCharacters[position].height


            val button : Button = dialogView.findViewById(R.id.btnDone)
            builder.setView(dialogView)
            val alertDialog = builder.create()
            button.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }

    //This function is initially used to populate the list of SWCharacters when it is called
    //From the main activity
    fun setSWCharacterList(listOfSWCharacters: List<SWCharacter>){
        this.listOfSWCharacters = listOfSWCharacters.toMutableList()
        notifyDataSetChanged()
    }

    fun addMoreData(listOfSWCharacters: List<SWCharacter>){
        this.listOfSWCharacters.addAll(listOfSWCharacters)
        notifyDataSetChanged()
    }


    class SWCharacterListViewHolder(itemView: View, var context: Context): RecyclerView.ViewHolder(itemView) {
        fun bindView(swCharacter: SWCharacter){
            val textView: TextView = itemView.findViewById(R.id.txt_character_name)
            textView.text = swCharacter.name
            
        }
    }


}