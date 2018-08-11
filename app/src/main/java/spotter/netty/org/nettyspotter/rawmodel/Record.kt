package spotter.netty.org.nettyspotter.rawmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Record(

        @SerializedName("datasetid")
        @Expose
        var datasetid: String,
        @SerializedName("recordid")
        @Expose
        var recordid: String,
        @SerializedName("fields")
        @Expose
        var fields: Fields,
        @SerializedName("geometry")
        @Expose
        var geometry: Geometry,
        @SerializedName("record_timestamp")
        @Expose
        var recordTimestamp: String

)