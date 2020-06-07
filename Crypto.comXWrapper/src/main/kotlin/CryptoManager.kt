import Exceptions.NotAuthenticatedException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

class CryptoManager {
    var apiKey:String? = null
    var apiSecret:String? = null
    val hasher = MessageDigest.getInstance("SHA-256")

    //Note: Crypto.com requires the params to be sorted while signing
    fun sign(params: SortedMap<String, String>) : String {
        if (apiKey == null || apiSecret == null)
            throw NotAuthenticatedException()
        val unixTime = System.currentTimeMillis()
        params["api_key"] = apiKey
        params["time"] = unixTime.toString()
        var paramstr = mapToStringNoDelimiter(params)
        paramstr += apiSecret
        val digest = getDigest(paramstr)
        params["sign"] = digest
        paramstr = mapToUrlEcondingString(params)
        return paramstr
    }

    fun getDigest(m:String) : String {
        val digestBytes = hasher.digest(m.toByteArray(StandardCharsets.UTF_8))
        return bytesToHex(digestBytes)
    }

    //credit: https://www.baeldung.com/sha-256-hashing-java
    fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuffer()
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }

    fun mapToUrlEcondingString(m: Map<String, String>) : String {
        var paramstr = ""
        m.forEach {
                k, v ->
            paramstr += k + "=" + v + "&"
        }
        return paramstr.subSequence(0, paramstr.length-1) as String
    }

    fun mapToStringNoDelimiter(m: Map<String, String>) : String{
        var paramstr = ""
        m.forEach {
                k, v ->
            paramstr += k + v
        }
        return paramstr
    }
}