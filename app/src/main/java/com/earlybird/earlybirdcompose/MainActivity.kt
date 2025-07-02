package com.earlybird.earlybirdcompose

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.earlybird.earlybirdcompose.presentation.screen.main.MainScreen
import com.earlybird.earlybirdcompose.presentation.screen.timer.OverlayService
import com.earlybird.earlybirdcompose.ui.theme.EarlyBirdComposeTheme
import kotlinx.coroutines.MainScope

const val REQUEST_CODE_OVERLAY_PERMISSION = 1001

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startIntent = intent

        if (!checkOverlayPermission(this)) {
            requestOverlayPermission(this)
        }

        setContent {
            EarlyBirdComposeTheme {
                AppNavigation(startIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            // 오버레이 권한이 허용되지 않은 상태일 때 서비스 실행
            Toast.makeText(this, "권한을 허용해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}

fun checkOverlayPermission(activity: Activity): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Settings.canDrawOverlays(activity)
    } else {
        true
    }
}
fun requestOverlayPermission(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${activity.packageName}")
        )
        activity.startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EarlyBirdComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen()
        }
    }
}