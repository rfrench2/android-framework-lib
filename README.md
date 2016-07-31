# android-utils-lib

## AppLog - Wrapper on top of android.util.Log class:
 - Formats messages with method name
 - Add support for exception listeners

## RecyclerView
 - ArrayAdapter
 - MergeRecyclerAdapter
 - EndlessRecyclerView
 - HeaderAdapter
 - TopOffsetItemDecorator

## Animations
 - RevealAnimatorCompat
 - ResizeAnimator
 - AnimatorCollection - performs animation with list of Animators

## Images
 - Images MemoryCache
 - ImageLoader using MemoryCache

## How to install

    repositories { 
          jcenter()
          maven { url "https://jitpack.io" }
    }
   
    dependencies {
          compile 'com.github.anod:android-utils-lib:1.0.1'
    }
