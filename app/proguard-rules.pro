# Room generates its implementations reflectively at runtime.
-keep class * extends androidx.room.RoomDatabase { <init>(); }
-dontwarn androidx.room.paging.**

# Hilt / Dagger generated components.
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }