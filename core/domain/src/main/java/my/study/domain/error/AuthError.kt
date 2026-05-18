package my.study.domain.error

class AuthError(
    cause: Throwable? = null
) : Throwable(cause)