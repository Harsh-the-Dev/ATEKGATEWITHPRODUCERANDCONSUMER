package com.atek.gate.data.model.config

import com.atek.gate.app.database.entity.Equipment
import com.atek.gate.app.database.entity.Fare
import com.atek.gate.app.database.entity.Product

data class ConfigResponse(
    val equipment: Equipment?,
    val fares: List<Fare>?,
    val products: List<Product>?
)
