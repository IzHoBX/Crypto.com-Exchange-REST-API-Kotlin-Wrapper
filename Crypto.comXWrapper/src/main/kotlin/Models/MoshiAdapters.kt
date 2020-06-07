package Models

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class MoshiAdapters {
    @ToJson
    fun toJson(cs:CoinSymbol) : String = cs.str

    @FromJson
    fun fromJson(str:String) : CoinSymbol = CoinSymbol.values().find{it.str == str}!!

}

