package com.ersubhadip.stockapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntity: List<CompanyListingEntity>
    )

    @Query("DELETE FROM company_listing")
    suspend fun deleteCompanyListings()

    @Query(
        """
            SELECT * FROM company_listing
            WHERE 
            LOWER(name) LIKE '%'|| LOWER(:query) LIKE '%' OR
            UPPER(:query) == symbol
        """
    )

    suspend fun searchListings(query: String): List<CompanyListingEntity>
}