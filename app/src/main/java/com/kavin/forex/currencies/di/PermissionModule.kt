package com.kavin.forex.currencies.di

import android.app.Activity
import com.kavin.forex.currencies.common.InternetPermissionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object PermissionModule {

    @Provides
    @Singleton
    fun providePermissionManager(activity: Activity): InternetPermissionManager {
        return InternetPermissionManager(activity)
    }
}
