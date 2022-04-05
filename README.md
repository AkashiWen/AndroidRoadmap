# AndroidRoadmap

## 1. glide

### 1.1 生命周期感知

``Glide.with(context)``

利用context创建空Fragment与Activity绑定，再利用接口回调给RequestManager

### 1.2 三级缓存

``Engine.into(imageView)``

- WeakReference<Bitmap>: ``ActiveCache.kt``
  > 弱引用回收在一个阻塞的Thread中处理（ReferenceQueue）
- Lru cache: ``MemoryCache.kt``
- File cache: ``DiskLruCacheImpl.kt``

## 2. okhttp

## 3. 隔离层设计 - 代理模式和hilt

### 3.1 代理模式

- 抽象网络请求框架，不关心具体第三方请求SDK（Okhttp、Volley等）

**代理模式角色**

- 公共接口：Subject
- 代理角色：Proxy
- 源角色：RealSubject
- Client: Activity、Fragment等

> Proxy将会持有RealSubject对象
> Client调用Proxy，不关心具体RealSubject

**回调接口**

- ``ICallback.kt``
- 默认抽象实现处理json转换``HttpCallback.kt``


**Subject:**
``IHttpProcessor.kt``

**Proxy:**
``HttpProxy.kt``

**RealSubject**

- ``OkhttpProcessor.kt``
- ``VolleyProcessor.kt``

**Client**
``SubjectProxyActivity.kt``