package com.ersubhadip.stockmarketapp.di

import com.ersubhadip.stockmarketapp.data.csv.CSVParser
import com.ersubhadip.stockmarketapp.data.csv.CompanyListingsParser
import com.ersubhadip.stockmarketapp.data.csv.IntradayInfoParser
import com.ersubhadip.stockmarketapp.data.repository.StockRepositoryImpl
import com.ersubhadip.stockmarketapp.domain.model.CompanyListing
import com.ersubhadip.stockmarketapp.domain.model.IntradayInfo
import com.ersubhadip.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}