package com.example.sao_joao_arcocity.network

import com.example.sao_joao_arcocity.models.AlertaResponse
import com.example.sao_joao_arcocity.models.BannerResponse
import com.example.sao_joao_arcocity.models.IncidenteRequest
import com.example.sao_joao_arcocity.models.IncidenteResponse
import com.example.sao_joao_arcocity.models.LiveResponse
import com.example.sao_joao_arcocity.models.PontoResponse
import com.example.sao_joao_arcocity.models.ProgramacaoResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("banners")
    suspend fun buscarBanners(): List<BannerResponse>

    @GET("programacoes")
    suspend fun buscarProgramacoes(): List<ProgramacaoResponse>

    @GET("programacoes/{id}")
    suspend fun buscarProgramacaoPorId(@Path("id") id: String): ProgramacaoResponse

    @GET("live")
    suspend fun buscarLive(): LiveResponse

    @GET("pontos")
    suspend fun buscarPontos(): List<PontoResponse>

    @GET("pontos/{id}")
    suspend fun buscarPontoPorId(@Path("id") id: String): PontoResponse

    @GET("alertas/ativos")
    suspend fun buscarAlertasAtivos(): List<AlertaResponse>

    @POST("incidentes")
    suspend fun registrarIncidente(@Body request: IncidenteRequest): IncidenteResponse
}
