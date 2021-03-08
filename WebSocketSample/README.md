# WebSocket client example in Android
Android sample app to learn how to implement a web socket client. This is achieved using Okhttp3's [WebSocket](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-web-socket/) and [WebSocketClient](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-web-socket-listener/) APIs. The sample opens a connection to the [Bitstamp web socket](https://www.bitstamp.net/websocket/v2/), subscribes to Bitcoin trade events and displays its price (as well as its fluctuations).

Another goal of this sample is to convert a callback-based API to a stream-based API, namely Kotlin Flow. To achieve this, the sample uses [callbackFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/callback-flow.html).

![web-socket-sample](https://github.com/husaynhakeem/android-playground/blob/master/WebSocketSample/art/web-socket-android.gif)
