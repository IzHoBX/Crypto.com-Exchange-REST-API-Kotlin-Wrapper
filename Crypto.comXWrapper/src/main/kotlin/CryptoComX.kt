import Exceptions.CryptoComServerResException
import Models.MoshiAdapters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.internal.Util
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CryptoComX {

    companion object {
        val moshi = Moshi.Builder()
            .add(MoshiAdapters())
            .add(KotlinJsonAdapterFactory())
            .build()

        fun getAllMarket() : GetAllMarketRes{
            val resFromServer = HTTPHelper.getHttp(StringConstants.GETALLMARKET_ENDPOINT)
            return jsonStringToObject(GetAllMarketRes::class.java, resFromServer)
        }

        fun <T> jsonStringToObject(className: Class<T>, dataString:String?): T {
            if (dataString != null) {
                val toReturn = moshi.adapter(className).fromJson(dataString)
                if (toReturn== null) {
                    throw CryptoComServerResException()
                } else {
                    return toReturn
                }
            } else {
                throw CryptoComServerResException()
            }
        }


        fun getAllBalances() : GetAllBalanceRes {
            val resFromServer = HTTPHelper.postHttpSigned(StringConstants.GETALLBALANCE_ENDPOINT)
            return jsonStringToObject(GetAllBalanceRes::class.java, resFromServer)
        }

        fun setApiKey(s:String) {
            HTTPHelper.cryptoManager.apiKey = s
        }

        fun setApiSecret(s:String) {
            HTTPHelper.cryptoManager.apiSecret = s
        }
    }
}