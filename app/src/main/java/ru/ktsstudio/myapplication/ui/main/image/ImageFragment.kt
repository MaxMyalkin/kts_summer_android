package ru.ktsstudio.myapplication.ui.main.image

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_image.*
import leakcanary.LeakSentry
import retrofit2.Call
import ru.ktsstudio.myapplication.R
import ru.ktsstudio.myapplication.data.models.GithubUser
import ru.ktsstudio.myapplication.data.models.SearchWrapper
import ru.ktsstudio.myapplication.data.network.GithubApiService
import ru.ktsstudio.myapplication.di.DI
import toothpick.Toothpick
import javax.inject.Inject

class ImageFragment : Fragment() {

    @Inject
    lateinit var api: GithubApiService

    private var currentCall: Call<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scope = Toothpick.openScope(DI.APP)
        Toothpick.inject(this, scope)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initListeners()
        handleList(emptyList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LeakSentry.refWatcher.watch(adapter!!)
//        adapter = null
    }

    private fun initList() {
        adapter = ImageAdapter()
        with(list) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = ImageFragment.adapter
        }
    }

    private fun initListeners() {
        searchButton.setOnClickListener {
            val searchQuery = searchText.text.toString()

            progressBar.isVisible = true
            emptyText.isVisible = false

            api.searchUsers(searchQuery)
                .apply { currentCall = this }
                .enqueue(object : retrofit2.Callback<SearchWrapper<GithubUser>> {
                    override fun onFailure(call: retrofit2.Call<SearchWrapper<GithubUser>>, t: Throwable) {
                        progressBar.isVisible = false
                        Log.e("Retrofit", "response fail", t)
                        handleList(emptyList())
                    }

                    override fun onResponse(
                        call: retrofit2.Call<SearchWrapper<GithubUser>>,
                        response: retrofit2.Response<SearchWrapper<GithubUser>>
                    ) {
                        progressBar.isVisible = false
                        val list = if (!response.isSuccessful) emptyList() else response.body()?.items ?: emptyList()
                        handleList(list)
                    }
                })

        }
    }

    private fun handleList(users: List<GithubUser>) {
        val avatars = users.mapNotNull { it.avatarUrl }
        emptyText.isVisible = avatars.isEmpty()
        adapter?.submitList(avatars)
    }

    override fun onPause() {
        super.onPause()
        currentCall?.cancel()
        currentCall = null
    }

    companion object {
        //Leak canary check
        @JvmStatic
        private var adapter: ImageAdapter? = null
    }
}