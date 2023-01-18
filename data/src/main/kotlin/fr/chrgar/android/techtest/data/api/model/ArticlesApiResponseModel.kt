package fr.chrgar.android.techtest.data.api.model

/**
 * Wrapper class to handle the Api returnValues in a standardized way
 */
sealed class ArticlesApiResponseModel<out SUCCESS, out FAILURE> {
    data class Success<out SUCCESS>(val value: SUCCESS) : ArticlesApiResponseModel<SUCCESS, Nothing>()
    data class Failure<out FAILURE>(val cause: FAILURE) : ArticlesApiResponseModel<Nothing, FAILURE>()

    companion object {
        /**
         * Returns the given value as a successful ApiResponse
         */
        fun <T> success(value: T) = Success(value)

        /**
         * Returns the given value as a failed ApiResponse
         */
        fun <T> failure(cause: T) = Failure(cause)
    }
}

