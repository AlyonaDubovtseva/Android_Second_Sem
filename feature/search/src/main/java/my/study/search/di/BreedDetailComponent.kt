package my.study.search.di

import dagger.BindsInstance
import dagger.Subcomponent
import my.study.search.ui.BreedDetailViewModel

@Subcomponent
interface BreedDetailComponent {

    fun viewModel(): BreedDetailViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance @BreedId breedId: String
        ): BreedDetailComponent
    }
}