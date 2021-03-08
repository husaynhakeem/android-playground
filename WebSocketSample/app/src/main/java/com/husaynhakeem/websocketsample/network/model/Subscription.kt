package com.husaynhakeem.websocketsample.network.model

data class Subscription(
    val event: String,
    val data: SubscriptionData = SubscriptionData()
)

data class SubscriptionData(val channel: String = "live_trades_btcusd")
