package ru.ktsstudio.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.ktsstudio.myapplication.R
import ru.ktsstudio.myapplication.ui.main.image.ImageFragment

class MainFragmentContainer : Fragment(), MainNavigator {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            navigateToWithTabs()
        }
    }

    override fun navigateToWithTabs() = replaceFragment(WishTabsFragment())

    override fun navigateToImages() = replaceFragment(ImageFragment())

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commitNow()
    }
}