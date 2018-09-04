# Android Tests



## JUnit Test

### Code

```java
public class SomeTest {
    @Test
    public void testSomething() throws Exception {
        final excepted = ...
        final actual = beTestedMethod();
        assertThat(actual, is(excepted));
    }
}
```

### Dependances

```groovy
dependencies {
    ...
    testImplementation "junit:junit:${junit_ver}"
    testImplementation "org.hamcrest:hamcrest-all:${hamcrest_ver}",
    testImplementation "org.mockito:mockito-all:${mockito_ver}"
    testImplementation "org.unitils:unitils-core:${unitils_ver}",
    testImplementation "com.squareup.assertj:assertj-core:${assertj_ver}"
}
```

### Advantage

- Simple
- Ran fast

### Disadvantage

- Just for JDK, unable to test Android SDK



## Robolectric Test

> [Home](http://robolectric.org/})
>
> [Github](https://github.com/robolectric/robolectric)

### Code

```java
@RunWith(RobolectricTestRunner.class)
public class MyActivityTest {
    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {
        Activity activity = Robolectric.setupActivity(MyActivity.class);

        Button button = (Button) activity.findViewById(R.id.press_me_button);
        TextView results = (TextView) activity.findViewById(R.id.results_text_view);

        button.performClick();
        assertThat(results.getText().toString(), is("Testing Android Rocks!"));
    }
}
```

### Dependances

```groovy
dependencies {
    ...
    testImplementation "junit:junit:${junit_ver}"
    testImplementation "org.hamcrest:hamcrest-all:${hamcrest_ver}",
    testImplementation "org.mockito:mockito-all:${mockito_ver}"
    testImplementation "org.unitils:unitils-core:${unitils_ver}",
    testImplementation "com.squareup.assertj:assertj-core:${assertj_ver}"
    testImplementation "org.robolectric:robolectric:${robolectric_ver}"
}
```

### Advantage

- Also can be ran fast
- Without Android Device or Simulator

### Disadvantage

- Some tests may became complex
- Unstable
- Not all Android API can be covered



## Android Test

### Code

```java
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AndroidTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void sayHello() {
        onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
        onView(withText("Say hello!")).perform(click());

        String expectedText = "Hello, " + STRING_TO_BE_TYPED + "!";
        onView(withId(R.id.textView)).check(matches(withText(expectedText)));
    }
}
```

```groovy
dependencies {
    ... 
    testImplementation "org.mockito:mockito-core:${mockito_ver}",
    testImplementation "org.unitils:unitils-core:${unitils_ver}"
    testImplementation "com.android.support.test:runner:${runner_ver}"
    testImplementation "com.android.support.test:rules:${rules_ver}"
    testImplementation "com.android.support.test.espresso:espresso-core:${espresso_ver}"
}
```



### Advantage

- Full test

### Disadvantage

- Ran slow
- Must with Android Device or Simulator
