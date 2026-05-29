package com.saojoao.routes

import com.saojoao.models.AlertaRequest
import com.saojoao.repository.AlertaRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.alertaRoutes(repository: AlertaRepository) {
    route("/alertas") {
        get("/ativos") {
            call.respond(repository.listarAtivos())
        }

        post {
            val request = call.receive<AlertaRequest>()
            call.respond(HttpStatusCode.Created, repository.inserir(request))
        }

        put("/{id}/desativar") {
            val id = call.parameters["id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")
            if (repository.desativar(id)) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.NotFound, "Alerta não encontrado")
        }

        delete("/{id}") {
            val id = call.parameters["id"]
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID inválido")
            if (repository.deletar(id)) call.respond(HttpStatusCode.NoContent)
            else call.respond(HttpStatusCode.NotFound, "Alerta não encontrado")
        }
    }
}
