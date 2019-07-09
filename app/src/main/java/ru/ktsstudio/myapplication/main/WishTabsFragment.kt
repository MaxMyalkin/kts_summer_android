package ru.ktsstudio.myapplication.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.ktsstudio.myapplication.R
import ru.ktsstudio.myapplication.app.ActivityNavigator

class WishTabsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wish_tabs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.logout).setOnClickListener {
            requireActivity().let { it as ActivityNavigator }.navigateToLoginScreen()
        }

        view.findViewById<Button>(R.id.checkPermissions).setOnClickListener {
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

    companion object {
        private const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 432
    }
}