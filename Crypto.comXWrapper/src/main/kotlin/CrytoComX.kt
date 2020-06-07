import Exceptions.CryptoComServerResException
import Models.MoshiAdapters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CrytoComX {

    companion object {
        fun getAllMarket() : GetAllMarketRes{
            val resFromServer = HTTPHelper.getHttp(StringConstants.GETALLMARKET_ENDPOINT)
            if (resFromServer != null) {
                val moshi = Moshi.Builder()
                    .add(MoshiAdapters())
                    .add(KotlinJsonAdapterFactory())
                    .build()
                val toReturn = moshi.adapter(GetAllMarketRes::class.java).fromJson(resFromServer)
                if (toReturn== null) {
                    throw CryptoComServerResException()
                } else {
                    return toReturn
                }
            } else {
                throw CryptoComServerResException()
            }
        }
    }
}