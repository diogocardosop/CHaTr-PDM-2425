package com.project.chatr.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.project.chatr.R


class NavigationActions(
    val onBackAction: (() -> Unit)? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: Int? = null,
    navActions: NavigationActions = NavigationActions()
) {
    TopAppBar(
        title = { Text(stringResource(title ?: R.string.app_name)) },
        navigationIcon = {
            ConditionalIconButton(
                Icons.AutoMirrored.Filled.ArrowBack,
                navActions.onBackAction,
            )
        }
    )
}

@Composable
fun ConditionalIconButton(
    icon: ImageVector,
    action: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    if (action != null) {
        IconButton(onClick = action, modifier = modifier) {
            Icon(imageVector = icon, contentDescription = "")
        }
    }
}