package spotter.netty.org.nettyspotter.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Immutable model class for a public toilet (AKA netty).
 * Objects of this type are received from the RATP open API, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room nettys table, where the netty [id] is the primary key.
 */
@Entity(tableName = "netties")
data class Netty(val datasetId: String,
                 @PrimaryKey val recordId: String,
                 val type: String,
                 val longitude: Float,
                 val latitude: Float,
                 val recordTimestamp: String,
                 val objectId: Int,
                 val source: String,
                 val district: String,
                 val streetName: String,
                 val manager: String,
                 val number: String,
                 val toiletId: String,
                 val openHours: String)