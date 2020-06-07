import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


fun main(args: Array<String>) {
    val url = URL(StringConstants.HTTP_ENDPOINT + "/v1/symbols")
    val con: HttpURLConnection = url.openConnection() as HttpURLConnection
    con.setRequestMethod("GET")
    val `in` = BufferedReader(
            InputStreamReader(con.inputStream))
    var inputLine: String?
    while (`in`.readLine().also { inputLine = it } != null) {
        println(inputLine)
    }
    `in`.close()
}