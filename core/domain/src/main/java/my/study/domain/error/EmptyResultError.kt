package my.study.domain.error

class EmptyResultError(
    cause: Throwable? = null
) : Throwable(cause)