package com.rbraithwaite.yourmovielog.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ModalNavigationDrawer
import com.rbraithwaite.yourmovielog.ui.main.MainNavDrawer
import com.rbraithwaite.yourmovielog.ui.main.MainNavHost
import com.rbraithwaite.yourmovielog.ui.main.rememberMainState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainState = rememberMainState()

            ModalNavigationDrawer(
                drawerState = mainState.drawerState,
                drawerContent = {
                    MainNavDrawer(mainState = mainState)
                }
            ) {
                MainNavHost(mainState = mainState)
            }
        }
    }
}
