package ru.ktsstudio.myapplication.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_wish_tabs.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import ru.ktsstudio.myapplication.R
import ru.ktsstudio.myapplication.data.models.GithubUser
import ru.ktsstudio.myapplication.data.stores.GsonStore
import ru.ktsstudio.myapplication.data.stores.OkHttp
import ru.ktsstudio.myapplication.data.stores.RetrofitStore
import ru.ktsstudio.myapplication.ui.app.ActivityNavigator
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class WishTabsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wish_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout.setOnClickListener {
            requireActivity().let { it as ActivityNavigator }.navigateToLoginScreen()
        }
        images.setOnClickListener { parentFragment.let { it as MainNavigator }.navigateToImages() }
        checkNetworkHttpUrlConnection.setOnClickListener { checkHttpUrlConnection() }
        checkOkhttp.setOnClickListener { checkOkHttp() }
        checkRetrofit.setOnClickListener { checkRetrofit() }

        checkPermissions.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                    // Показать диалог для объяснения, почему вам нужен этот permission
                    AlertDialog.Builder(requireContext())
                        .setMessage(R.string.permission_rationale)
                        .setPositiveButton(android.R.string.ok) { dialog, _ ->
                            requestContactPermission()
                            dialog.dismiss()
                        }
                        .setNegativeButton(android.R.string.no) { dialog, _ -> dialog.dismiss() }
                        .show()
                } else {
                    requestContactPermission()
                }
            } else {
                Toast.makeText(requireContext(), "Contact permission granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestContactPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_CONTACTS),
            MY_PERMISSIONS_REQUEST_READ_CONTACTS
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(requireContext(), "Contact permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Contact permission denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun checkHttpUrlConnection() {
        Thread {
            val url = URL("https://api.github.com/users/MaxMyalkin")
            var connection: HttpURLConnection? = null
            try {
                connection = url.openConnection()
                    .let { it as HttpURLConnection }
                    .apply {
                        readTimeout = 10_000
                        connectTimeout = 10_000
                        requestMethod = "GET"
                        useCaches = false
                        doInput = true
                        doOutput = true
                        instanceFollowRedirects = true
                        setRequestProperty("Authorization", "myToken")
                        connect()
                    }

                val dataToSend = "test string"

                BufferedOutputStream(connection.outputStream).use {
                    it.write(dataToSend.toByteArray())
                    it.flush()
                }

                val responseCode = connection.responseCode
                connection.headerFields.entries.forEach { (key, values) ->
                    values.forEach { value ->
                        Log.d("HttpUrlConnection", "KEY: $key| VALUE: $value")
                    }
                }

                val inputStream = if (responseCode < 400) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }

                val response = inputStream.use {
                    val reader = BufferedReader(InputStreamReader(it, Charset.forName("UTF-8")))
                    val sb = StringBuilder()
                    while (true) {
                        val line = reader.readLine() ?: break
                        sb.append(line)
                    }

                    sb.toString()
                }
                Log.d("HttpUrlConnection", "response = $response")
            } catch (e: Exception) {
                Log.e("HttpUrlConnection", "error", e)
            } finally {
                try {
                    connection?.disconnect()
                } catch (e: Exception) {
                    Log.e("HttpUrlConnection", "error", e)
                }
            }
        }.start()
    }

    private fun checkOkHttp() {
        val request = Request.Builder()
            .url("https://api.github.com/users")
            .get()
            .build()

        OkHttp.instance.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("OkHttp", "response fail", e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) throw IOException("Unexpected code ${response.code()}")

                    val responseString = response.body()?.string().orEmpty()

                    val type = object : TypeToken<List<GithubUser>>() {}.type
                    val users: List<GithubUser> = GsonStore.instance.fromJson(responseString, type)
                }
            })
    }

    private fun checkRetrofit() {
        RetrofitStore.service.getUsers()
            .enqueue(object : retrofit2.Callback<List<GithubUser>> {
                override fun onFailure(call: retrofit2.Call<List<GithubUser>>, t: Throwable) {
                    Log.e("Retrofit", "response fail", t)
                }

                override fun onResponse(
                    call: retrofit2.Call<List<GithubUser>>,
                    response: retrofit2.Response<List<GithubUser>>
                ) {
                    if (!response.isSuccessful) throw IOException("Unexpected code ${response.code()}")
                    val users = response.body()
                }
            })
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 432
    }
}