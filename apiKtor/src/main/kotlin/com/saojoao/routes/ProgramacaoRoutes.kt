package com.saojoao.routes

import com.saojoao.data.programacoesMock
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.programacaoRoutes() {
    route("/programacoes") {
        get {
            call.respond(programacoesMock)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val programacao = programacoesMock.find { it.id == id }

            if (programacao == null) {
                call.respondText("Programação não encontrada")
            } else {
                call.respond(programacao)
            }
        }
    }
}