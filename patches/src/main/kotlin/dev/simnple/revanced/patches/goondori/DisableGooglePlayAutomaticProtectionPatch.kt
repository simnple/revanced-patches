package dev.simnple.revanced.patches.goondori

import app.revanced.patcher.extensions.addInstruction
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val disableGooglePlayAutomaticProtectionPatch = bytecodePatch(
    name = "Disable Google Play Automatic Protection",
    description = "Prevents Google Play PairIP from blocking Goondori when it was installed outside Google Play.",
) {
    compatibleWith("com.goondori"("5.6.0"))

    apply {
        pairipLicenseCheckFingerprint.method.addInstruction(0, "return-void")
    }
}
