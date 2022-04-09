# Retrofit和它的注解

## 1. Retrofit

```kotlin
val retrofit = Retrofit.Builder().baseUrl("").build()
```

**1.1 封装okhttp**

- 在建造者中创建OkHttpClient对象
- 在建造者中创建回调线程池

**okhttp存在的问题：**

- 返回值在子线程，不能渲染UI，必须使用handler通信

**Retrofit如何解决：**

- 使用回调线程池MainThreadExecutor

```java
Executor callbackExecutor = this.callbackExecutor;  
if (callbackExecutor == null) {  
  // 获得MainThreadExecutor
  callbackExecutor = platform.defaultCallbackExecutor();  
}
```
> platform.defaultCallbackExecutor();  动态绑定到AndroidPlatform从而创建MainThreadExecutor对象

```java
static final class MainThreadExecutor implements Executor {  
  private final Handler handler = new Handler(Looper.getMainLooper());  
  
  @Override  
  public void execute(Runnable r) {  
    handler.post(r);  
  }  
}
```
- 在Call.enqueue()中发送到主线程

```java
@Override
public void enqueue(final Callback<T> callback) {  
  Objects.requireNonNull(callback, "callback == null");  
  
  // delegate是Okhttp的call，被代理对象
  delegate.enqueue(  
      new Callback<T>() {  
        @Override  
		public void onResponse(Call<T> call, final Response<T> response) {  
		  // execute runnable
          callbackExecutor.execute(  
              () -> {  
                if (delegate.isCanceled()) {  
				} else {  
                  callback.onResponse(ExecutorCallbackCall.this, response);  
				}  
              });  
		  }  
  
        @Override  
	    public void onFailure(Call<T> call, final Throwable t) {  
          callbackExecutor.execute(() -> callback.onFailure(ExecutorCallbackCall.this, t));  
	    }  
      });  
}
```


## 2. 注解和动态代理

### 2.1 动态代理

- 静态代理能明确知道谁是代理者（Proxy），但动态代理不知道
- 动态代理能代理许多Interface Subject，静态代理只能一个个代理

**InvocationHandler和Proxy**


```kotlin
// 通用
class DynamicProxy(private val obj: Any) : InvocationHandler {  
  
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {  
        method?.invoke(obj, args)  
        return obj  
  }  
}
```

```kotlin
private fun proxy() {  
    val realSubject = RealSubject()  
    val cls = realSubject.javaClass  
	val proxy = Proxy.newProxyInstance(  
        cls.classLoader,  
	    cls.interfaces,  
		DynamicProxy(realSubject)  
    ) as ISubject  
  
    proxy.buySth()  
}
```

**大体流程**

Retrofit -> 建造者 -> create(interface api)函数 -> 实现匿名动态代理 ->  调用动态代理对象函数 -> 注解解析 -> Retrofit.Call.enqueue() -> OkHttp.RealCall 切入子线程 -> 主线程回调线程池切回主线程