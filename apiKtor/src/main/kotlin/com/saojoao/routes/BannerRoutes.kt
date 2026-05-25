package com.saojoao.routes

import com.saojoao.models.BannerRequest
import com.saojoao.repository.BannerRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bannerRoutes(repository: BannerRepository) {
    route("/banners") {
        get {
            call.respond(repository.listarTodos())
        }

        post {
            val request = call.receive<BannerRequest>()
            val created = repository.inserir(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest, "ID inválido")
            val request = call.receive<BannerRequest>()
            val updated = repository.atualizar(id, request)
            if (updated) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.NotFound, "Banner não encontrado")
        }

        delete("/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "ID inválido")
            val deleted = repository.deletar(id)
            if (deleted) call.respond(HttpStatusCode.NoContent) else call.respond(HttpStatusCode.NotFound, "Banner não encontrado")
        }
    }
}
