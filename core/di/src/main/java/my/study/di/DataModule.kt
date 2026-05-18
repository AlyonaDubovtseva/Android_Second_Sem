package my.study.di

import dagger.Binds
import dagger.Module
import my.study.data.repository.BreedRepositoryImpl
import my.study.domain.repository.BreedRepository
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindBreedRepository(
        impl: BreedRepositoryImpl
    ): BreedRepository
}