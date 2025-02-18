package com.begunok.begunok.data.models

import java.util.Date

data class InfoPackage(
    val timeLast: Date,
    val distance: Double,
    val call: Boolean = false
)
