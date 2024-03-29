package com.mariomanhique.dokkhota

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.mariomanhique.dokkhota.ui.theme.DokkhotaApp
import com.mariomanhique.dokkhota.ui.theme.DokkhotaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
            enableEdgeToEdge(
//                statusBarStyle = SystemBarStyle.light(
//                    Color.TRANSPARENT,
//                    Color.TRANSPARENT,
//                ),
//                navigationBarStyle = SystemBarStyle.auto(
//                    lightScrim,
//                    darkScrim,
//                ) ,
            )
        super.onCreate(savedInstanceState)

//        FirebaseAuth.getInstance().signOut()
//        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {
            DokkhotaTheme {
                // A surface container using the 'background' color from the theme

                DokkhotaApp(windowSizeClass = calculateWindowSizeClass(this))
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DokkhotaTheme {
        Greeting("Android")
    }
}

private val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)
