package com.saojoao.routes

import com.saojoao.data.bannersMock
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bannerRoutes() {
    route("/banners") {
        get {
            call.respond(bannersMock)
        }
    }
}