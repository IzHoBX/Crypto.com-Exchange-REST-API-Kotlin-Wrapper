import Exceptions.CryptoComServerResException
import Models.MoshiAdapters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlin.reflect.jvm.internal.impl.resolve.jvm.JvmClassName

class MoshiHelper {
    companion object {
        private val moshi = Moshi.Builder()
                .add(MoshiAdapters())
                .add(KotlinJsonAdapterFactory())
                .build()

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

        fun <T:Any> objectToJsonString(o:T) : String = (moshi.adapter(o::class.java) as JsonAdapter<T>).toJson(o)

        fun getInstance() : Moshi = moshi
    }
}