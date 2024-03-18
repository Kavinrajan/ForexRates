package com.kavin.forex.currencies.common

import android.content.SharedPreferences
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.kavin.forex.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Boolean.TRUE

fun Fragment.showConfirmationDialog(message: String, onConfirm: () -> Unit) {
    AlertDialog.Builder(requireContext())
        .setMessage(message)
        .setPositiveButton("Yes") { _, _ -> onConfirm.invoke() }
        .setNegativeButton("No", null)
        .show()
}

/*fun Fragment.showBadgeVisibility(showBadge: Boolean) {
    val navBottomView =
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation)
    navBottomView.updateCartBadgeVisibility(showBadge)
}*/
fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run { navigate(direction) }
}

fun Fragment.checkInternetConnection() {
    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
        val context = context ?: return@launch

        val isInternetAvailable = InternetPermissionManager(requireActivity()).isInternetAvailable(context)

        requireActivity().runOnUiThread {
            if (isInternetAvailable) {
                // Toast.makeText(context, "İnternet bağlantısı var", Toast.LENGTH_SHORT).show()
            } else {
                showConfirmationDialog(
                    getString(R.string.no_internet_connection_dialog),
                    onConfirm = {
                        if (isAdded) {
                            InternetPermissionManager(requireActivity()).enableInternet()
                        }
                    },
                )
            }
        }
    }
}

/*fun getUserIdFromSharedPref(sharedPrefs: SharedPreferences): String {
    return sharedPrefs.getString(
        Constants.PREF_FIREBASE_USERID_KEY,
        Constants.PREF_DEF_STR,
    ) ?: Constants.PREF_DEF_STR
}*/

fun isAppFirstLaunchPerf(sharedPrefs: SharedPreferences): Boolean {
    return sharedPrefs.getBoolean(
        Constants.PREF_IS_APP_FIRST_OPEN,
        TRUE,
    )
}