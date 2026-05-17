package com.saojoao

import com.saojoao.routes.pontosRoutes
import com.saojoao.routes.programacaoRoutes
import com.saojoao.routes.bannerRoutes
import com.saojoao.routes.liveRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("API São João de arcoverde")
        }

        pontosRoutes()
        programacaoRoutes()
        bannerRoutes()
        liveRoutes()
    }
}