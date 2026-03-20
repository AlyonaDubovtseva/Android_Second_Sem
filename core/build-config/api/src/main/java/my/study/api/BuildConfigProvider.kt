package my.study.api

interface BuildConfigProvider {
    fun getCatApiBaseUrl() : String
    fun getCatApiKey() : String
}