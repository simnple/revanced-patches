package dev.simnple.revanced.patches.goondori

import app.revanced.patcher.patch.resourcePatch
import org.w3c.dom.Element

private const val ANDROID_NAME = "android:name"
private const val ANDROID_VALUE = "android:value"

private val removedPermissions = setOf(
    "android.permission.ACCESS_MEDIA_LOCATION",
    "android.permission.READ_MEDIA_VIDEO",
    "android.permission.SYSTEM_ALERT_WINDOW",
    "android.permission.WRITE_EXTERNAL_STORAGE",
    "android.permission.DETECT_SCREEN_CAPTURE",
    "com.google.android.gms.permission.AD_ID",
    "android.permission.ACCESS_ADSERVICES_AD_ID",
    "android.permission.ACCESS_ADSERVICES_ATTRIBUTION",
    "android.permission.ACCESS_ADSERVICES_TOPICS",
)

private fun Element.setMetaData(name: String, value: String) {
    val nodes = getElementsByTagName("meta-data")
    val existing = (0 until nodes.length)
        .mapNotNull { nodes.item(it) as? Element }
        .firstOrNull { it.getAttribute(ANDROID_NAME) == name }

    (existing ?: ownerDocument.createElement("meta-data").also { appendChild(it) }).apply {
        setAttribute(ANDROID_NAME, name)
        setAttribute(ANDROID_VALUE, value)
    }
}

@Suppress("unused")
val privacyManifestPatch = resourcePatch(
    name = "Privacy Manifest",
    description = "Disables Firebase and Sentry startup collection and removes advertising, overlay, video/location metadata, and screen-capture detection permissions.",
) {
    compatibleWith("com.goondori"("5.6.0"))

    apply {
        document("AndroidManifest.xml").use { document ->
            val manifest = document.documentElement
            val application = document.getElementsByTagName("application").item(0) as Element

            application.setMetaData("firebase_analytics_collection_enabled", "false")
            application.setMetaData("firebase_analytics_collection_deactivated", "true")
            application.setMetaData("google_analytics_adid_collection_enabled", "false")
            application.setMetaData("google_analytics_default_allow_ad_personalization_signals", "false")
            application.setMetaData("io.sentry.enabled", "false")
            application.setMetaData("io.sentry.dsn", "")

            val permissionNodes = document.getElementsByTagName("uses-permission")
            (permissionNodes.length - 1 downTo 0).forEach { index ->
                val permission = permissionNodes.item(index) as? Element ?: return@forEach
                if (permission.getAttribute(ANDROID_NAME) in removedPermissions) {
                    manifest.removeChild(permission)
                }
            }

            application.removeAttribute("android:requestLegacyExternalStorage")
        }
    }
}
