import Exceptions.CryptoComServerResException
import Models.MoshiAdapters
import Models.Period
import Models.TradePair
import MoshiHelper.Companion.jsonStringToObject
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.OkHttpClient

class CryptoComX {

    companion object {

        private val scarletInstance = Scarlet.Builder()
            .webSocketFactory(OkHttpClient().newWebSocketFactory(StringConstants.WS_ENDPOINT))
            .addMessageAdapterFactory(com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter.Factory(MoshiHelper.getInstance()))
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
            val resFromServer = HTTPHelper.getHttp(StringConstants.GETTICKER_ENDPOINT)
            return jsonStringToObject(GetTickerForAllMarketRes::class.java, resFromServer)
        }

        fun getTickerForMarket(tradePair: TradePair): GetTickerForSingleMarketRes {
            val resFromServer = HTTPHelper.getHttp(StringConstants.GETTICKER_ENDPOINT, mapOf("symbol" to tradePair.toString()))
            return jsonStringToObject(GetTickerForSingleMarketRes::class.java, resFromServer)
        }

        fun getKline(tradePair: TradePair, period: Period) : GetKLineOverPeriodRes{
            val resFromServer = HTTPHelper.getHttp(StringConstants.GETKLINE_ENDPOINT, mapOf("symbol" to tradePair.toString(), "period" to period.inMin.toString()))
            return jsonStringToObject(GetKLineOverPeriodRes::class.java, resFromServer)
        }

        fun getLastTrades(tradePair: TradePair) : GetLastTradeRes {
            val resFromServer = HTTPHelper.getHttp(StringConstants.GETLAST200_ENDPOINT, mapOf("symbol" to tradePair.toString()))
            return jsonStringToObject(GetLastTradeRes::class.java, resFromServer)
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