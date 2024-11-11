package com.hannhb.movieapplication.navigation

enum class MovieScreen {
    HOME_SCREEN,
    DETAIL_SCREEN;
    companion object {
        fun fromRoute(route: String?): MovieScreen
        = when(route?.substringBefore("/")) {
            HOME_SCREEN.name -> HOME_SCREEN
            DETAIL_SCREEN.name -> DETAIL_SCREEN
            null -> HOME_SCREEN
            else -> throw IllegalArgumentException("Route ${route} is not recognized")
        }
    }
}