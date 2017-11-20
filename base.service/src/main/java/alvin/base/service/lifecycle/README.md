# Service lifecycle



## Create and destroy Service

When `Service` start or bind first time, the `Service` will be created, and the method `onCreate` will be invorked once time;

If `Service` was stoped and on one bind on this `Service`, the `Service` will be destroyed, and the method `onDestroy` will be invoked once time.

```java
public class MyService extends Service {
    ...

    @Override
    public void onCreate() {
        ...
    }

    @Override
    public void onDestroy() {
        ...
    }
}
```

The whole lifecycle of each `Service` is from the `onCreate` method is called, until to the `onDestroy` is called.



## Start and stop Service

`Service` can be started by `Context::startService` method, if this `Service` did not exist, it will be created, and `onCreate` method of this `Service` will be call once time;

```java
...
final Intent intent = new Intent(context, MyService.class);
context.startService(intent);
```

`Service` also can be stoped by `Context::stopService` method, if any others not bind on this `Service`, this `Service` will be destroyed, and whole `Service` will not available any more.



## Bind and unbind Service

Bind `Service` need 

```java
final ServiceConnection conn = new ServiceConnection() {
	@Override
  	public void onServiceConnected(ComponentName componentName, IBinder binder) {
      	...
  	}

	@Override
  	public void onServiceDisconnected(ComponentName componentName) {
      	...
  	}
};
```

```java
final Intent intent = new Intent(context, MyService.class);
context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
```

`Service` can be unbind by `Context::unbindService` method.

```java
context.unbindService(conn);
```



There is a **Reference Counter** in `Service`, it will be increment when someone bind on this `Servie` and decrement when someone unbind from this `Service`

If no one start the `Service` and Reference Counte is zero, the `Service` should be destroyed







