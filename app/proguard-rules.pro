# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

-dontwarn android.view.RenderNode
-dontwarn android.view.DisplayListCanvas
-dontwarn android.view.HardwareCanvas


-keep class com.google.android.gms.** { *; }
-keep class com.google.firebase.** { *; }

-keep class com.google.googlesignin.** { *; }

-keepnames class com.google.googlesignin.* { *; }

-keep class com.google.android.gms.auth.** { *; }

-keep class com.google.* {*;}
-keep class com.google.impl.* {*;}
-keep class com.google.firebase.* {*;}
-keep class com.google.googlesignin.** { *; }
-keepnames class com.google.googlesignin.* { *; }
-keep class com.google.gms.** {*;}
-keep class com.google.android.gms.auth.** { *; }
-keep class com.google.android.* {*;}

-keepclassmembers class androidx.compose.ui.platform.ViewLayerContainer {
    protected void dispatchGetDisplayList();
}

-keepclassmembers class androidx.compose.ui.platform.AndroidComposeView {
    android.view.View findViewByAccessibilityIdTraversal(int);
}

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keep,allowshrinking class * extends androidx.compose.ui.node.ModifierNodeElement

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable
-keep,allowshrinking,allowobfuscation class androidx.compose.**.* {
    static void throw*Exception(...);
    # For methods returning Nothing
    static java.lang.Void throw*Exception(...);
}
# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
