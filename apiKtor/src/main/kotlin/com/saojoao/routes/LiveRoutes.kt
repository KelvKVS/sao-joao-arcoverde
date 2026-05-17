package com.saojoao.routes

import com.saojoao.data.liveMock
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.liveRoutes() {
    get("/live") {
        call.respond(liveMock)
    }
}