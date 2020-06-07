import Models.CoinSymbol
import Models.TradePair

data class GetAllMarketRes(
        val code:String,
        val msg:String,
        val data:List<MarketRes>
)

data class GetAllBalanceRes (
        val code:String,
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