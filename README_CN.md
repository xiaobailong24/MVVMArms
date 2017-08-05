# MVVMArms - MVVM 与 Android Architecture Components 的最佳实战
首先感谢 [JessYan](https://github.com/JessYanCoding) 开源的 [MVPArms](https://github.com/JessYanCoding/MVPArms)，学到了很多知识，也方便了公司项目的开发！
在 Google I/O 2017 大会上，Google 推出了新的组件库 - **Android Architecture Components**，官方文档做的很详细，分别介绍了各个组件的应用场景和使用方式，这无疑会推进 Android MVVM 模式的发展。关于 **MVVM** 这里不做过多介绍，如果有需要可以自行搜索。

# 正文
## Android Architecture Components 简介
先来看一下 Android Architecture Components 包含哪些内容：
- [Handling Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle.html)

- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)

- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)

- [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room.html)

加上之前推出的 Data Binding Library
- [Data Binding Library](https://developer.android.com/topic/libraries/data-binding/index.html)

如果想要引用 Components，可参考以下链接：
- [Adding Components to your Project](https://developer.android.com/topic/libraries/architecture/adding-components.html)

> 注意：如果你无法访问 Google Maven 库，可以使用以下地址代替：    
> **maven { url "https://dl.google.com/dl/android/maven2" }**

这里需要特别说明的是，**ViewModel** 相当于 MVVM 的 VM 层，它和 View 是通过 Data Binding 双向绑定的，ViewModel 不再持有 View 的引用，而是存储UI相关的数据；而且 ViewModel 是生命周期感知的，当创建它的 Activity/Fragment 销毁时，ViewModel 会自动销毁。

## MVVMArms 框架图
这里引用 [官方](https://developer.android.com/topic/libraries/architecture/guide.html#the_final_architecture) 架构图，参考 MVPArms 做了一些修改。
![MVVMArms _Architecture](https://github.com/xiaobailong24/MVVMArms/raw/master/image/MVVMArms_Architecture.png)

整个框架采用 **Dagger** 进行依赖注入，并使用了最新的 **Dagger.Android**，组织好 **Module** 和 **Components**，框架会自动注入。具体可参考 demo。

## MVVM 组件的封装
### Model
框架中，数据的获取是通过 **Model** 层获取的，在 Model 中持有一个数据管理类对象- **RepositoryManager**（实现了 IRepositoryManager 接口），分别可以通过 **Retrofit**
获取网络数据，通过 **Room** 获取 SQLite 数据库中的持久化数据。具体实现可查看源码链接：
- [BaseModel](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/mvvm/BaseModel.java)
- [RepositoryManager](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/repository/RepositoryManager.java)

Room 的使用需要在 gradle 中添加：
```
android {
...

    //Room
    javaCompileOptions {
        annotationProcessorOptions {
            arguments = ["room.schemaLocation":
                                 "$projectDir/schemas".toString()]
        }
    }
}
```

### ViewModel
ViewModel 是整个框架比较新颖和重要的部分，**BaseViewModel** 继承于 Components 中的 **AndroidViewModel**，并实现了 **LifecycleObserver** 接口；它持有 Model 的引用，主要的业务逻辑都是在 ViewModel 中实现的；ViewModel 中UI相关的数据通过 **LiveData** 包装，使得当数据变化时，自动反映到UI上，实现了数据UI。在 xml 布局文件中，通过 Data Binding 绑定对应的 ViewModel。 BaseViewModel 源码如下：
- [BaseViewModel](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/mvvm/BaseViewModel.java)

### View
框架封装了 **ArmsActivity** 和 **ArmsFragment**，它们都实现了 Components 中的 **LifecycleRegistryOwner** 接口来处理生命周期。上面提到在 xml 布局中绑定 ViewModel，而 ViewModel 的创建和生命周期绑定是在 Activity/Fragment 中进行的，具体是通过 Components 中提供的 **ViewModelProviders** 类完成的。下面是创建 ViewModel 的实例：
```java
//创建ViewModel
mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WeatherViewModel.class);
```
> - mViewModel 是通过泛型封装在 **ArmsActivity** 和 **ArmsFragment** 中的；
- 同样，mViewModelFactory 是通过 Dagger 将 **ViewModelFactory** 注入在 **ArmsActivity** 和 **ArmsFragment** 中的。
- ViewModelProviders.of() 是一个重载的方法，通过传入的第一个参数，限定该 ViewModel 与 哪个 Activity/Fragment 进行生命周期绑定。

创建好了 ViewModel 后，在 **ArmsActivity** 和 **ArmsFragment** 设置 ViewModel 监听 Lifecyle 的回调：
```java
if (mViewModel != null)
    getLifecycle().addObserver((LifecycleObserver) mViewModel);
```

同时，需要将上述提到的 xml 中绑定的 ViewModel 进行设置，这就很简单了，示例代码如下：
```java
mBinding.setViewModel(mViewModel);
```
源码链接：
- [ArmsActivity](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/base/ArmsActivity.java)
- [ArmsFragment](https://github.com/xiaobailong24/MVVMArms/blob/master/arms/src/main/java/me/xiaobailong24/mvvmarms/base/ArmsFragment.java)


# Github
更多细节请移步 Github，写了一个简单的 demo，欢迎 star、fork，issue、pr，希望可以一起交流：
- [MVVMArms](https://github.com/xiaobailong24/MVVMArms)

# 参考
- [MVPArms](https://github.com/JessYanCoding/MVPArms)
- [android-architecture-components](https://github.com/googlesamples/android-architecture-components)
