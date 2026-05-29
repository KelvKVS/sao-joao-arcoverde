package com.saojoao.routes

import com.saojoao.models.IncidenteRequest
import com.saojoao.repository.IncidenteRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.incidenteRoutes(repository: IncidenteRepository) {
    route("/incidentes") {
        get {
            call.respond(repository.listarTodos())
        }

        get("/abertos") {
            call.respond(repository.listarAbertos())
        }

        post {
            val request = call.receive<IncidenteRequest>()
            call.respond(HttpStatusCode.Created, repository.inserir(request))
        }

        put("/{id}/resolver") {
            val id = call.parameters["id"]
                ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")
            if (repository.resolver(id)) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.NotFound, "Incidente não encontrado")
        }
    }
}
