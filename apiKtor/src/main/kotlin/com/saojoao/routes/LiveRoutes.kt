package com.saojoao.routes

import com.saojoao.repository.LiveRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.liveRoutes(repository: LiveRepository) {
    get("/live") {
        val live = repository.buscarLiveAtiva()
        if (live != null) {
            call.respond(live)
        } else {
            call.respond(HttpStatusCode.NotFound, "Live não encontrada")
        }
    }
}