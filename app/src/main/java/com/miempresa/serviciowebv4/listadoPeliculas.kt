package com.miempresa.serviciowebv4

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_peliculas.*
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class listadoPeliculas : AppCompatActivity() {
    var llenarLista = ArrayList<Elementos>()
    fun cargarLista() {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)
        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = getString(R.string.urlAPI) + "/peliculas"
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            val duracion =
                                response.getJSONObject(i).getString("duracion")
                            val imagen =
                                response.getJSONObject(i).getString("imagen")
                            val categoria =
                                response.getJSONObject(i).getString("categoria")
                            llenarLista.add(Elementos(id.toInt(),imagen,nombre, duracion.toInt(), categoria))
                        }
                        val adapter = AdaptadorElementos(llenarLista)
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
    fun cargarLista2(direccion:String) {
        lista.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        lista.layoutManager = LinearLayoutManager(this)

        AsyncTask.execute {
            val queue = Volley.newRequestQueue(applicationContext)
            val url = direccion
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        for (i in 0 until response.length()) {
                            val id =
                                response.getJSONObject(i).getString("id")
                            val nombre =
                                response.getJSONObject(i).getString("nombre")
                            val duracion =
                                response.getJSONObject(i).getString("duracion")
                            val imagen =
                                response.getJSONObject(i).getString("imagen")
                            val categoria =
                                response.getJSONObject(i).getString("categoria")
                            llenarLista.add(Elementos(id.toInt(),imagen,nombre, duracion.toInt(), categoria))
                        }
                        val adapter = AdaptadorElementos(llenarLista)
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
        setContentView(R.layout.activity_listado_peliculas)
        cargarLista()
        btnAgregar.setOnClickListener(){
            val llamarActividad = Intent(applicationContext, verPelicula::class.java)
            startActivity(llamarActividad)

        }
        btnSalirPelicula.setOnClickListener(){
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
        txtBuscar.addTextChangedListener{ user ->
            llenarLista.clear()
            cargarLista2(getString(R.string.urlAPI)+"/peliculas?nombre_like="+user)
        }
        btnBuscar.setOnClickListener(){
            val cat = txtBuscar.text.toString()
            llenarLista.clear()
            cargarLista2(getString(R.string.urlAPI)+"/peliculas?categoria_like="+cat)
        }

    }
}