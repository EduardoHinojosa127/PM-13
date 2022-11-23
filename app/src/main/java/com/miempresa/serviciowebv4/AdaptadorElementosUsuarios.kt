package com.miempresa.serviciowebv4

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class AdaptadorElementosUsuarios(val ListaElementos:ArrayList<ElementosUsuarios>): RecyclerView.Adapter<AdaptadorElementosUsuarios.ViewHolder>() {

    override fun getItemCount(): Int {
        return ListaElementos.size;
    }
    class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val fUsuarios = itemView.findViewById<TextView>(R.id.elemento_usuario)
        val fEstado = itemView.findViewById<TextView>(R.id.elemento_estado)

        //set the onclick listener for the singlt list item
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.fUsuarios?.text=ListaElementos[position].usuario
        holder?.fEstado?.text=ListaElementos[position].estado
        var id = ListaElementos[position].id
        var usuarios = ListaElementos[position].usuario
        var clave = ListaElementos[position].clave
        var estado = ListaElementos[position].estado

        holder.itemView.setOnClickListener(){
            val llamaractividad = Intent(holder.itemView.context, verUsuario::class.java)
            llamaractividad.putExtra("idUsuario", id.toString())
            llamaractividad.putExtra("usuario", usuarios.toString())
            llamaractividad.putExtra("clave", clave.toString())
            llamaractividad.putExtra("estado", estado.toString())
            holder.itemView.context.startActivity(llamaractividad)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoslistausuarios, parent, false);
        return ViewHolder(v);
    }

}
