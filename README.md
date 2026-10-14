# Aegis

An Android password manager built in Kotlin with Jetpack Compose.

The interface was designed screen by screen in [pen.dev](https://pen.dev) and
implemented here in Compose, screen for screen: onboarding, master-password
setup, unlock, the password list, category browsing, item detail, the add-item
form, the generator, the security audit, settings and search.

## Screens

| Screen | Route |
| --- | --- |
| Onboarding | `onboarding` |
| Create master password | `create_master` |
| Unlock | `unlock` |
| Passwords | `passwords` |
| Logins category | `category/{title}` |
| Login detail | `login/{id}` |
| New item | `item/new` |
| Generator | `generator` |
| Security | `security` |
| Settings | `settings` |
| Search | `search` |

## Stack

- Kotlin 2.0.20, Jetpack Compose (BOM 2024.10.01), Material 3
- Hilt for dependency injection
- Room for persistence
- Navigation Compose
- `androidx.biometric` for the unlock prompt
- AGP 8.5.2, Gradle 8.7, JDK 17, compileSdk 35, minSdk 27

## Security status: demo build

This build is a **demo**. The vault is a plain local Room database and nothing
is encrypted at rest. Do not put real credentials in it.

The seam is already in place. Every secret read or written goes through
`CryptoBox`:

```
app/src/main/java/com/aegis/manager/data/crypto/CryptoBox.kt
```

The current binding is `PlaintextCryptoBox`, an identity transform, supplied in
`di/AppModule.kt`. Swapping in a real AES-GCM + AndroidKeyStore implementation
and rebinding it changes no call site.

Planned next: a key-encryption key derived from the master password via PBKDF2
that wraps a randomly generated data-encryption key, so the master password can
be changed without re-encrypting the vault, plus a Keystore-protected copy for
biometric unlock.

## Security audit

The Security screen does not display hard-coded numbers. `SecurityAuditor` runs
over whatever is actually in the vault and derives the score, the weak/reused/
missing-2FA/breached counts and the lists underneath them.

## Building

There is no Gradle wrapper checked in; CI installs Gradle 8.7 directly.

```bash
gradle testDebugUnitTest
gradle assembleDebug
```

The APK lands at `app/build/outputs/apk/debug/app-debug.apk`.

## CI

`.github/workflows/android.yml` runs on every push to `main`. It runs the unit
tests (which gate the build), lint (advisory only), assembles the debug APK, and
publishes it to the releases page as a rolling **pre-release** on the `debug`
tag. There is no artifact upload step: the APK is attached straight to the
release so it can be downloaded from a phone without signing keys.

## Made with opencode

Generated with [opencode](https://opencode.ai) using the **space-bunny** model.

## Licence

MIT. Bundled fonts are separately licensed - see `licenses/`.
