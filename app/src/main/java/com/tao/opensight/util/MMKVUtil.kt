import android.util.Log
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object MMKVUtil  {

    private val mmkv: MMKV = MMKV.defaultMMKV()

    fun <T> encode(key: String, value: T) {
        Log.d("MMKVUtil", "Encoding key: $key, Value: $value")
        when (value) {
            is Int -> mmkv.encode(key, value)
            is Long -> mmkv.encode(key, value)
            is Float -> mmkv.encode(key, value)
            is Double -> mmkv.encode(key, value)
            is Boolean -> mmkv.encode(key, value)
            is String -> mmkv.encode(key, value)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> decode(key: String, defValue: T): T {
        val result = when (defValue) {
            is Int -> mmkv.decodeInt(key, defValue)
            is Long -> mmkv.decodeLong(key, defValue)
            is Float -> mmkv.decodeFloat(key, defValue)
            is Double -> mmkv.decodeDouble(key, defValue)
            is Boolean -> mmkv.decodeBool(key, defValue)
            is String -> mmkv.decodeString(key, defValue)
            else -> defValue
        }
        Log.d("MMKVUtil", "Decoded key: $key, Result: $result")  // 打印取出的值
        return result as T
    }

}
class MMKVDelegate<T>(private val key: String, private val def: T) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return MMKVUtil.decode(key, def)
    }


    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =MMKVUtil.encode(key, value)
}

