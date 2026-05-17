package com.example.sao_joao_arcocity.network

import com.example.sao_joao_arcocity.models.PontoResponse
import com.example.sao_joao_arcocity.models.ProgramacaoResponse
import com.example.sao_joao_arcocity.models.BannerResponse
import com.example.sao_joao_arcocity.models.LiveResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pontos")
    suspend fun buscarPontos(): List<PontoResponse>

    @GET("pontos/{id}")
    suspend fun buscarPontoPorId(@Path("id") id: Int): PontoResponse

    @GET("programacoes")
    suspend fun buscarProgramacoes(): List<ProgramacaoResponse>

    @GET("programacoes/{id}")
    suspend fun buscarProgramacaoPorId(@Path("id") id: Int): ProgramacaoResponse

    @GET("banners")
    suspend fun buscarBanners(): List<BannerResponse>

    @GET("live")
    suspend fun buscarLive(): LiveResponse
}