package com.miempresa.serviciowebv4

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_ver_pelicula.*
import kotlinx.android.synthetic.main.activity_ver_usuario.*

class verUsuario : AppCompatActivity() {
    fun eliminarUsuario() {
        AsyncTask.execute {
            val id = txtIdUsuario.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios/" + id
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.DELETE, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro eliminado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al eliminar el registro",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ){}
            queue.add(postRequest)
        }
    }

    fun agregarUsuario() {
        AsyncTask.execute {

            val id = txtIdUsuario.text.toString()
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val estado = txtEstado.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios"
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro agregado correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al agregar los datos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["id"] = id
                    params["usuario"] = usuario
                    params["clave"] = clave
                    params["estado"] = estado
                    return params
                }
            }
            queue.add(postRequest)
        }
    }

    fun editarUsuario() {
        AsyncTask.execute {

            val id = txtIdUsuario.text.toString()
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val estado = txtEstado.text.toString()

            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI) + "/usuarios/"+id
            val postRequest: StringRequest = object : StringRequest(
                Request.Method.PUT, url,
                Response.Listener { response -> // response
                    Toast.makeText(
                        applicationContext,
                        "Registro actualizadp correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                },
                Response.ErrorListener { response ->// error
                    Toast.makeText(
                        applicationContext,
                        "Se produjo un error al actualizar los datos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> =
                        HashMap()
                    params["id"] = id
                    params["usuario"] = usuario
                    params["clave"] = clave
                    params["estado"] = estado
                    return params
                }
            }
            queue.add(postRequest)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_usuario)
        btnGuardarUsuario.setOnClickListener(){
            agregarUsuario()
        }
        btnEliminarUsuario.setOnClickListener(){
            eliminarUsuario()
        }
        val bundle: Bundle?= intent.extras
        if(bundle!=null){
            txtIdUsuario.setText(bundle.getString("idUsuario").toString())
            txtUsuario.setText(bundle.getString("usuario").toString())
            txtClave.setText(bundle.getString("clave").toString())
            txtEstado.setText(bundle.getString("estado").toString())
            btnGuardarUsuario.setEnabled(true)
            btnEliminarUsuario.setEnabled(true)
            btnGuardarUsuario.setOnClickListener(){
                editarUsuario()
            }
        }else{
            btnGuardarUsuario.setEnabled(true)
            btnEliminarUsuario.setEnabled(false)
        }
    }
}