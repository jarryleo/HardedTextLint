## 检测 data class 是否打上注解 @Keep

本库只有一个作用，检测 data class 是否打上注解 @Keep ,遗漏则爆红，并且无法编译通过
血的教训换来的库，望珍惜！

### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

![RELEASE](https://jitpack.io/#jarryleo/LintChecker/Tag)

### Step 2. Add the dependency

```
    dependencies {
	        implementation 'com.github.jarryleo:LintChecker:1.0.0'
	}
```