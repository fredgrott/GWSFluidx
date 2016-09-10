![fluidx](art/fluidx.png)

GWSFluidx
=========

There are several myths about developing android applications, with one 
of those myths being that rxJava gives you App Responsiveness by default.
In-fact it does not.

in-fact you can develop App Responsive Android Apps without RxJava 
and this framework provides the tools to do that with MVP-VM and MVVM patterns.

# BENEFITS

1. In the MVP pattern we get a full mock-able Presenter that is not coupled 
   with a data-source or view.
    
2. In both MVP and MVVM patterns one gets a fluid UI at 60 fps almost fully 
   fail-safe, ie will almost-by-default obtain 60 fps fluid  UI status with 
   no extra work on the developer's part.
   
# COSTS

1. No, you cannot use the MVC pattern with this framework(not really a con though). 

2. You might not be use to a fully de-coupled presenter pattern.

3. You have to and should use immutables instead of mutables.

# SENIOR ANDROID DEVELOPER NOTE

ANDROID 4.X
   
1. No separate RenderThread
   
2. Does have smooth scrolling via timing changes of frame pre-fetches, ie 
   VSYNCH via Project Butter
   
3. Dalvik VM with slower GC pass speed of 4ms

4. UI-thread has scrolling and rendering on the same thread along with 
   Material Animations.
   
ANDROID 5.X TO 7.X

1. Separate RenderThread which has both rendering and material animations

2. Does have smooth scrolling via timing changes of frame pre-fetches, ie 
   VSYNCH via Project Butter
   
3. ART VM with a faster GC passes of 2ms

4. UI thread has scrolling and non-material animations

Thus this application architecture framework has to fail-safe in that in its 
default use state the developer only has a very little or no work to do to 
get full 60 fps FLUID UI APP RESPONSIVENESS. And it does this by some correct 
architecture and implementation choices such as off-loading expensive tasks off 
of the UI-thread, btw which every Android GDE and Google Android Team member 
recommends.


   
   
   
# ARTICLES

Articles explaining the architecture can be found at:

# WIKI

Start at these wiki pages for docs:




# Usage

if library, describe how to download library using jitpack than describe how to use the library.

I use jitpack to upload my libraries so you put this in your root buildscript:

```groovy
allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }
```
Than in the module buildscript:


```groovy
compile 'com.github.shareme:GWSFluidX:{latest-release-number}@aar'
```

# Developed By

![gws logo](art/gws_github_header.png)

<a href="http://stackoverflow.com/users/237740/fred-grott">
<img src="http://stackoverflow.com/users/flair/237740.png" width="208" height="58" alt="profile for Fred Grott at Stack Overflow, Q&amp;A for professional and enthusiast programmers" title="profile for Fred Grott at Stack Overflow, Q&amp;A for professional and enthusiast programmers">
</a>


Created by [Fred Grott](http://shareme.github.com).


# Credits

The original un-polished code and concepts was developed by Robert LaThanh:

[android-mvp-framework](https://github.com/lathanh/android-mvp-framework)

I inserted some debugging hooks and created some example base classes and 
added some docs and articles to explain how to use it. I also expanded it 
so that one does not have the Google Data Binding library if one wants to 
use it without that library and provide your own binding via either 
RxJava or Agera.


# Resources

Resources can be found at the [GWSTheWayOfAndroid wiki](http://github.com/shareme/GWSTheWayOfAndroid/wiki).



# License

Copyright (C) 2016 Fred Grott(aka shareme GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License"); you
may not use this file except in compliance with the License. You may
obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied. See the License for the specific language
governing permissions and limitations under License.


