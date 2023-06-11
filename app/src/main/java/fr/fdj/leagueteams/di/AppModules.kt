package fr.fdj.leagueteams.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.fdj.leagueteams.data.api.LeagueTeamApi
import fr.fdj.leagueteams.data.api.LeagueTeamRepoImpl
import fr.fdj.leagueteams.data.local.LeagueTeamsDatabase
import fr.fdj.leagueteams.data.local.dao.LeagueDao
import fr.fdj.leagueteams.data.local.dao.TeamDao
import fr.fdj.leagueteams.data.repository.LeagueTeamRepository
import fr.fdj.leagueteams.utils.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModules {

    @Provides
    @Singleton
    fun provideRetrofit(
    ): Retrofit {
        val gson: Gson by lazy {
            GsonBuilder().setLenient().create()
        }

        val httpClient : OkHttpClient by lazy {
            OkHttpClient.Builder().build()
        }

        return Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideLeagueTeamsApi(
        leagueTeamsRetrofit: Retrofit
    ): LeagueTeamApi = leagueTeamsRetrofit.create(LeagueTeamApi::class.java)

    @Provides
    @Singleton
    fun provideLocalDatabase(
        @ApplicationContext context: Context
    ): LeagueTeamsDatabase = LeagueTeamsDatabase.getDatabase(
        context,
        CoroutineScope(SupervisorJob())
    )

    @Provides
    @Singleton
    fun provideTeamDao(
        leagueTeamsDatabase: LeagueTeamsDatabase
    ): TeamDao = leagueTeamsDatabase.TeamDao()

    @Provides
    @Singleton
    fun provideLeagueDao(
        leagueTeamsDatabase: LeagueTeamsDatabase
    ): LeagueDao = leagueTeamsDatabase.LeagueDao()

    @Provides
    @Singleton
    fun provideLeagueTeamRepository(
        api: LeagueTeamApi,
        teamDao: TeamDao,
        leagueDao: LeagueDao
    ): LeagueTeamRepository = LeagueTeamRepoImpl(api, teamDao, leagueDao)
}