package com.project.chatr.screens.habitAdd

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.project.chatr.screens.components.AppTopBar
import com.project.chatr.screens.components.NavigationActions
import com.project.chatr.ui.theme.CHaTrTheme

class HabitAddActivity : ComponentActivity(){
//    private val favoritesViewModel by viewModels<FavoritesViewModel> {
//        viewModelInit {
//            FavoritesViewModel(RoomGameService((application as DependencyContainer).database))
//        }
//    }

    companion object {
        fun navigate(ctx: ComponentActivity) {
            val intent = Intent(ctx, HabitAddActivity::class.java)

            ctx.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CHaTrTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppTopBar(
                            navActions = NavigationActions(
                                onBackAction = { finish() },
                            )
                        )
                    }
                ) { innerPadding ->
                    HabitAddScreen(
                        modifier = Modifier.padding(innerPadding),
//                        viewModel = favoritesViewModel,
                    )
                }
            }
        }
    }
}