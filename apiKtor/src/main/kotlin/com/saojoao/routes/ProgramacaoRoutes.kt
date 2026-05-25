package com.saojoao.routes

import com.saojoao.models.ProgramacaoRequest
import com.saojoao.repository.ProgramacaoRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
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

        post {
            val request = call.receive<ProgramacaoRequest>()
            val created = repository.inserir(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")
            val request = call.receive<ProgramacaoRequest>()
            val updated = repository.atualizar(id, request)
            if (updated) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound, "Programação não encontrada")
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID inválido")
            val deleted = repository.deletar(id)
            if (deleted) call.respond(HttpStatusCode.NoContent) else call.respond(HttpStatusCode.NotFound, "Programação não encontrada")
        }
    }
}
