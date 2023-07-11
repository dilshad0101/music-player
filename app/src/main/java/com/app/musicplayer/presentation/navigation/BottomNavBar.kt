package com.app.musicplayer.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    navController: NavController
){
    val destinations = listOf<NavigationRoute>(
        NavigationRoute.HomeScreen,
        NavigationRoute.SearchScreen,
        NavigationRoute.PlaylistScreen,
        NavigationRoute.SettingsScreen
    )


    var selectedScreen by remember{ mutableStateOf(navController.currentBackStackEntry?.destination?.route) }
    NavigationBar(
        contentColor = Color.White,
        containerColor = Color.Transparent,
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .height(80.dp)
            .background(
                brush = Brush.verticalGradient(
                    0f to Color.Transparent,
                    Float.POSITIVE_INFINITY to Color(0xFC000000)
                )
            )

    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            destinations.forEachIndexed { index, screen ->
                NavigationBarItem(
                    selected = selectedScreen == screen.route,
                    onClick = { selectedScreen = screen.route;
                       navController.navigate(screen.route)
                    },
                    icon = {
                        Icon(painter =
                        if (selectedScreen == screen.route) {
                            painterResource(id = screen.iconFilled)
                        }else{
                            painterResource(id = screen.iconOutlined)
                        },
                            contentDescription = "Navigation Bar",
                            tint = Color.White
                            )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedIconColor = Color.LightGray,
                        unselectedTextColor = Color.LightGray,
                        indicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }

    }
}