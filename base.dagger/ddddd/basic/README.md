# Component 与 Module



## I Component

### 1.1 定义 Component

`Component`相当于“对象管理容器”，用于管理所有可被注入的对象以及这些对象的生存周期以及依赖范围 (`Scope`)

#### 1.1.1 定义 Component 接口

```java
@Component
public interface MyComponent {
    void inject(MyClass target);	// 注入方法
}
```

`Component`中定义的`inject`方法用于将`Component`管理的对象按需注入到`target`对象中

#### 1.1.2 定义被注入的类

```java
public class MyService {
    @Inject
    public MyService() {
        ...
    }
    ...
}
```

`MyService`必须具备一个注解为`@Inject`的构造器，否则`Dagger`无法构造`MyService`类对象

#### 1.1.3 编译

Build 当前工程，通过`dagger-compiler`生成`DaggerMyComponent`类，用于管理所有的备注解对象

### 1.2 使用 Component

```java
public class MyClass {
    @Inject MyService service;
    
    public void onCreate(...) {
        DaggerMyComponent.create().inject(this);
    }
}
```

在需要注入的类中，使用`DaggerMyComponent`类产生`MyComponent`对象，通过定义的`inject`方法进行注入



## II 为 Component 配置 Module

### 2.1 定义 Module

`Module`可以是一个普通`class`，也可以是`abstract class`或`interface`，各自有不同的用途

#### 2.1.1 定义 Module 类

```java
@Module
public class MyModule {
}
```
#### 2.1.2 将 Module 和 Component 进行关联

```java
@Component(modules = {MyModule.class})
public interface MyComponent {
    void inject(MyClass target);
}
```

#### 2.1.3 使用Module

```java
public class MyClass {
    @Inject MyProcessor processor;
    
    public void onCreate(...) {
        DaggerMyComponent.builder()
            .myModule(new MyModule())
            .inject(this);
    }
}
```

此时的`Module`类并没有什么实际的作用，但可以在`Module`中定义`Provider`和`Binder`，来明确注入对象

### 2.2 在 Module 中定义 Provider 

```java
@Module
public class MyModule {
	private final MyOperator opt;

	public MyModule(final MyOperator opt) {
		this.opt = opt;
  	}
  
  	@Providers
	public MyProcessor providersProcessor() {
    	return new MyProcessor(opt);
	}
}
```

如果 Module 中具备`@Providers`注解的方法，则`Module`可以定义为`class`或`abstract class`

如果无需依赖 Module 类成员，`@Providers`注解也可以标注在`static`方法上

```java
@Module
public class MyModule {
	@Providers
	public MyOperator providersOperator() {
    	return new MyOperator();
	}
  
  	@Providers
	public static MyProcessor providersProcessor(MyOperator opt) {
    	return new MyProcessor(opt);
	}
}
```

#### 2.2.1 使用带 Provides 的 Module 类

```java
public class MyClass {
    @Inject MyProcessor processor;
    
    public void onCreate(...) {
      	final MyOperator opt = ...;
      
        DaggerMyComponent.builder()
            .myModule(new MyModule(opt))
            .inject(this);
    }
}
```

或者

```java
public class MyClass {
    @Inject Provider<MyProcessor> processor;
    
    public void onCreate(...) {
      	final MyOperator opt = ...;
      
        DaggerMyComponent.builder()
            .myModule(new MyModule(opt))
            .inject(this);
    }
}
```

### 2.3 在 Module 中定义 Binder

假设`MyProcessor`实现了`IMyProcessor`接口

```java
public interface IMyProcessor { ... }
```

```java
public class MyProcessor implements IMyProcessor { ... }
```

如果需要以`IMyProcessor`接口类型注入对象，则需要通过`@Binder`来指定接口与实现类的关系

```java
@Module
public interface MyModule {
  	@Binds
  	IMyProcessor bindMyProcessor(MyProcessor processor);
}
```

这样就说明了`IMyProcessor`接口由`MyProcessor`类实现。

注意：`@Binds`注解的方法必须是一个**抽象**方法（接口方法或`abstract`方法），也就决定了具备`@Binds`注解的`Module`定义必须是`interface`或`abstract class`之一

注意：Dagger规定，**非静态的`@Provider`注解和`@Binds`注解不能同时出现在同一个`Module`中**，所以需要整合不同类型的`Module`来解决这个问题

```java
public class MyProcessor implements IMyProcessor {
  	private MyOperator operator;
  	
  	@Inject
  	public MyProcessor(MyOperator operator) {
    	this.operator = operator;
  	}
}
```

```java
@Module(includes = {MyModule.BindingModule.class})	// 包含另一个 Module 共同起作用
public class MyModule {
  	private MyOperator operator;
  
	public MyModule(MyOperator operator) {
    	this.operator = operator;
	}
  
  	@Providers
	public MyOperator providersMyOperator() {
    	return this.operator;
  	}
  	
  	@Module
	public interface BindingModule {
    	@Binds
	  	IMyProcessor bindMyProcessor(MyProcessor processor);
  	}
}
```




## III 为 Component 设置 Builder

如果需要注入一些额外对象，但通过`Provider`又显得比较麻烦，则可以自定义`Component`的`Builder`来解决这个问题

### 3.1 定义 Builder

```java
@Component
public interface MyComponent {
    void inject(MyClass target);
  
  	@Component.Builder
	public interface Builder {
		Builder name(@Named("USER_NAME") String name);	// 需注入的对象
		MyComponent build();
    }
}
```

#### 3.1.1 使用 Builder

```java
public class MyClass {
    @Inject MyProcessor processor;
    @Inject @Named("USER_NAME") String name;
  
    public void onCreate(...) {
      	final MyOperator opt = ...;
      
        DaggerMyComponent.builder()
            .name("Alvin")
            .inject(this);
    }
}
```

#### 3.1.2 同时使用 Builder 和 Module

如果定义`Builder`的`Component`也同时关联了`Module`，则需要在`Builder`中定义`Module`设置方法

```java
@Component(modules = {MyModule.class})
public interface MyComponent {
    void inject(MyClass target);
  
  	@Component.Builder
	public interface Builder {
      	Builder myModule(MyModule module);
		Builder name(@Named("USER_NAME") String name);	// 需注入的对象
		MyComponent build();
    }
}
```

使用是需要提供`Module`的对象实例

```java
public class MyClass {
    @Inject MyProcessor processor;
    @Inject @Named("USER_NAME") String name;
  
    public void onCreate(...) {
      	final MyOperator opt = ...;
      
        DaggerMyComponent.builder()
            .name("Alvin")
          	.myModule(new MyModule())
            .inject(this);
    }
}
```

