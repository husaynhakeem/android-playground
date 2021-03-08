package com.husaynhakeem.websocketsample.network

import com.google.gson.Gson
import com.husaynhakeem.websocketsample.network.model.Channel
import com.husaynhakeem.websocketsample.network.model.Subscription
import com.husaynhakeem.websocketsample.network.model.isTrade
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.*
import okio.ByteString

@OptIn(ExperimentalCoroutinesApi::class)
class BtcWebSocket {

    private lateinit var socket: WebSocket
    private val gson = Gson()

    val events: Flow<State> = callbackFlow {
        // send initial state
        sendBlocking(State.Connecting)

        // Attach listener to the socket
        val client = OkHttpClient()
        val request = Request.Builder().url(SOCKET_URL).build()
        val listener = createListener()
        socket = client.newWebSocket(request, listener)

        // Cancel the socket when the channel is closed
        awaitClose {
            unsubscribe()
            socket.cancel()
        }
    }

    private fun ProducerScope<State>.createListener(): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                sendBlocking(State.Connected)
                subscribe()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                val channel = gson.fromJson(text, Channel::class.java)
                if (channel.isTrade()) {
                    sendBlocking(State.Price(channel.data.price))
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                onMessage(webSocket, String(bytes.toByteArray()))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                sendBlocking(State.Disconnecting)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                sendBlocking(State.Disconnected)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                sendBlocking(State.Error("Error: ${t.message}"))
            }
        }
    }

    private fun subscribe() {
        socket.send(gson.toJson(SUBSCRIBE_MESSAGE))
    }

    private fun unsubscribe() {
        socket.send(gson.toJson(UNSUBSCRIBE_MESSAGE))
    }

    sealed class State {
        object Connecting : State()
        object Connected : State()
        data class Price(val value: Double) : State()
        object Disconnecting : State()
        object Disconnected : State()
        data class Error(val cause: String) : State()
    }

    companion object {
        private const val SOCKET_URL = "wss://ws.bitstamp.net"
        private val SUBSCRIBE_MESSAGE = Subscription(event = "bts:subscribe")
        private val UNSUBSCRIBE_MESSAGE = Subscription(event = "bts:unsubscribe")
    }
}