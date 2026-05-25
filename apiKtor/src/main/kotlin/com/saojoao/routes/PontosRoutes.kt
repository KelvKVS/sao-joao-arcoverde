package com.saojoao.routes

import com.saojoao.models.PontoRequest
import com.saojoao.repository.PontoRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
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

        post {
            val request = call.receive<PontoRequest>()
            val created = repository.inserir(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")
            val request = call.receive<PontoRequest>()
            val updated = repository.atualizar(id, request)
            if (updated) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound, "Ponto não encontrado")
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID inválido")
            val deleted = repository.deletar(id)
            if (deleted) call.respond(HttpStatusCode.NoContent) else call.respond(HttpStatusCode.NotFound, "Ponto não encontrado")
        }
    }
}
