package my.study.domain.error

class ApiError(
    val code: Int,
    cause: Throwable? = null
) : Throwable(cause)