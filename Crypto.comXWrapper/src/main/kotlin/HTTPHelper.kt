import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

fun Map<String, String>.mapToUrlEcondingString() : String {
    var paramstr = ""
    this.forEach {
        k, v ->
        paramstr += k + "=" + v + "&"
    }
    return paramstr.subSequence(0, paramstr.length-1) as String
}

fun Map<String, String>.mapToStringNoDelimiter() : String{
    var paramstr = ""
    this.forEach {
        k, v ->
        paramstr += k + v
    }
    return paramstr
}

class HTTPHelper {

    companion object {
        val cryptoManager = CryptoManager()

        fun getHttp(endPoint:String) : String? {
            val url = URL(StringConstants.HTTP_ENDPOINT + endPoint)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            con.setRequestMethod("GET")
            val `in` = BufferedReader(
                InputStreamReader(con.inputStream)
            )
            val toReturn = `in`.readLine()
            `in`.close()
            return toReturn
        }

        fun postHttpSigned(endPoint: String, params:Map<String, String> = TreeMap<String, String>()) : String? {
            val url = URL(StringConstants.HTTP_ENDPOINT + endPoint)
            val con: HttpURLConnection = url.openConnection() as HttpURLConnection
            con.setRequestMethod("POST")
            con.setDoOutput(true)
            val out = DataOutputStream(con.outputStream)
            out.writeBytes(cryptoManager.sign(params as SortedMap<String, String>))
            out.flush()
            out.close()
            val `in` = BufferedReader(
                InputStreamReader(con.inputStream)
            )
            val toReturn = `in`.readLine()
            `in`.close()
            return toReturn
        }
    }

}