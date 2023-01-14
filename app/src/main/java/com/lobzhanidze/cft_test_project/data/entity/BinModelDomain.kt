package com.lobzhanidze.cft_test_project.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_query")
data class BinModelDomain(
    @PrimaryKey
    var idBin: Int = 0,
    var scheme: String?,
    var brand: String?,
    var lengthNumber: String?,
    var luhnNumber: String?,
    var type: String?,
    var prepaid: String?,
    var emojiCountry: String?,
    var nameCountry: String?,
    var latitudeCountry: String?,
    var longitudeCountry: String?,
    var nameBank: String?,
    var urlBank: String?,
    var phoneBank: String?
    ) {
    constructor(): this(0,"","","","","","","","","","","","","")
}