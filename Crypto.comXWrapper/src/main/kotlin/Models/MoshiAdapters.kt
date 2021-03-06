package Models

import Exceptions.CoinSymbolNotFoundException
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class MoshiAdapters {
    @ToJson
    fun coinSymbolToJson(cs:CoinSymbol) : String = cs.str

    @FromJson
    fun coinSymbolfromJson(str:String) : CoinSymbol {
        return stringToCoinSymbol(str)
    }

    @ToJson
    fun tradePairToJson(tradePair:TradePair) : String {
        return tradePair.baseCoin.str + tradePair.countCoin.str
    }

    @FromJson
    fun tradePairFromJson(str:String) : TradePair {
        var cs1:CoinSymbol
        var cs2:CoinSymbol
        var cs1Is3Char:Boolean
        try {
            cs1 = stringToCoinSymbol(str.substring(0, 3))
            cs1Is3Char = true
        } catch (e:CoinSymbolNotFoundException) {
            cs1Is3Char = false
            cs1 = stringToCoinSymbol(str.substring(0, 4))
            cs2 = stringToCoinSymbol(str.substring(4))
            return TradePair(cs1, cs2)
        }
        return TradePair(cs1, stringToCoinSymbol(str.substring(3)))
    }

    @FromJson
    fun periodFromJson(str:String) : Period {
        return intToPeriod(str.toInt())
    }

    @ToJson
    fun periodToJson(p:Period) : String {
        return p.inMin.toString()
    }

    @FromJson
    fun klineTickerFromJson(l:List<Float>) : GetKLineOverPeriodRes.Ticker {
        return GetKLineOverPeriodRes.Ticker(l[0].toLong(), l[1], l[2], l[3], l[4], l[5])
    }

    @ToJson
    fun klineTickerToJson(t:GetKLineOverPeriodRes.Ticker) : List<String> {
        return t.asListOfString()
    }

    @FromJson
    fun tradeTypeFromJson(s:String) : TradeType = stringToTradeType(s)

    @ToJson
    fun tradeTypeToJson(t:TradeType) : String = t.str

}

