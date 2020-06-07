package Models

import Exceptions.CoinSymbolNotFoundException

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

fun stringToCoinSymbol(str:String) : CoinSymbol{
    var str = str.toLowerCase()
    val x = CoinSymbol.values().find { it.str == str }
    if (x==null) {
        throw CoinSymbolNotFoundException(str)
    }
    return x
}

data class TradePair(val countCoin:CoinSymbol, val baseCoin:CoinSymbol) {
    override fun toString(): String {
        return countCoin.str + baseCoin.str
    }
}