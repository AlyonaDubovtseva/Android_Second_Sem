package my.study.domain.error

class UnknownError(
    cause: Throwable? = null
) : Throwable(cause)