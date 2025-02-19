# Keep all @Serializable classes
-keep @kotlinx.serialization.Serializable class ** { *; }

# Keep the generated serializers
-keep class kotlinx.serialization.internal.** { *; }
-keep class kotlinx.serialization.json.** { *; }
-keepnames class kotlinx.serialization.** { *; }

# Prevent obfuscation of enum values
-keepclassmembers enum * { *; }

# Retain classes with reflective annotations
-keepclassmembers class ** {
    @kotlinx.serialization.* <fields>;
    @kotlinx.serialization.* <methods>;
}

# Keep all kotlinx serialization-related classes and methods
-keep class kotlinx.serialization.** { *; }