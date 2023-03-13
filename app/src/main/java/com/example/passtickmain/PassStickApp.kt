package com.example.passtickmain

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.passtickmain.R.string
import com.example.passtickmain.ui.navigation.PasstickNavHost

/*
Top level composable that represents screens for the application.
 */

@Composable
fun PassStickApp(navController: NavHostController = rememberNavController()){
    PassStickNavHost(navController = navController)
}


@Composable
fun PassStickTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
){
    if (canNavigateBack){
        TopAppBar(
            title = {Text(title)},
            modifier = modifier,
            navigationIcon = {
                IconButton(onClick = navigateUp){
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = stringResource(string.back_button)
                    )
                }
            }
        )
    } else {
        TopAppBar(title = {Text(title)}, modifier = modifier)
    }
}
