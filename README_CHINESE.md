# PiCrop
#### 图片剪裁库，暂且使用了Android原生的剪裁
PS. 写这个库其实只是为了测试一下jCenter罢了

## Version
`0.2.1`

## Download

首先把远程库加入你的项目里呀 (Gradle 或 Maven 选择一个就好了).

  1. Gradle:

  ```groovy
  compile 'com.moinut:picrop:0.2.1'
  ```
  2. Maven:

  ```xml
  <dependency>
    <groupId>com.moinut</groupId>
    <artifactId>picrop</artifactId>
    <version>0.2.1</version>
    <type>pom</type>
  </dependency>
  ```

## Usage

*实在看不懂我说的啥，就直接看例子里地代码吧！ - [例子](https://github.com/moiling/PiCrop/tree/master/sample).*

#### 步骤

1. 在 `Activity` 或 `Fragment` 或 `Fragment-v4` 里实例化我们的PiCrop以便我们使用它

  ```java
  PiCrop piCrop = new PiCrop(this);
  ```

2. 使用`get`方法，通过`type`选择得到图片的途径，通过回调做你想做的事情

  `FROM_ALBUM`  : 从相册中选择图片剪切<br>
  `FROM_CAMERA` : 照一张照片来剪切
  
  ```java
  piCrop.get(PiCrop.FROM_ALBUM /*或者: PiCrop.FROM_CAMERA*/, new OnCropListener() {
      @Override
      public void onStart() {
          // 当它开始工作的时候，你可以在这里显示个进度条什么的，让它慢慢转
      }

      @Override
      public void onSuccess(Uri picUri) {
          // 剪裁完了，这里的 picUri 存的就是剪裁过后的图片了，你可以用它干所有你想干的事
      }

      @Override
      public void onError(String errorMsg) {
          // 哦豁，出错了
      }
  });
  ```

3. 复写`onActivityResult`方法，并实现PiCrop的`onActivityResult`，一定要写！否则结果得不到，那就是功亏一篑

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
