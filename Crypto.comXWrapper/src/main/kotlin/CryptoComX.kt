import Exceptions.CryptoComServerResException
import Models.MoshiAdapters
import Models.TradePair
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Message
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient

class CryptoComX {

    companion object {
        val moshi = Moshi.Builder()
            .add(MoshiAdapters())
            .add(KotlinJsonAdapterFactory())
            .build()

        val scarletInstance = Scarlet.Builder()
            .webSocketFactory(OkHttpClient().newWebSocketFactory(StringConstants.WS_ENDPOINT))
            .addMessageAdapterFactory(com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()

        fun getAllMarket() : GetAllMarketRes{
            val resFromServer = HTTPHelper.getHttp(StringConstants.GETALLMARKET_ENDPOINT)
            return jsonStringToObject(GetAllMarketRes::class.java, resFromServer)
        }

        fun getAllBalances() : GetAllBalanceRes {
            val resFromServer = HTTPHelper.postHttpSigned(StringConstants.GETALLBALANCE_ENDPOINT)
            return jsonStringToObject(GetAllBalanceRes::class.java, resFromServer)
        }

        fun setApiKey(s:String) {
            HTTPHelper.cryptoManager.apiKey = s
        }

        fun setApiSecret(s:String) {
            HTTPHelper.cryptoManager.apiSecret = s
        }

        fun getTickersForAllMarkets() : GetTickerForAllMarketRes{
            val resFromServer = HTTPHelper.getHttp(StringConstants.GETTICKERALLMARKETS_ENDPOINT)
            return jsonStringToObject(GetTickerForAllMarketRes::class.java, resFromServer)
        }

        fun <T> jsonStringToObject(className: Class<T>, dataString:String?): T {
            if (dataString != null) {
                val toReturn = moshi.adapter(className).fromJson(dataString)
                if (toReturn== null) {
                    throw CryptoComServerResException()
                } else {
                    return toReturn
                }
            } else {
                throw CryptoComServerResException()
            }
        }

        fun subLatestTicker(pair: TradePair, lmbd: (WssSubNewTickerResponse) -> Unit) {
            val service = scarletInstance.create<SubNewTickerService>()

            val SUBSCRIBE_MESSAGE = SubNewTickerService.Subscribe(
                "sub",
                SubNewTickerService.Params("market_" + pair.toString() + "_ticker")
            )

            service.observeWebSocketEvent()
                .filter { it is WebSocket.Event.OnConnectionOpened<*> }
                .subscribe {
                    service.sendSubscribe(SUBSCRIBE_MESSAGE)
                }

            service.observeWebSocketEvent()
                .filter { it is WebSocket.Event.OnMessageReceived  }
                .subscribe { res ->
                    val str = WssResDecoder.webSocketMessageEventToString(res as WebSocket.Event.OnMessageReceived)
                    if(str!= null && !WssResDecoder.isPingResponse(str))
                        lmbd(jsonStringToObject(WssSubNewTickerResponse::class.java, str))
                }
        }
    }
}