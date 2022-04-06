# 注解和注解器

## 1. 注解

**java注解**

```java
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface BindView {
}
```

**kotlin注解**

```kotlin
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotaion class BindView(
)
```

