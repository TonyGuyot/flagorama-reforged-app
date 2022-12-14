# FLAGORAMA (reforged)

## Description

Flagorama displays the flags of the different countries of the world, along with some information about the
countries.

## Purpose

This is intended as a playground to experiment with the Jetpack libraries & other Android 
technologies.

This is a new (reforged) version of [Flagorama](https://github.com/TonyGuyot/flagorama-app). The new
version is using Jetpack Compose, Material Design 3 and Dagger/Hilt among other things.

The old version was using the Android view system and is no more maintained.

## Downloading the app

The app is available on the [Google Play Store](https://play.google.com/store/apps/details?id=io.github.tonyguyot.flagorama) 
for download.

## Technology stack

- [x] Kotlin (of course)
- [x] Coroutines for background processing
- [x] Jetpack Compose for the UI
- [x] Compose Navigation for navigation between screens
- [x] Material Design 3 for the styling of the UI
- [x] Jetpack ViewModel and LifeCycle for the architecture
- [x] Coroutine Flows for the communication between components
- [x] Room database for local storage
- [x] Retrofit for network access
- [x] Coil for the download of images
- [x] Dagger/Hilt for dependency injection
- [x] GrGit for the automatic versioning of the app

Next steps:

- [ ] Add some animations with Compose
- [ ] Add logging (Timber, Firebase)

## Technical documentation

The [architecture description](https://github.com/TonyGuyot/flagorama-app/blob/master/doc/architecture.md) 
of the old app is still valid to some extents.

## Copyrights and licenses

The source code is copyrighted by Tony Guyot (verdaroboto@gmail.com) and is released under the 
Apache 2.0 license.

The data for the countries and flags is provided by <https://restcountries.com>. They are using the
Mozilla Public License MPL 2.0. The source code is available on GitLab: 
<https://gitlab.com/amatos/rest-countries>.

