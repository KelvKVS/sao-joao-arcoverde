package com.saojoao.routes

import com.saojoao.data.pontosMock
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.pontosRoutes() {
    route("/pontos") {
        get {
            call.respond(pontosMock)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            val ponto = pontosMock.find { it.id == id }

            if (ponto == null) {
                call.respondText("Ponto não encontrado")
            } else {
                call.respond(ponto)
            }
        }
    }
}