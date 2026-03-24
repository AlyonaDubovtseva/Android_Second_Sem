package my.study.domain.error

class NetworkError(
    cause: Throwable? = null
) : Throwable(cause)