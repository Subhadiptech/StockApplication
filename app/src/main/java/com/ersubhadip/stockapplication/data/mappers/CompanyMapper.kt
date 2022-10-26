package com.ersubhadip.stockapplication.data.mappers

import com.ersubhadip.stockapplication.data.local.CompanyListingEntity
import com.ersubhadip.stockapplication.domain.models.CompanyListing

fun CompanyListingEntity.toCompanyListing():CompanyListing{
    return CompanyListing(
        name=name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity():CompanyListingEntity{
    return CompanyListingEntity(
        name=name,
        symbol = symbol,
        exchange = exchange
    )
}