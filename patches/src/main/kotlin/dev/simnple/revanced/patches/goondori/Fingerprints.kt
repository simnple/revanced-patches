package dev.simnple.revanced.patches.goondori

import app.revanced.patcher.fingerprint

private const val FIREBASE_ANALYTICS = "Lcom/google/firebase/analytics/FirebaseAnalytics;"
private const val ADROP_METRICS_MODULE = "Lio/adrop/AdropMetricsModule;"
private const val PAIRIP_LICENSE_CLIENT = "Lcom/pairip/licensecheck/LicenseClient;"

internal fun exactVoidMethodFingerprint(
    classDescriptor: String,
    methodName: String,
    parameterTypes: List<String>,
) = fingerprint {
    custom { method, classDef ->
        classDef.type == classDescriptor &&
            method.name == methodName &&
            method.returnType == "V" &&
            method.parameterTypes.map(CharSequence::toString) == parameterTypes
    }
}

internal val firebaseLogEventFingerprint = exactVoidMethodFingerprint(
    FIREBASE_ANALYTICS,
    "logEvent",
    listOf("Ljava/lang/String;", "Landroid/os/Bundle;"),
)

internal val firebaseSetUserIdFingerprint = exactVoidMethodFingerprint(
    FIREBASE_ANALYTICS,
    "setUserId",
    listOf("Ljava/lang/String;"),
)

internal val firebaseSetUserPropertyFingerprint = exactVoidMethodFingerprint(
    FIREBASE_ANALYTICS,
    "setUserProperty",
    listOf("Ljava/lang/String;", "Ljava/lang/String;"),
)

internal val firebaseSetDefaultEventParametersFingerprint = exactVoidMethodFingerprint(
    FIREBASE_ANALYTICS,
    "setDefaultEventParameters",
    listOf("Landroid/os/Bundle;"),
)

internal val adropSetPropertyFingerprint = exactVoidMethodFingerprint(
    ADROP_METRICS_MODULE,
    "setProperty",
    listOf("Ljava/lang/String;", "Lcom/facebook/react/bridge/ReadableArray;"),
)

internal val adropSetUidFingerprint = exactVoidMethodFingerprint(
    "Lio/adrop/AdropAdsModule;",
    "setUID",
    listOf("Ljava/lang/String;"),
)

internal val adropInitializeFingerprint = exactVoidMethodFingerprint(
    "Lio/adrop/AdropAdsModule;",
    "initialize",
    listOf("Z", "Lcom/facebook/react/bridge/ReadableArray;", "Z"),
)

internal val adropLogEventFingerprint = exactVoidMethodFingerprint(
    ADROP_METRICS_MODULE,
    "logEvent",
    listOf("Ljava/lang/String;", "Lcom/facebook/react/bridge/ReadableMap;"),
)

internal val adropSendEventFingerprint = exactVoidMethodFingerprint(
    ADROP_METRICS_MODULE,
    "sendEvent",
    listOf("Ljava/lang/String;", "Lcom/facebook/react/bridge/ReadableMap;"),
)

internal val pairipLicenseCheckFingerprint = exactVoidMethodFingerprint(
    PAIRIP_LICENSE_CLIENT,
    "checkLicense",
    listOf("Landroid/content/Context;"),
)
