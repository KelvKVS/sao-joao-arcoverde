package com.saojoao

import com.saojoao.database.mongoDatabase
import com.saojoao.repository.BannerRepository
import com.saojoao.repository.LiveRepository
import com.saojoao.repository.PontoRepository
import com.saojoao.repository.ProgramacaoRepository
import com.saojoao.routes.pontosRoutes
import com.saojoao.routes.programacaoRoutes
import com.saojoao.routes.bannerRoutes
import com.saojoao.routes.liveRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val bannerRepository = BannerRepository(mongoDatabase)
    val liveRepository = LiveRepository(mongoDatabase)
    val pontoRepository = PontoRepository(mongoDatabase)
    val programacaoRepository = ProgramacaoRepository(mongoDatabase)

    routing {
        get("/") {
            call.respondText("API São João de arcoverde")
        }

        pontosRoutes(pontoRepository)
        programacaoRoutes(programacaoRepository)
        bannerRoutes(bannerRepository)
        liveRoutes(liveRepository)
    }
}