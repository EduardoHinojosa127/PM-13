package com.miempresa.serviciowebv4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PackageManagerCompat
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy =
            StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        btnLogear.setOnClickListener(){
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            var url = getString(R.string.urlAPI)+"/usuarios?"
            url = url + "usuario="+usuario+"&clave="+clave
            Log.e("gg", "${usuario} y ${clave}")
            val stringRequest = JsonArrayRequest(url,
                Response.Listener { response ->
                    try {
                        val valor = response.getJSONObject(0)
                        /*Toast.makeText(
                            applicationContext,
                            "Validación de usuario: "+valor.getString("usuario")+
                                    " con clave: "+valor.getString("clave")+" correcta",
                            Toast.LENGTH_LONG
                        ).show()*/
                        val llamarActividad = Intent(applicationContext, Opciones::class.java)
                        startActivity(llamarActividad)
                    }catch (e: JSONException){
                        Toast.makeText(applicationContext,
                        "Error en las credenciales proporcionadas",
                        Toast.LENGTH_LONG).show()
                    }
                },Response.ErrorListener {
                    Toast.makeText(applicationContext,
                        "Compruebe que tiene accesoa a internet",
                        Toast.LENGTH_LONG).show()
                }
            )
            queue.add(stringRequest)
        }
        btnRegistrar.setOnClickListener(){
            val usuario = txtUsuario.text.toString()
            val clave = txtClave.text.toString()
            val queue = Volley.newRequestQueue(this)
            var url = "http://192.168.0.105:3000/usuarios?"
            val jsonObject = JSONObject()
            jsonObject.put("usuario", usuario)
            jsonObject.put("clave", clave)
            jsonObject.put("estado", "A")
            val jsonObjectRequest = JsonObjectRequest(url, jsonObject,
                    Response.Listener{ response ->
                        Log.e("ee", "Response is: ${response}")
                        Toast.makeText(this, "Usuario registrado satisfactoriamente", Toast.LENGTH_LONG).show()
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                    }
                )
            queue.add(jsonObjectRequest)
        }
        btnSalir.setOnClickListener(){
            var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Mensaje de confirmación")
            alertDialog.setMessage("¿Desea salir de la aplicación?")

            alertDialog.setPositiveButton("Si"){ dialog, which ->
                finish()
            }
            alertDialog.setNegativeButton("No"){dialog, which ->

            }
            alertDialog.show()
        }
    }
}