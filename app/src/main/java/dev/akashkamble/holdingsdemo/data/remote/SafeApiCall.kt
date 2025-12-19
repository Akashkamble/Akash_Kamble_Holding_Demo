package dev.akashkamble.holdingsdemo.data.remote

import dev.akashkamble.holdingsdemo.domain.result.Result
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Result<T> {
    return try {
        Result.Success(apiCall())
    } catch (
        e: UnknownHostException
    ) {
        Result.Error("No internet connection")
    } catch (e: Exception) {
        // For simplicity, we're treating all exceptions as Exception.
        // In a real-world app, you might want to handle different exceptions differently.
        // For example, you might want to handle IOException, HttpException separately.
        Result.Error(e.message.toString())
    }
}