package spotter.netty.org.nettyspotter.rawmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RawData(
        @SerializedName("nhits")
        @Expose
        var nhits: Int,
        @SerializedName("parameters")
        @Expose
        var parameters: Parameters,
        @SerializedName("records")
        @Expose
        var records: List<Record>
)