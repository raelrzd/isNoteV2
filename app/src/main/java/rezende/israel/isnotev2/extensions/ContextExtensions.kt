package rezende.israel.isnotev2.extensions

import android.content.Context
import android.content.Intent

fun Context.vaiPara(
    clazz: Class<*>,
    intent: Intent.() -> Unit = {}
) {
    Intent(this, clazz).apply {
        intent()
        startActivity(this)
    }
}