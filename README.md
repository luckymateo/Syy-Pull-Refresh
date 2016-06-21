## Syy-Pull-Refresh
    支持自定义页面加载动画
    支持上拉刷新，下拉加载（自定义header、footer）
    
##Integration
Step 1.Add the JitPack repository to your build file Add it in your root build.gradle at the end of repositories:
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
Step 2. Add the dependency
    dependencies {
        compile 'com.github.SmileyCloud:Syy-Pull-Refresh:v1.0.0'
    }
