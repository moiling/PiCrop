# PiCrop
#### Picture Cropper. Use Android native cropper.
PS. This repository is just for testing jCenter.

[Chinese README](https://github.com/moiling/PiCrop/blob/master/README_CHINESE.md)

## Version
`0.1.2`

## Download

Include the library as local library project (Gradle or Maven choose one).

  1. Gradle:

  ```groovy
  compile 'com.moinut:picrop:0.1.2'
  ```
  2. Maven:

  ```xml
  <dependency>
    <groupId>com.moinut</groupId>
    <artifactId>picrop</artifactId>
    <version>0.1.2</version>
    <type>pom</type>
  </dependency>
  ```

## Usage

*You can look at the Sample Project - [sample](https://github.com/moiling/PiCrop/tree/master/sample).*

#### Details of the steps.

1. Use PiCrop in `Activity` or `Fragment` or `Fragment-v4`.

  ```java
  PiCrop piCrop = new PiCrop(this);
  ```

2. To load and crop picture with callback.

  ```java
  piCrop.get(PiCrop.FROM_ALBUM, new OnCropListener() {
      @Override
      public void onStart() {
          // When PiCrop start working. You can do something like show a progressbar.
      }

      @Override
      public void onSuccess(Uri picUri) {
          // Done. The picUri is the cropped picture's uri. It's something you want!
      }

      @Override
      public void onError(String errorMsg) {
          // Error :(
      }
  });
  ```

3. Override onActivityResult method and handle PiCrop result.

  ```java
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      piCrop.onActivityResult(requestCode, resultCode, data);
  }
  ```

## License
```
Copyright (c) 2016  MOILING

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
