import Models.TradePair
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface SubNewTickerService {
    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>
    @Send
    fun sendSubscribe(subscribe: Subscribe)
    @Receive
    fun observeTicker(): Flowable<WssSubNewTickerResponse>

    data class Subscribe(
        val event: String,
        val params:Params
    )


    data class Params (
        val channel: String
    )
}