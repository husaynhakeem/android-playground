package com.husaynhakeem.hiltsample.di

import com.husaynhakeem.hiltsample.data.PostsDataSource
import com.husaynhakeem.hiltsample.data.PostsFakeDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier

@Qualifier
annotation class FakeDataSource

@Qualifier
annotation class RealDatSource

@Module
@InstallIn(ActivityComponent::class)
abstract class PostsDataSourceModule {

    @Binds
    @FakeDataSource
    abstract fun fakeDataSource(dataSource: PostsFakeDataSource): PostsDataSource
}