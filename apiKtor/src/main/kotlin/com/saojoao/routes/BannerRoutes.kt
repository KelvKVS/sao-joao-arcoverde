package com.saojoao.routes

import com.saojoao.repository.BannerRepository
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.bannerRoutes(repository: BannerRepository) {
    route("/banners") {
        get {
            call.respond(repository.listarTodos())
        }
    }
}