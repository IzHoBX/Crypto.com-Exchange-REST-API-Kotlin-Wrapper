package Models

import Exceptions.CoinSymbolNotFoundException
import Exceptions.PeriodNotFoundException

enum class CoinSymbol(val str:String) {
    CRO("cro"),
    BTC("btc"),
    MCO("mco"),
    ETH("eth"),
    XRP("xrp"),
    LTC("ltc"),
    EOS("eos"),
    XLM("xlm"),
    ATOM("atom"),
    LINK("link"),
    XTZ("xtz"),
    BCH("bch"),
    VET("vet"),
    ICX("icx"),
    USDT("usdt"),
    USDC("usdc"),
    ADA("ada")
}

enum class Period(val inMin:Int) {
    MIN1(1),
    MIN5(5),
    MIN15(15),
    MIN30(30),
    HOUR(60),
    DAY(1440),
    WEEK(10080),
    MONTH(43200)
}

enum class TradeType(val str:String) {
    SELL("sell"),
    BUY("buy")
}

fun stringToTradeType(str:String) : TradeType{
    var str = str.toLowerCase()
    val x = TradeType.values().find { it.str == str }
    if (x==null) {
        throw CoinSymbolNotFoundException(str)
    }
    return x
}

fun stringToCoinSymbol(str:String) : CoinSymbol{
    var str = str.toLowerCase()
    val x = CoinSymbol.values().find { it.str == str }
    if (x==null) {
        throw CoinSymbolNotFoundException(str)
    }
    return x
}

fun intToPeriod(i:Int) : Period {
    val x = Period.values().find {it.inMin == i}
    if (x==null) {
        throw PeriodNotFoundException(i)
    }
    return x
}

data class TradePair(val countCoin:CoinSymbol, val baseCoin:CoinSymbol) {
    override fun toString(): String {
        return countCoin.str + baseCoin.str
    }
}