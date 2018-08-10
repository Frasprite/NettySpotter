package spotter.netty.org.nettyspotter.model

import com.google.gson.annotations.SerializedName

data class Coordinates(@SerializedName("0") val longitude: Double,
                       @SerializedName("1") val latitude: Double)