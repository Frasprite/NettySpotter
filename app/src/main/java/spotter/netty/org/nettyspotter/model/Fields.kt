package spotter.netty.org.nettyspotter.model

import com.google.gson.annotations.SerializedName

data class Fields(@SerializedName("objectid") val objectId: Int,
                  @SerializedName("source") val source: String,
                  @SerializedName("arrondissement") val district: String,
                  @SerializedName("nom_voie") val streetName: String,
                  @SerializedName("gestionnaire") val manager: String,
                  @SerializedName("numero_voie") val number: String,
                  @SerializedName("identifiant") val toiletId: String,
                  @SerializedName("horaires_ouverture") val openHours: String)


