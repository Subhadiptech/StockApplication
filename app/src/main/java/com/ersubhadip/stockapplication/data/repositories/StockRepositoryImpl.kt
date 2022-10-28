package com.ersubhadip.stockapplication.data.repositories

import com.ersubhadip.stockapplication.Resource
import com.ersubhadip.stockapplication.data.csv.CSVParser
import com.ersubhadip.stockapplication.data.csv.CompanyListingParser
import com.ersubhadip.stockapplication.data.local.StockDao
import com.ersubhadip.stockapplication.data.local.StockDatabase
import com.ersubhadip.stockapplication.data.mappers.toCompanyListing
import com.ersubhadip.stockapplication.data.mappers.toCompanyListingEntity
import com.ersubhadip.stockapplication.data.remote.StockAPI
import com.ersubhadip.stockapplication.domain.models.CompanyListing
import com.ersubhadip.stockapplication.domain.repositories.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockAPI,
    val db: StockDatabase,
    val companyListingParser: CSVParser<CompanyListing>
) : StockRepository {
    private val dao = db.dao
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchListings(query)
            emit(Resource.Success(
                data = localListings.map {
                    it.toCompanyListing()
                }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldLoanFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldLoanFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings(StockAPI.API_KEY)
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Please Try again later!"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Something Went Wrong!"))
                null
            }

            //if remote data is not equal to null
            remoteListings?.let {
                emit(Resource.Success(it))
                emit(Resource.Loading(false))
                dao.deleteCompanyListings()
                dao.insertCompanyListings(it.map {
                    it.toCompanyListingEntity()
                })
            }
        }
    }
}