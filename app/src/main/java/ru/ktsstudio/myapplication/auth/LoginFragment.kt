package ru.ktsstudio.myapplication.auth

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import ru.ktsstudio.myapplication.R

class LoginFragment : Fragment() {

    private var constraintSet1 = ConstraintSet()
    private var constraintSet2 = ConstraintSet()
    private var constraintLayout: ConstraintLayout? = null
    private var state: Boolean = false

    private val authNavigator: AuthNavigator
        get() = parentFragment as AuthNavigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        constraintLayout = view as ConstraintLayout
        with(view) {
            findViewById<Button>(R.id.login).setOnClickListener { authNavigator.navigateToMain() }
            findViewById<Button>(R.id.register).setOnClickListener { authNavigator.navigateToRegistration() }
            findViewById<Button>(R.id.button11).setOnClickListener { changeState() }
        }

        constraintSet2.clone(context, R.layout.fragment_login2)
        constraintSet1.clone(constraintLayout)
    }

    private fun changeState() {
        TransitionManager.beginDelayedTransition(constraintLayout)
        if (state) {
            constraintSet1.applyTo(constraintLayout)
        } else {
            constraintSet2.applyTo(constraintLayout)
        }
        state = !state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        constraintLayout = null
    }
}