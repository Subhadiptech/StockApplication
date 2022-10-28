package com.ersubhadip.stockapplication.domain.repositories

import com.ersubhadip.stockapplication.Resource
import com.ersubhadip.stockapplication.domain.models.CompanyListing
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}