import Models.CoinSymbol
import Models.TradePair
import java.util.*

open class Res(
        val code: Int,
        val msg:String
)

class GetAllMarketRes(
        code:Int,
        msg:String,
        val data:List<MarketInfo>
): Res(code, msg)

class GetAllBalanceRes (
        code:Int,
        msg:String,
        val data:TotalBalance
):Res(code, msg)

class GetTickerForAllMarketRes (
        code: Int,
        msg:String,
        val data:DateAndTicker
):Res(code, msg)

data class DateAndTicker (
        val date:Long,
        val ticker:List<InProgressTicker>
)

data class InProgressTicker (
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

data class MarketInfo (
        val symbol:TradePair,
        val count_coin:CoinSymbol,
        val amount_precision:Int,
        val base_coin:CoinSymbol,
        val price_precision:Int
)

data class WssSubNewTickerResponse (
        val event_rep:String,
        val channel:String,
        val data:String?,
        val tick : Ticker,
        val ts:Long,
        val status:String
)

data class Ticker (
        val amount:Float,
        val close:Float,
        val high:Float,
        val low:Float,
        val open:Float,
        val rose:Float,
        val vol:Float
)