package my.study.di

import dagger.Module
import my.study.search.di.BreedDetailComponent

@Module(
    subcomponents = [
        BreedDetailComponent::class
    ]
)
object AppSubcomponentsModule