package spotter.netty.org.nettyspotter.model

import android.arch.persistence.room.Embedded
import com.google.gson.annotations.SerializedName

data class Geometry(@SerializedName("type") val type: String,
                    @Embedded(prefix = "coordinates") val coordinates: Coordinates)



