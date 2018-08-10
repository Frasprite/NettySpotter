package spotter.netty.org.nettyspotter.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import com.google.gson.annotations.SerializedName

/**
 * Immutable model class for a public toilet (AKA netty).
 * Objects of this type are received from the RATP open API, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room nettys table, where the netty [id] is the primary key.
 */
@Entity(tableName = "nettys")
data class Netty(@field:SerializedName("datasetid") val datasetId: String,
                 @PrimaryKey @field:SerializedName("recordid") val recordId: String,
                 @Embedded(prefix = "fields") val fields: Fields,
                 @Embedded(prefix = "geometry") val geometry: Geometry,
                 @field:SerializedName("record_timestamp") val recordTimestamp: String)