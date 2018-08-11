package spotter.netty.org.nettyspotter.rawmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Geometry(
        @SerializedName("type")
        @Expose
        var type: String,
        @SerializedName("coordinates")
        @Expose
        var coordinates: List<Float>
)