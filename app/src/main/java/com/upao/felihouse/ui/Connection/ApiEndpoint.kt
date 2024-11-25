package com.upao.felihouse.ui.Connection

import retrofit2.Response
import retrofit2.http.GET

interface ApiEndpoint {

    @GET("cuarto1on")
    suspend fun encenderCuarto1(): Response<String>

    @GET("cuarto1off")
    suspend fun apagarCuarto1(): Response<String>

    @GET("cuarto2on")
    suspend fun encenderCuarto2(): Response<String>

    @GET("cuarto2off")
    suspend fun apagarCuarto2(): Response<String>

    @GET("cuartoprincipalon")
    suspend fun encenderCuartoPrincipal(): Response<String>

    @GET("cuartoprincipaloff")
    suspend fun apagarCuartoPrincipal(): Response<String>

    @GET("banoon")
    suspend fun encenderBano(): Response<String>

    @GET("banooff")
    suspend fun apagarBano(): Response<String>

    @GET("pasadisoon")
    suspend fun encenderPasadiso(): Response<String>

    @GET("pasadisooff")
    suspend fun apagarPasadiso(): Response<String>

    @GET("salaon")
    suspend fun encenderSala(): Response<String>

    @GET("salaoff")
    suspend fun apagarSala(): Response<String>

    @GET("cocinaon")
    suspend fun encenderCocina(): Response<String>

    @GET("cocinaoff")
    suspend fun apagarCocina(): Response<String>

    @GET("cocheraon")
    suspend fun encenderCochera(): Response<String>

    @GET("cocheraoff")
    suspend fun apagarCochera(): Response<String>

    @GET("jardinon")
    suspend fun encenderJardin(): Response<String>

    @GET("jardinoff")
    suspend fun apagarJardin(): Response<String>

    @GET("allon")
    suspend fun encenderTodo(): Response<String>

    @GET("alloff")
    suspend fun apagarTodo(): Response<String>
}
