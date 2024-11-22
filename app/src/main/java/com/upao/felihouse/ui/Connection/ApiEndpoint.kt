package com.upao.felihouse.ui.Connection

import retrofit2.http.GET

interface ApiEndpoint {

    @GET("cuarto1on")
    suspend fun encenderCuarto1(): String

    @GET("cuarto1off")
    suspend fun apagarCuarto1(): String

    @GET("cuarto2on")
    suspend fun encenderCuarto2(): String

    @GET("cuarto2off")
    suspend fun apagarCuarto2(): String

    @GET("cuartoprincipalon")
    suspend fun encenderCuartoPrincipal(): String

    @GET("cuartoprincipaloff")
    suspend fun apagarCuartoPrincipal(): String

    @GET("banoon")
    suspend fun encenderBano(): String

    @GET("banooff")
    suspend fun apagarBano(): String

    @GET("pasadisoon")
    suspend fun encenderPasadiso(): String

    @GET("pasadisooff")
    suspend fun apagarPasadiso(): String

    @GET("salaon")
    suspend fun encenderSala(): String

    @GET("salaoff")
    suspend fun apagarSala(): String

    @GET("cocinaon")
    suspend fun encenderCocina(): String

    @GET("cocinaoff")
    suspend fun apagarCocina(): String

    @GET("cocheraon")
    suspend fun encenderCochera(): String

    @GET("cocheraoff")
    suspend fun apagarCochera(): String

    @GET("jardinon")
    suspend fun encenderJardin(): String

    @GET("jardinoff")
    suspend fun apagarJardin(): String

    @GET("allon")
    suspend fun encenderTodo(): String

    @GET("alloff")
    suspend fun apagarTodo(): String
}
