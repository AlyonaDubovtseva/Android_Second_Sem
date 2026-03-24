package my.study.impl

import my.study.api.BuildConfigProvider

class BuildConfigProviderImpl : BuildConfigProvider{
    override fun getCatApiBaseUrl(): String {
        return BuildConfig.CAT_API_BASE_URL
    }

    override fun getCatApiKey(): String {
        return BuildConfig.CAT_API_KEY
    }
}