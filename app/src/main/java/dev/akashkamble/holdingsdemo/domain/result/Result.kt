package dev.akashkamble.holdingsdemo.domain.result

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(
        val error: String
    ) : Result<Nothing>
}
