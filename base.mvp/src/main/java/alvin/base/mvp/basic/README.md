# Component and module

## Component

### Define component

Define component interface

```java
@Component
public interface MyComponent {
    void inject(MyClass target);
}
```

Define one class with construct mark with `@Inject` annotation

```java
public class MyService {
    
    @Inject
    public MyService() {
        ...
    }
    
    ...
}

```

### Compile

Build project, and `dagger-compiler` should generate `DaggerMyComponent` class


### Use component

```java
public class MyClass {
    @Inject MyService service;
    
    public void onCreate(...) {
        DaggerMyComponent.create().inject(this);
    }
}
```

One instance of `MyService` class should be inject to `service` field


## Component with module

### Define module

Define a module class, with **provider** methods

```java
public class MyModule {
    private MyOperator operator;
    
    public MyModule(MyOperator operator) {
        this.operator = operator;
    }
    
    @Provides
    public MyProcessor providesMyProcessor() {
        ...
        
        return new MyProcessor(admin);
    }
}

```
> Constructor with arguments of module not necessary


Define a components base on this module

```java
@Component(modules = {MyModule.class})
public interface MyComponent {
    void inject(MyClass target);
}
```

### Use module

```java
public class MyClass {
    @Inject MyProcessor processor;
    
    public void onCreate(...) {
        
        MyOperator operator = [create object];
        
        DaggerMyComponent.builder()
            .myModule(new MyModule(operator))
            .inject(this);
    }
}
```