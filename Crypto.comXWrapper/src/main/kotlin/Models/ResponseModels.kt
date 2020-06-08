import Models.CoinSymbol
import Models.TradePair
import Models.TradeType

open class Res(
        val code: Int,
        val msg:String
)

class GetAllMarketRes(
        code:Int,
        msg:String,
        val data:List<MarketInfo>
): Res(code, msg) {
    data class MarketInfo (
            val symbol:TradePair,
            val count_coin:CoinSymbol,
            val amount_precision:Int,
            val base_coin:CoinSymbol,
            val price_precision:Int
    )
}

class GetAllBalanceRes (
        code:Int,
        msg:String,
        val data:TotalBalance
):Res(code, msg) {
    data class TotalBalance (
            val total_asset:Float,
            val coin_list: List<CoinBalance>
    )

    data class CoinBalance (
            val normal: Float,
            val locked: Float,
            val btcValuation: Float?,
            val coin: CoinSymbol
    )
}

class GetTickerForAllMarketRes (
        code: Int,
        msg:String,
        val data:DateAndTicker
):Res(code, msg) {
    data class DateAndTicker (
            val date:Long,
            val ticker:List<Ticker>
    )

    data class Ticker (
            val symbol: TradePair,
            val high:Float?=null,
            val vol:Float?=null,
            val last:Float?=null,
            val low:Float?=null,
            val buy:Float?=null,
            val sell:Float?=null,
            val change:Float?=null,
            val rose:Float?=null
    )
}

class GetTickerForSingleMarketRes (
    code:Int,
    msg:String,
    val data:Ticker
):Res(code, msg) {
    data class Ticker (
            val high:Float?=null,
            val vol:Float?=null,
            val last:Float?=null,
            val low:Float?=null,
            val buy:Float?=null,
            val sell:Float?=null,
            val rose:Float?=null,
            val time:Long
    )
}

data class WssSubNewTickerResponse (
        val event_rep:String,
        val channel:String,
        val data:String?,
        val tick : Ticker,
        val ts:Long,
        val status:String
) {
    data class Ticker (
            val amount:Float,
            val close:Float,
            val high:Float,
            val low:Float,
            val open:Float,
            val rose:Float,
            val vol:Float
    )
}

class GetKLineOverPeriodRes (
        code:Int,
        msg:String,
        val data:List<Ticker>
) : Res(code, msg) {
    data class Ticker (
            val time:Long,
            val open:Float,
            val high:Float,
            val low:Float,
            val close:Float,
            val vol:Float
    )
}

class GetLastTradeRes (
        code:Int,
        msg:String,
        val data:List<Trade>
) : Res(code, msg) {
    data class Trade (
            val amount:Float,
            val price:Float,
            val ctime:Float,
            val id:Int,
            val type: TradeType
    )
}