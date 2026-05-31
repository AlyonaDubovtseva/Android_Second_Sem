package my.study.data.cache

import my.study.domain.model.BreedModel
import javax.inject.Inject

class BreedCache @Inject constructor() {
    private val cache = mutableMapOf<String, CacheEntry>()
    private val ttlSeconds = 60L

    private data class CacheEntry(
        val breeds: List<BreedModel>,
        val timestamp: Long = System.currentTimeMillis()
    )

    fun get(query: String): CacheResult? {
        val entry = cache[query] ?: return null
        val now = System.currentTimeMillis()
        val ageSeconds = (now - entry.timestamp) / 1000
        if (ageSeconds > ttlSeconds) {
            cache.remove(query)
            return null
        }
        return CacheResult(
            breeds = entry.breeds,
            ageSeconds = ageSeconds
        )
    }

    data class CacheResult(
        val breeds: List<BreedModel>,
        val ageSeconds: Long
    )

    fun put(query: String, breeds: List<BreedModel>) {
        cache[query] = CacheEntry(breeds)
    }
}