package my.study.domain.model

data class DataWithSource<T>(
    val data: T,
    val source: DataSource,
    val ageSeconds: Long? = null
)

enum class DataSource {
    NETWORK, CACHE
}
