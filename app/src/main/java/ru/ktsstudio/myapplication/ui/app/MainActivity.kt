package ru.ktsstudio.myapplication.ui.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.ktsstudio.myapplication.ui.auth.AuthFragmentContainer
import ru.ktsstudio.myapplication.ui.main.MainFragmentContainer
import ru.ktsstudio.myapplication.R

class MainActivity : AppCompatActivity(), ActivityNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateToLoginScreen()
        }

    }

    override fun navigateToMainScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, MainFragmentContainer())
            .commitNow()
    }

    override fun navigateToLoginScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, AuthFragmentContainer())
            .commitNow()
    }
}
