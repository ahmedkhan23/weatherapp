package com.ahmed.weatherapp.view

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ahmed.weatherapp.R
import com.ahmed.weatherapp.data.PermissionAction

const val TAG = "PermissionDialog"

@Composable
fun rememberIfPermissionGranted(context: Context, permission: String): MutableState<Boolean> {
    return remember { mutableStateOf(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)  }
}

fun shouldShowPermissionRationale(context: Context, permission: String): Boolean {
    val activity = context as Activity?
    if (activity == null)
        Log.d(TAG, "Activity is null")
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

    // check if the permission has already been granted and if so, invoke the permissionAction fun
    // accordingly and return as we have nothing more to do
    val checkIfPermissionGranted by rememberIfPermissionGranted(context, permission)

    if (checkIfPermissionGranted) {
        permissionAction(PermissionAction.PermissionGranted)
    }

    // create a Permissions launcher that will be used later based on logic flow of whether
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


    val showPermissionRationale = shouldShowPermissionRationale(context, permission)
    var isDialogDismissed by remember { mutableStateOf(false) }
    var notShownYet by remember { mutableStateOf(true) }

    if ((showPermissionRationale && !isDialogDismissed) ||
        (!isDialogDismissed && notShownYet)) {

        //dialog shows
        notShownYet = false

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
                permissionsLauncher.launch(permission)
            }
        }
    }





}