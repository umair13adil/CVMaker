package com.blackbox.onepage.cvmaker.di

import com.blackbox.onepage.cvmaker.data.local.RealmHelper
import com.blackbox.onepage.cvmaker.data.repositories.CVRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCVDataSource(db: RealmHelper): CVRepository {
        return CVRepository(db)
    }
}