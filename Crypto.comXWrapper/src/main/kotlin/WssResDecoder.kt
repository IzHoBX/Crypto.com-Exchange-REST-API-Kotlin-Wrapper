import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.CharBuffer
import java.nio.charset.StandardCharsets
import java.util.zip.GZIPInputStream

class WssResDecoder {
    companion object{
        val pingResRegex = Regex("\\{\"ping\":[0-9]*\\}")

        fun byteBufferToString(buffer: ByteBuffer): String? {
            val charBuffer: CharBuffer
            return try {
                val charset = StandardCharsets.ISO_8859_1
                val decoder = charset.newDecoder()
                charBuffer = decoder.decode(buffer)
                buffer.flip()
                charBuffer.toString()
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
        }

        fun uncompress(str:String?): String? {
            if (str == null || str.length == 0) {
                return str
            }
            val out = ByteArrayOutputStream()
            val `in` = ByteArrayInputStream(str.toByteArray(charset("ISO-8859-1")))
            val gunzip = GZIPInputStream(`in`)
            val buffer = ByteArray(256)
            var n: Int
            while (gunzip.read(buffer).also { n = it } >= 0) {
                out.write(buffer, 0, n)
            }
            return out.toString()
        }

        fun webSocketMessageEventToString(wse:WebSocket.Event.OnMessageReceived) : String? {
            val x = ByteBuffer.wrap((wse.message as Message.Bytes).component1())
            val y = WssResDecoder.byteBufferToString(x)
            return uncompress(y)
        }

        fun isPingResponse(str:String) : Boolean {
            return pingResRegex.matches(str as CharSequence)
        }
    }
}