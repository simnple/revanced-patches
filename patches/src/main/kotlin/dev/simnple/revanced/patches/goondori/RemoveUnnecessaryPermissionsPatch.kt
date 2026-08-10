package dev.simnple.revanced.patches.goondori

import app.revanced.patcher.patch.resourcePatch
import org.w3c.dom.Element

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

@Suppress("unused")
val removeUnnecessaryPermissionsPatch = resourcePatch(
    name = "Remove Unnecessary Permissions",
    description = "Removes advertising, overlay, video, media-location, legacy storage, and screen-capture detection permissions.",
) {
    compatibleWith("com.goondori"("5.6.0"))

    apply {
        document("AndroidManifest.xml").use { document ->
            val manifest = document.documentElement
            val application = document.getElementsByTagName("application").item(0) as Element
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
