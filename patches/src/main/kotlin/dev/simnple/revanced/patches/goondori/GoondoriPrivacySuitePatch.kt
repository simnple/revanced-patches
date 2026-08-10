package dev.simnple.revanced.patches.goondori

import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val goondoriPrivacySuitePatch = bytecodePatch(
    name = "Goondori Privacy Suite",
    description = "Applies the tested Firebase Analytics, Adrop Metrics, and manifest privacy patches together.",
) {
    compatibleWith("com.goondori"("5.6.0"))
    dependsOn(
        disableFirebaseAnalyticsPatch,
        disableAdropMetricsPatch,
        privacyManifestPatch,
    )
}
