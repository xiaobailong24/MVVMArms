# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\ProgramFiles\Android\AS_sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# http://www.jianshu.com/p/0aa044263d4d
# https://www.diycode.cc/topics/380
# 在prguard-rules.pro文件中写的，其实就是混淆规则，规定哪些东西不需要混淆。
# 自己编写的代码中大致就是一些重要的类需要混淆，而混淆的本质就是精简类名，用简单的a,b,c等单词来代替之前写的如DataUtil等易懂的类名。
# 所以，理解了这点，也就好理解这个混淆文件该怎么写了，大致思路就是：
# 不混淆第三方库，不混淆系统组件，一般也可以不混淆Bean等模型类，因为这些对别人都是没用的，毕竟都是开源的。。。

-optimizationpasses 5 #指定代码压缩级别
-dontusemixedcaseclassnames #混淆时不会产生形形色色的类名
-dontskipnonpubliclibraryclasses #指定不忽略非公共类库
-dontskipnonpubliclibraryclassmembers #指定不忽略非公共类库成员
-dontpreverify  #不预校验，如果需要预校验，是-dontoptimize
-ignorewarnings #屏蔽警告
-verbose #混淆时记录日志
-printmapping priguardMapping.txt
-optimizations !code/simplification/artithmetic,!field/*,!class/merging/* #优化
# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification


################common###############

-keep public class * implements me.xiaobailong24.mvvmarms.repository.ConfigModule

 #实体类不参与混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepnames class * implements java.io.Serializable
-keepattributes Signature
-keep class **.R$* {*;} #过滤R文件的混淆
-ignorewarnings
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * { # 保持native方法不被混淆
    native <methods>;
}

-keepclassmembers enum * {  # 使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用，
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


################support###############
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**


################annotation###############
-keep class android.support.annotation.** { *; }
-keep interface android.support.annotation.** { *; }


################retrofit###############
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions


################okhttp###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**


################gson###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
#-keep class com.sunloto.shandong.bean.** { *; }


################androidEventBus###############
-keep class org.simple.** { *; }
-keep interface org.simple.** { *; }
-keepclassmembers class * {
    @org.simple.eventbus.Subscriber <methods>;
}
-keepattributes *Annotation*


################Rxjava and RxAndroid###############
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**

-keep class io.reactivex.** { *; }
-keep interface io.reactivex.** { *; }

-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-dontwarn io.reactivex.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep class sun.misc.Unsafe { *; }

-dontwarn java.lang.invoke.*

-keep class io.reactivex.schedulers.Schedulers {
    public static <methods>;
}
-keep class io.reactivex.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.TestScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class io.reactivex.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontwarn io.reactivex.internal.util.unsafe.**


################RxErrorHandler#################
 -keep class me.jessyan.rxerrorhandler.** { *; }
 -keep interface me.jessyan.rxerrorhandler.** { *; }


################espresso###############
-keep class android.support.test.espresso.** { *; }
-keep interface android.support.test.espresso.** { *; }

################Timber#################
-dontwarn org.jetbrains.annotations.**


################Canary#################
-dontwarn com.squareup.haha.guava.**
-dontwarn com.squareup.haha.perflib.**
-dontwarn com.squareup.haha.trove.**
-dontwarn com.squareup.leakcanary.**
-keep class com.squareup.haha.** { *; }
-keep class com.squareup.leakcanary.** { *; }