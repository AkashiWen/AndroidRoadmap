**2022年04月06日21:40:09**

# 注解（annotation）与注解处理器（APT annotation Processing Tool）

## 1. 注解

**使用场景**

- 注解+APT 生成一些java文件（butterknife dagger hilt databinding..）
- 注解+代码埋点 （ARouter）
- 注解+反射Reflect+动态代理 （Lifecycle核心原理，Retrofit）
- 替代枚举类，提升性能

**注解语法**

1. 普通注解
```java
// java
@Target({ElementType.FIELD, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
	int value();
}
```  
```kotlin
// kotlin
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)// 写到哪种东西上（字段、类、函数等等）  
@Retention(AnnotationRetention.SOURCE) // 注解生命周期  
annotation class BindView(  
    val value: Int  
)
```

2. 限定注解
```java
// java （系统的DrawbaleRes）
@Documented  
@Retention(CLASS)  
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE})  
public @interface DrawableRes {  
}
```
```kotlin  
// kotlin
/**  
 * 语法注解，同时可做枚举
 * 定义只能使用MONDAY, TUESDAY
 * But Kotlin1.0.3以后已经不支持@IntDef annotation了 
 * */
@MustBeDocumented  
@IntDef(MONDAY, TUESDAY)  
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)  
@Retention(AnnotationRetention.SOURCE)  
annotation class WeekDay {  
    companion object {  
      const val MONDAY = 1  
	  const val TUESDAY = 2  
  }  
}
```


> - AnnotationRetention
    - SOURCE // 注解只在源码阶段有效，javac编译成.class后无效
    - BINARY // 注解在源码阶段、javac编译成.class后有效，在加载虚拟机后无效，反射不可见
    - RUNTIME // 注解在所有阶段有效，包括运行时


**使用注解**

1. 普通用法
```kotlin
class AnnotationActivity : AppCompatActivity() {  
  
    @BindView(1)  // @BindView(value:1)
    lateinit var binding: ActivityAnnotationBinding  
  
    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        binding = ActivityAnnotationBinding.inflate(layoutInflater)  
        setContentView(binding.root)  
    }  
}
```

2. 规定参数语法规则
```kotlin
// 规定只能使用res资源
fun setDrawable(@DrawableRes resId: Int) {}

/**  
 * day视为枚举类型 
 * */
class Week {  
  @WeekDay  
  var day: Int = WeekDay.MONDAY  
}
```


## 2. APT

**作用**

> java -------- javac --------> class

- 用来生成代码

>APT在javac阶段可以寻找注解，针对注解编程，修改编译过程


**使用**
``project :annotationCompiler``
```groovy
plugins {  
  id 'java-library'  
  id 'org.jetbrains.kotlin.jvm'  
  id 'kotlin-kapt'  
}  
  
java {  
  sourceCompatibility = JavaVersion.VERSION_1_8  
  targetCompatibility = JavaVersion.VERSION_1_8  
}  
  
dependencies {  
  
  implementation fileTree(dir: 'libs', include: ['*.jar'])  
    api project(path: ':annotations')  
	  
	// 添加auto-service
    annotationProcessor 'com.google.auto.service:auto-service:1.0-rc6'  
    // 只在编译器，不打包
    compileOnly 'com.google.auto.service:auto-service:1.0-rc6'
}
```

``app/build.gradle``
```groovy
plugins {
  id 'kotlin-kapt'  
}
dependencies {
  // 坑1：注意kotlin项目必须用kapt依赖而非annotationProcessor，否则无法生成源码
  kapt project(':annotation_compiler')
}
```

**注册注解处理器**

- 静态注册
  在main/下创建resources目录，文件../main/resources/META-INF/services/javax.annotation.processing.Processor
  并填写注解处理器路径com.akashi.annotation_compiler.Compiler

- 动态注册（Java有效）：

```kotlin
/**  
 * 注解处理程序 * 1.打注解@AutoService, 注册Process.class * 2.继承AbstractProcessor */
@AutoService(Process::class)  
class Compiler : AbstractProcessor() {  
    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {  
        TODO("Not yet implemented")  
    }  
}
```
> Process.class是javac在编译过程中用到的类，这里只注册到AutoService
> 使javac在编译阶段能调用到此处的process()，最终生成新代码

**生成源码（source file）**

1. 手写
2. Javapoet
>使用kotlin的话，可以用square的[kotlinpoet](https://github.com/square/kotlinpoet)来生成源码


## 3. 注解的多态

注解的注解即为注解的多态

```java
public @interface EventBase {
	Class<?> listenerType();
	String listenerSetter();
	String listenerCallback();
}
```

```java
@EventBase(listenerType = "View.OnClickListener.class", listenerSetter = "setOnClickListener", listenerCallback = "onClick")
public @interface onClick {}
```

```java
@EventBase(listenerType = "View.OnLongClickListener.class", listenerSetter = "setOnLongClickListener", listenerCallback = "onLongClick")
public @interface onLongClick {}
```

> EventBase为onClick、onLongClick的父类