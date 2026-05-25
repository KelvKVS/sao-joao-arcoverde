package com.saojoao.routes

import com.saojoao.repository.ProgramacaoRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.programacaoRoutes(repository: ProgramacaoRepository) {
    route("/programacoes") {
        get {
            call.respond(repository.listarTodas())
        }

        get("/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }

            val programacao = repository.buscarPorId(id)

            if (programacao == null) {
                call.respond(HttpStatusCode.NotFound, "Programação não encontrada")
            } else {
                call.respond(programacao)
            }
        }
    }
}