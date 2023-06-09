package com.example.myapp.ui.navigation

sealed class NavRoute(val path: String) {

    object Login: NavRoute("login")

    object Home: NavRoute("home")

    object Figures: NavRoute("figures")

    object Search: NavRoute("search")

    // build navigation path (for screen navigation)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/$arg")
            }
        }
    }

    // build and setup route format (in navigation graph)
    fun withArgsFormat(vararg args: String) : String {
        return buildString {
            append(path)
            args.forEach{ arg ->
                append("/{$arg}")
            }
        }
    }
}

