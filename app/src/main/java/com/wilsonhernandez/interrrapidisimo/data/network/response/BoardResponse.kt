package com.wilsonhernandez.interrrapidisimo.data.network.response

import com.google.gson.annotations.SerializedName

data class BoardResponse(
    @SerializedName("NombreTabla") val nameBoard: String,
    @SerializedName("Pk") val pk: String,
    @SerializedName("QueryCreacion") val query: String,
    @SerializedName("BatchSize") val batchSize: String,
    @SerializedName("Filtro") val filter: String,
    @SerializedName("Error") val error: String,
    @SerializedName("NumeroCampos") val numberFields: Int,
    @SerializedName("MetodoApp") val methodApp: String,
    @SerializedName("FechaActualizacionSincro") val dateUpdate: String,
)
