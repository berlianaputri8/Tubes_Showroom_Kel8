package id.ac.unpas.showroom.di


import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.ac.unpas.showroom.persistences.MobilDao
import id.ac.unpas.showroom.persistences.MobilDatabase
import id.ac.unpas.showroom.persistences.MotorDao
import id.ac.unpas.showroom.persistences.MotorDatabase
import id.ac.unpas.showroom.persistences.PromoDao
import id.ac.unpas.showroom.persistences.PromoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    @Provides
    @Singleton
    fun provideMobilDatabase(application: Application): MobilDatabase {
        return Room
            .databaseBuilder(
                application,
                MobilDatabase::class.java,
                "mobil"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMobilDao(appDatabase: MobilDatabase): MobilDao {
        return appDatabase.mobilDao()
    }

    @Provides
    @Singleton
    fun provideMotorDatabase(application: Application): MotorDatabase {
        return Room
            .databaseBuilder(
                application,
                MotorDatabase::class.java,
                "motor"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMotorDao(appDatabase: MotorDatabase): MotorDao {
        return appDatabase.motorDao()
    }

    @Provides
    @Singleton
    fun providePromoDatabase(application: Application): PromoDatabase {
        return Room
            .databaseBuilder(
                application,
                PromoDatabase::class.java,
                "promo"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePromoDao(appDatabase: PromoDatabase): PromoDao {
        return appDatabase.promoDao()
    }
}