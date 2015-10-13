Election App : Mae
------------------

Ultimate guide to 2015 general election in Myanmar. 

Learn about parties, compare candidates and get in-depth coverage on how, where and when you can vote.

Before you compile
---
You will see several unknown variables in `build.gradle` file.

- buildConfigField `MAE_KEY`
- buildConfigField `MAE_SECRET`

Those are needed to access our API however there is no way you can get it. So, you won't really be able to view the data inside the app. The purpose of open source is just to release our source codes for reference.

- storePassword `MAE_PASSWORD`
- keyAlias `MAE_NAME`
- keyPassword `MAE_PASSWORD`

Those are needed for signing configs. You can safely delete all those `signingConfig`.

- resValue `GOOGLE_MAPS_API_KEY_{buildType}`

You will see three of those. One for each build type. Generate your own Google Maps API Key and do as follow:

1. Create `gradle.properties` file under root directory.
2. Add `GOOGLE_MAPS_API_KEY_DEBUG = "YOUR_DEBUG_KEY_HERE"`

Disclaimer
---
Although we released the source codes for reference and study purpose, we don't guarantee that our codes are best practices to follow.

License
-------

    Copyright 2015 MMAUG

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
