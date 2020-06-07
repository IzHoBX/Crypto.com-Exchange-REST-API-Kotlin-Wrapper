import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HTTPHelper {

    companion object {
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
    }

}