<div align="center">

# ReVanced Patches

**Small, auditable, privacy-focused patches for Android apps.**

[![Build](https://github.com/simnple/revanced-patches/actions/workflows/build.yml/badge.svg)](https://github.com/simnple/revanced-patches/actions/workflows/build.yml)
[![ReVanced Patcher](https://img.shields.io/badge/ReVanced%20Patcher-22.0.0-9b59b6)](https://github.com/ReVanced/revanced-patcher)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

</div>

## About

This repository contains an independent ReVanced patch bundle focused on reducing unnecessary analytics, advertising identifiers, telemetry, and Android permissions.

It is intentionally separate from the official ReVanced patches repository. Each supported app lives in its own package and is constrained by package name and verified version, making the patches easier to review and extend.

> [!IMPORTANT]
> These patches do not bypass authentication, payments, subscriptions, or premium entitlements.

## Supported apps

| App | Package | Verified version | Patch suite | Status |
| --- | --- | --- | --- | --- |
| Goondori (군돌이) | `com.goondori` | `5.6.0` (`214`) | `Goondori Privacy Suite` | Initial support |

More apps can be added under their own directory without importing unrelated official patches.

## Goondori Privacy Suite

The first suite was built from a static review of Goondori 5.6.0 and targets exact class and method signatures found in that release.

### What it changes

- Prevents the Adrop SDK from initializing, stopping its ad request and display path.
- Blocks Adrop UID, user-property, and analytics-event bridges.
- Permanently disables Firebase Analytics collection.
- Blocks Firebase event logging, account linking, user properties, and default event parameters.
- Disables Sentry through manifest metadata.
- Removes advertising ID and AdServices permissions.
- Removes unnecessary overlay, media-location, video, legacy write-storage, and screen-capture detection permissions.

### What it deliberately keeps

- Goondori account authentication and session handling
- Payment and subscription verification
- RevenueCat entitlement checks
- Expo/FCM push notifications
- GraphQL access required by core app features

## Project layout

```text
patches/src/main/kotlin/dev/simnple/revanced/patches/
└── goondori/
    ├── GoondoriPrivacySuitePatch.kt
    ├── DisableAdropMetricsPatch.kt
    ├── DisableFirebaseAnalyticsPatch.kt
    ├── PrivacyManifestPatch.kt
    └── Fingerprints.kt
```

Future apps should receive a separate directory, compatibility declaration, fingerprints, and suite patch.

## Build

### GitHub Actions

Every push to `main` builds the bundle and uploads the resulting `.rvp` file as a workflow artifact.

1. Open the repository's **Actions** tab.
2. Select **Build patch bundle**.
3. Open the latest successful run.
4. Download the `revanced-patches` artifact.

### Local build

Requirements:

- JDK 17 or newer
- A GitHub classic personal access token with `read:packages`

Add the following to `~/.gradle/gradle.properties`:

```properties
gpr.user=YOUR_GITHUB_USERNAME
gpr.key=YOUR_TOKEN_WITH_READ_PACKAGES
githubPackagesUsername=YOUR_GITHUB_USERNAME
githubPackagesPassword=YOUR_TOKEN_WITH_READ_PACKAGES
```

Build the Android patch bundle:

```bash
./gradlew :patches:buildAndroid
```

The `.rvp` bundle is written to `patches/build/libs/`.

## Apply with ReVanced CLI

Extract `com.goondori.apk` from the original XAPK and run:

```bash
java -jar revanced-cli.jar patch \
  -bp revanced-patches.rvp \
  --exclusive \
  -e "Goondori Privacy Suite" \
  -o goondori-privacy-5.6.0.apk \
  com.goondori.apk
```

The original app is distributed as split APKs. A patched base APK is re-signed, so the original splits may also need to be re-signed with the same key. Test installation on a secondary device or work profile before replacing an existing installation.

## Verification

A successful build only proves that the patch bundle compiled. Runtime verification should include:

- Login, session restoration, and logout
- Push registration and notification delivery
- Payment and entitlement restoration
- Widget and image-selection flows
- Absence of Firebase user IDs, military metadata, and events
- Absence of Adrop requests, UID, user properties, and events
- Absence of new Sentry sessions or events
- Confirmation that removed permissions no longer appear in `dumpsys package`

## Known limitations

- The Hermes bundle passes the access token to `console.log` during session initialization. This repository does not rewrite Hermes bytecode because unsafe length-changing edits can corrupt the bundle. Sentry is disabled, but runtime logcat exposure still requires device verification.
- Goondori assembles a full resident registration number for its identity-verification request. Blocking that field would break identity verification, so the patch does not alter it.
- The Adrop library remains physically present in the APK, but its verified React Native initialization entry point is returned immediately. Dynamic testing should confirm that no alternate native initialization path is exercised.
- Compatibility is intentionally pinned to Goondori 5.6.0. New app releases must be reviewed before the version constraint is updated.

## Contributing

Contributions for additional apps are welcome when they include:

1. A narrow package and version constraint
2. Reproducible evidence for every patched method or resource
3. A description of expected feature impact
4. A runtime verification checklist

Avoid speculative fingerprints and broad patches that silently affect unrelated apps.

## License

Licensed under the [MIT License](LICENSE).

ReVanced is a separate project. This repository is not affiliated with or endorsed by the ReVanced project or the developers of supported apps.
