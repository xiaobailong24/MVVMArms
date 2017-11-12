# MVVMArms
<div align="center"><img src="https://github.com/xiaobailong24/MVVMArms/blob/master/image/MVVMArms_Logo.png" width="40%" height="40%" align=center></div>

Android MVVM Architecture Components based on [MVPArms](https://github.com/JessYanCoding/MVPArms) and [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/index.html).

[中文](https://github.com/xiaobailong24/MVVMArms/blob/master/README_CN.md)

> This is **BETA** Version.
Welcome to communicate and discuss.

## Architecture
<div align="center"><img src="https://github.com/xiaobailong24/MVVMArms/raw/master/image/MVVMArms_Architecture.png" width="80%" height="80%" align=center></div>

## Demo
### APK
[MVVMArms APK](https://github.com/xiaobailong24/MVVMArms/blob/master/image/MVVMArms.apk)
### Google Play
<div align="center">
<a href='https://play.google.com/store/apps/details?id=me.xiaobailong24.mvvmarms.weather&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'>
<img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'/></a>
</div>

### Video
[![MVVMArms demo video](https://img.youtube.com/vi/Ja8nHGoWGjo/0.jpg)](https://youtu.be/Ja8nHGoWGjo)

## Libraries & References
- [MVPArms](https://github.com/JessYanCoding/MVPArms)
- [android-architecture-components](https://github.com/googlesamples/android-architecture-components)
- [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/index.html)
- [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle.html)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)
- [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room.html)
- [Dagger](https://github.com/google/dagger)
- [Retrofit](https://github.com/square/retrofit)
- [Okhttp](https://github.com/square/okhttp)
- [Glide](https://github.com/bumptech/glide)
- [Gson](https://github.com/google/gson)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [RxCache](https://github.com/VictorAlbertos/RxCache)
- [Timber](https://github.com/JakeWharton/timber)
- [LeakCanary](https://github.com/square/leakcanary)
- [AndroidEventBus](https://github.com/hehonghui/AndroidEventBus)
- [RxErrorHandler](https://github.com/JessYanCoding/RxErrorHandler)
- [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
- [SuperTextView](https://github.com/lygttpod/SuperTextView)
- [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)
- [Weather API](https://www.seniverse.com)
- etc...

## Update Log
- Release 1.0 - 2017/11/12.
- [RepositoryComponent](https://github.com/xiaobailong24/MVVMArms/blob/master/repository/src/main/java/me/xiaobailong24/mvvmarms/repository/di/component/RepositoryComponent.java) Provides [Cache](https://github.com/xiaobailong24/MVVMArms/blob/master/repository/src/main/java/me/xiaobailong24/mvvmarms/repository/cache/Cache.java) & RxCache. - 2017/10/11.
- [lifecycle](https://github.com/xiaobailong24/MVVMArms/tree/master/lifecycle) & [repository](https://github.com/xiaobailong24/MVVMArms/tree/master/repository). - 2017/10/01.
- [ArmsFragment](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/base/ArmsFragment.java) Support Lazy load. - 2017/09/24.
- [ViewModel&LiveData](https://github.com/xiaobailong24/MVVMArms/blob/master/weather/src/main/java/me/xiaobailong24/mvvmarms/weather/mvvm/viewmodel/WeatherViewModel.java) Activity share data between fragments, and ViewModel retain data across configuration changes. - 2017/09/23.
- [IRepositoryManager](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/repository/IRepositoryManager.java) Support RoomDatabase for customization(GlobalConfigModule.Builder) BUT injectRoomDatabase(DBClass) first in ConfigModule#registerComponents(). - 2017/09/22.
- [ArmsUtils](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/utils/ArmsUtils.java) to obtain [**ArmsComponent**](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/di/component/ArmsComponent.java) easily. - 2017/08/23.
- [ImageLoader](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/http/imageloader/ImageLoader.java) default Glide. - 2017/08/17.
- Survives configuration changes for ViewPager using Fragment. - 2017/08/16
- MVVMArms was BORN!!! - 2017/08/02

## Thanks
Thanks to all the authors and organizations that contributed to the open source library!

## License
```
Copyright 2017 xiaobailong24

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
