package spotter.netty.org.nettyspotter.rawmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Fields(
        @SerializedName("objectid")
        @Expose
        var objectid: Int,
        @SerializedName("source")
        @Expose
        var source: String,
        @SerializedName("arrondissement")
        @Expose
        var arrondissement: String,
        @SerializedName("nom_voie")
        @Expose
        var nomVoie: String,
        @SerializedName("gestionnaire")
        @Expose
        var gestionnaire: String,
        @SerializedName("geom_x_y")
        @Expose
        var geomXY: List<Float>,
        @SerializedName("y")
        @Expose
        var y: Float,
        @SerializedName("x")
        @Expose
        var x: Float,
        @SerializedName("numero_voie")
        @Expose
        var numeroVoie: String? = null,
        @SerializedName("identifiant")
        @Expose
        var identifiant: String,
        @SerializedName("horaires_ouverture")
        @Expose
        var horairesOuverture: String
)
