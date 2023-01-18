package fr.chrgar.android.techtest.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TechTestApplication : Application(), ImageLoaderFactory {
    companion object {
        @JvmStatic
        lateinit var instance: TechTestApplication
            private set
    }

    override fun newImageLoader() = ImageLoader
        .Builder(this)
        .respectCacheHeaders(false)
        .memoryCache {
            MemoryCache.Builder(this)
                .maxSizePercent(0.25)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(cacheDir.resolve("img_cache"))
                .maxSizeBytes(15 * 1024 * 1024)
                .build()
        }
        .build()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
