package ru.ktsstudio.myapplication.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.ktsstudio.myapplication.R
import ru.ktsstudio.myapplication.app.ActivityNavigator

class AuthFragmentContainer : Fragment(), AuthNavigator {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            navigateToLogin()
        }
    }

    override fun navigateToRegistration() {
        childFragmentManager.beginTransaction()
            .replace(R.id.content, RegistrationFragment())
            .commitNow()
    }

    override fun navigateToLogin() {
        childFragmentManager.beginTransaction()
            .replace(R.id.content, LoginFragment())
            .commitNow()
    }

    override fun navigateToMain() {
        requireActivity().let { it as? ActivityNavigator }?.navigateToMainScreen()
    }
}