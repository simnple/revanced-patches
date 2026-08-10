<div align="center">

# ReVanced Patches

**Focused patches for supported Android apps.**

[![Build](https://github.com/simnple/revanced-patches/actions/workflows/build.yml/badge.svg)](https://github.com/simnple/revanced-patches/actions/workflows/build.yml)
[![Release](https://img.shields.io/github/v/release/simnple/revanced-patches)](https://github.com/simnple/revanced-patches/releases/latest)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

</div>

## Supported apps

| App | Package | Version |
| --- | --- | --- |
| Goondori (군돌이) | `com.goondori` | `5.6.0` (`214`) |

## Goondori (군돌이)

| Patch | Function |
| --- | --- |
| `Disable Adrop Ads` | Stops the Adrop advertising SDK from initializing and receiving a user ID. |
| `Disable Adrop Metrics` | Blocks Adrop user properties and analytics events. |
| `Disable Firebase Analytics` | Blocks Firebase events, user IDs, user properties, and default event parameters. |
| `Disable Firebase Startup Collection` | Disables Firebase Analytics collection and advertising-personalization signals at startup. |
| `Disable Sentry` | Disables Sentry error and performance telemetry. |
| `Remove Unnecessary Permissions` | Removes advertising, overlay, video, media-location, legacy storage, and screen-capture detection permissions. |

## Use

ReVanced Manager patches source:

```text
https://github.com/simnple/revanced-patches
```

Direct bundle download:

```text
https://github.com/simnple/revanced-patches/releases/latest/download/patches-1.1.0.rvp
```

## License

[MIT](LICENSE)
