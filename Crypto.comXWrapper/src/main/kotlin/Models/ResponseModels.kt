

data class GetAllMarketRes(
        val code:String,
        val msg:String,
        val data:List<MarketRes>
)

data class MarketRes (
        val symbol:String,
        val count_coin:String,
        val amount_precision:Int,
        val base_coin:String
)