package my.study.di

import dagger.Component
import my.study.domain.usecase.GetBreedByIdUseCase
import my.study.domain.usecase.SearchBreedsUseCase
import my.study.search.di.BreedDetailComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        AppSubcomponentsModule::class
    ]
)
interface AppComponent {

    fun getSearchBreedsUseCase(): SearchBreedsUseCase
    fun getGetBreedByIdUseCase(): GetBreedByIdUseCase
    fun breedDetailComponentFactory(): BreedDetailComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}