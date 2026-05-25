package com.saojoao.routes

import com.saojoao.models.LiveRequest
import com.saojoao.repository.LiveRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.liveRoutes(repository: LiveRepository) {
    route("/lives") {
        get {
            call.respond(repository.listarTodas())
        }

        post {
            val request = call.receive<LiveRequest>()
            val created = repository.inserir(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")
            val request = call.receive<LiveRequest>()
            val updated = repository.atualizar(id, request)
            if (updated) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound, "Live não encontrada")
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID inválido")
            val deleted = repository.deletar(id)
            if (deleted) call.respond(HttpStatusCode.NoContent) else call.respond(HttpStatusCode.NotFound, "Live não encontrada")
        }
    }

    get("/live") {
        val live = repository.buscarLiveAtiva()
        if (live != null) {
            call.respond(live)
        } else {
            call.respond(HttpStatusCode.NotFound, "Live não encontrada")
        }
    }
}
