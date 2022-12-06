package so.howl.common.api

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
        })
    }
}