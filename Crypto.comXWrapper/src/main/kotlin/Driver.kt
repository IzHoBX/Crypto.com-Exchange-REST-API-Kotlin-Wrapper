import Models.MoshiAdapters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


fun main(args: Array<String>) {
    println(CrytoComX.getAllMarket())
}