package com.example.biodex.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiException(message: String) : IOException(message)
class NetException : IOException("No hay conexión a internet")
class ServerException : IOException("Error en el servidor")

class ErrorInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()

        val response = try {
            chain.proceed(req)

        } catch (e: Exception){
            throw NetException()
        }

        when(response.code) {
            in 200 .. 200 -> return response
            401 -> throw ApiException("No estás autorizado")
            403 -> throw ApiException("Acceso denegado")
            404 -> throw ApiException("Recurso no encontrado")
            in 500 .. 599 -> throw ServerException()
            else -> throw ApiException("Error desconocido")

        }
    }
}