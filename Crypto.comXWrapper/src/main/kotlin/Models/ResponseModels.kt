import Models.CoinSymbol
import Models.TradePair

data class GetAllMarketRes(
        val code:String,
        val msg:String,
        val data:List<MarketRes>
)

data class MarketRes (
        val symbol:TradePair,
        val count_coin:CoinSymbol,
        val amount_precision:Int,
        val base_coin:CoinSymbol,
        val price_precision:Int
)