package spotter.netty.org.nettyspotter.rawmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Parameters(

        @SerializedName("dataset")
        @Expose
        var dataset: List<String>,
        @SerializedName("timezone")
        @Expose
        var timezone: String,
        @SerializedName("rows")
        @Expose
        var rows: Int,
        @SerializedName("start")
        @Expose
        var start: Int,
        @SerializedName("format")
        @Expose
        var format: String?

)