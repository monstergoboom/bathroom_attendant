# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Bathroom Attendant is a Kotlin LibGDX game about managing a public bathroom. Multi-platform project supporting desktop (LWJGL3) and Android.

## Build Commands

```bash
# Build
./gradlew build                    # Build all modules
./gradlew :core:build              # Build core module only
./gradlew clean build              # Clean rebuild

# Run desktop
./gradlew :desktop:run             # Run desktop client

# Package
./gradlew :desktop:dist            # Create standalone desktop JAR

# Android
./gradlew :android:assembleDebug   # Build debug APK
./gradlew :android:installDebug    # Install to connected device

# Code generation (Koin KSP)
./gradlew :core:kspKotlin          # Regenerate Koin modules

# Testing
./gradlew test                     # Run all tests
./gradlew :core:test               # Run core module tests only

# Checks
./gradlew lint                     # Android lint
./gradlew check                    # All checks
```

## Architecture

### Module Structure
- **core** - Shared game logic, entities, services
- **desktop** - LWJGL3 desktop client with ConsoleRenderer
- **android** - Android mobile client

### Dependency Injection (Koin)
Uses Koin with KSP for compile-time code generation:
- `AppModule.java` defines the DI module with `@ComponentScan("com.monstergoboom.game")`
- Singletons use `@Single` annotation (ManagedSpriteBatch, ManagedTexture, DataService, etc.)
- Classes implement `KoinComponent` and use `by inject()` for dependencies
- Generated code outputs to `/core/build/generated/ksp/`

### Key Patterns
- **Game Lifecycle**: `GameApplication` extends LibGDX `ApplicationAdapter`
- **Services**: `DataService` (SQLite), `CurrencyService` (formatting), `RenderService` (interface)
- **Resources**: `Resource` interface with `suspend fun load()` for coroutine-based async loading
- **Entities**: Game objects implement composition of interfaces (Playable, Identifiable, Purchasable, etc.)

### Data Layer
- **Protocol Buffers** for serialization (`/core/src/protos/`)
  - ItemData, Currency, DataBlock, ConversationData
  - Generated Java package: `com.monstergoboom.game.models`
- **SQLite** via JDBC for persistence (DataService)

### Entry Points
- Desktop: `desktop/.../DesktopLauncher.kt` - initializes Koin then LibGDX
- Android: `android/.../AndroidLauncher.kt`

## Tech Stack
- Kotlin 1.9.21, Java 17
- LibGDX 1.12.1 (game framework)
- Koin 3.5.3 + KSP 1.3.0 (dependency injection)
- Protocol Buffers 3.25.0 (serialization)
- SQLite JDBC 3.45.1.0 (database)
- Kotlinx Coroutines 1.7.3 (async)
