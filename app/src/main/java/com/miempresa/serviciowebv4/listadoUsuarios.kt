package com.miempresa.serviciowebv4

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_peliculas.*
import kotlinx.android.synthetic.main.activity_listado_peliculas.lista
import kotlinx.android.synthetic.main.activity_listado_usuarios.*
import org.json.JSONException

class listadoUsuarios : AppCompatActivity() {
    fun cargarLista() {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)
        var llenarLista = ArrayList<ElementosUsuarios>()
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/usuarios"
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val usuario =
                                response.getJSONObject(i).getString("usuario")
                            val clave =
                                response.getJSONObject(i).getString("clave")
                            val estado =
                                response.getJSONObject(i).getString("estado")
                            llenarLista.add(ElementosUsuarios(id.toInt(),usuario,clave, estado))
                        }
                        val adapter = AdaptadorElementosUsuarios(llenarLista)
                        lista.adapter = adapter
                    } catch (e: JSONException) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener los datos",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(
                        applicationContext,
                        "Verifique que esta conectado a internet",
                        Toast.LENGTH_LONG
                    ).show()
                })
            queue.add(stringRequest)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado_usuarios)
        cargarLista()
        btnAgregarUsuario.setOnClickListener(){
            val llamarActividad = Intent(applicationContext, verUsuario::class.java)
            startActivity(llamarActividad)

        }
        btnSalirUsuario.setOnClickListener(){
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Mensaje de confirmación")
            alertDialog.setMessage("¿Desea regresar a la vista de opciones?")

            alertDialog.setPositiveButton("Si"){ dialog, which ->
                finish()
            }
            alertDialog.setNegativeButton("No"){dialog, which ->

            }
            alertDialog.show()
        }
    }
}