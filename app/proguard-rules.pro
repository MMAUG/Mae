# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/poepoe/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose

#Google play services proguard rules:
 -keep class * extends java.util.ListResourceBundle {
     protected Object[][] getContents();
 }

 -keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
     public static final *** NULL;
 }

 # The -optimizations option disables some arithmetic simplifications that Dalvik 1.0 and 1.5 can't handle.
 #-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!method/propagation/parameter
 -keepattributes SourceFile,LineNumberTable,InnerClasses

 -keepnames @com.google.android.gms.common.annotation.KeepName class *
 -keepclassmembernames class * {
     @com.google.android.gms.common.annotation.KeepName *;
 }

 -keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
 }
 -keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
     public static final *** NULL;
 }

 ## because google play service gets stripped of unnessecary methods and code dont warn
 -dontwarn com.google.android.gms.**

 # Ignore overridden Android classes
 -dontwarn android.**
 -keep class android.**

 # Ignore imported library references
 -dontwarn android.support.v4.**
 -keep class android.support.v4.** { *; }

 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.preference.Preference

 -keepclasseswithmembernames class * {
     native <methods>;
 }

 # Keep all public constructors of all public classes, but still obfuscate+optimize their content.
 # This is necessary because optimization removes constructors which are called through XML.
 -keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet, int);
     public <init>(android.content.Context, android.util.AttributeSet);
     public <init>(android.content.Context);
 }
 -keepclassmembers class * extends android.app.Activity {
     public void *(android.view.View);
 }
 -keepclassmembers class **.R$* {
     public static <fields>;
 }

 # Keep serializable objects
 -keepclassmembers class * implements java.io.Serializable {
     private static final java.io.ObjectStreamField[] serialPersistentFields;
     private void writeObject(java.io.ObjectOutputStream);
     private void readObject(java.io.ObjectInputStream);
     java.lang.Object writeReplace();
     java.lang.Object readResolve();
 }

 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }

 -keep class * implements android.os.Parcelable {
     public static final android.os.Parcelable$Creator *;
 }

 -keep class * extends java.util.ListResourceBundle {
     protected Object[][] getContents();
 }

 ##########################
 ## SECURITY             ##
 ##########################

 # Remove VERBOSE and DEBUG level log statements
 -assumenosideeffects class android.util.Log {
     public static *** i(...);
     public static *** d(...);
     public static *** v(...);
     public static *** e(...);
     public static *** w(...);
     public static *** wtf(...);
 }

 ##########################
 ## SUPPORT LIBRARIES    ##
 ##########################

 # support-v4
 -dontwarn android.support.v4.**
 -keep class android.support.v4.app.** { *; }
 -keep interface android.support.v4.app.** { *; }

 # support-v7
 -dontwarn android.support.v7.**
 -keep class android.support.v7.internal.** { *; }
 -keep interface android.support.v7.internal.** { *; }
 -keep class android.support.v7.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable
-keepattributes EnclosingMethod
-dontwarn com.google.common.**
-keep class android.support.v7.widget.SearchView { *; }
-keep class android.support.v7.internal.widget.ToolbarWidgetWrapper { *; }

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

## GSON 2.2.4 specific rules ##

# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keepattributes EnclosingMethod

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }


## Google Analytics 3.0 specific rules ##

-keep class com.google.analytics.** { *; }

# Crashlytics 1.+

-keep class com.crashlytics.** { *; }
-keepattributes SourceFile,LineNumberTable
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# support design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**
# retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# Keep the annotations
-keepattributes *Annotation*

# glide transformation
-keep class jp.co.cyberagent.** { *; }
-dontwarn jp.wasabeef.glide.transformations.**
