package me.thestars.orbit.database.data

import  kotlinx.serialization.Serializable

@Serializable
data class InnerMemberResponse(val id: Long, var currency: Long)