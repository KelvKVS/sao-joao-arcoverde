package com.saojoao.routes

import com.saojoao.repository.PontoRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.pontosRoutes(repository: PontoRepository) {
    route("/pontos") {
        get {
            call.respond(repository.listarTodos())
        }

        get("/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }

            val ponto = repository.buscarPorId(id)

            if (ponto == null) {
                call.respond(HttpStatusCode.NotFound, "Ponto não encontrado")
            } else {
                call.respond(ponto)
            }
        }
    }
}