package com.ahmed.weatherapp.view

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import com.ahmed.weatherapp.R
import com.ahmed.weatherapp.TAG
import com.ahmed.weatherapp.data.PermissionAction

fun shouldShowPermissionRationale(context: Context, permission: String): Boolean {
    val activity = context as Activity?
    return ActivityCompat.shouldShowRequestPermissionRationale(
        activity!!,
        permission
    )
}

@Composable
fun PermissionDialog(
    permission: String,
    permissionAction: (PermissionAction) -> Unit
) {

    val context = LocalContext.current

    // create a Permissions launcher that *will be used later* based on logic flow of whether
    // or not we need to request permissions - result is captured with permissionAction
    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            permissionAction(PermissionAction.PermissionGranted)
        } else {
            permissionAction(PermissionAction.PermissionDenied)
        }
    }

    Log.d(TAG, " just putting this out there to see for recomposition - log msg 1")

    val showPermissionRationale = shouldShowPermissionRationale(context, permission)
    var isDialogDismissed by remember { mutableStateOf(false) }
    var notShownYet by remember { mutableStateOf(true) }

    if ((showPermissionRationale && !isDialogDismissed) ||
        (!isDialogDismissed && notShownYet)) {

        notShownYet = false

        Log.d(TAG, " just putting this out there to see for recomposition - log msg 2")

        AlertDialog(
            onDismissRequest = {
                isDialogDismissed = true
                permissionAction(PermissionAction.PermissionDenied)
            },
            title = { Text(text = stringResource(R.string.permission_required)) },
            text = { Text(text = stringResource(R.string.location_permission_rationale)) },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        Log.d(TAG, " launching permissions - 1")
                        permissionsLauncher.launch(permission)
                    }
                ) {
                    Text(text = stringResource(R.string.grant_access))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isDialogDismissed = true
                        permissionAction(PermissionAction.PermissionDenied)
                    }
                ) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        )
    }
    else {
        if (!isDialogDismissed) {
            SideEffect {
                Log.d(TAG, " launching permissions - 2")
                permissionsLauncher.launch(permission)
            }
        }
    }





}