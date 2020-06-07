import Models.CoinSymbol
import Models.TradePair

data class GetAllMarketRes(
        val code:Int,
        val msg:String,
        val data:List<MarketRes>
)

data class GetAllBalanceRes (
        val code:Int,
        val msg:String,
        val data:TotalBalance
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

data class MarketRes (
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