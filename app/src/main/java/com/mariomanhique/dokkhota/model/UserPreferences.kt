package com.mariomanhique.dokkhota.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    var examNumber: String ="",
)