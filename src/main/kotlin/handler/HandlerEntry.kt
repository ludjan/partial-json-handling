package handler

import kotlinx.serialization.KSerializer

data class HandlerEntry<T>(
    val serializer: KSerializer<T>,
    val handler: (T) -> Unit
)