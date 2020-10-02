# Bulls:ox: & :cow2:Cows

就是那个每个有机器人的QQ群都会有的猜数小游戏

## 目录结构

``` powershell
./
├── bin/                # 生成的debug class文件
├── build/              # 生成的release class文件、jar文件
├── build.gradle*       # Gradle构建文件
├── gradle/             # Gradle目录
├── gradlew*            # 类Unix环境下的gradle wrapper
├── gradlew.bat*        # Windows环境下的gradle wrapper
├── README.md*          # 此自述文件
├── settings.gradle*    # Gradle设置文件
└── src/                # 源代码目录
   ├── main/            # 工程代码目录
   └── test/            # 测试代码目录
```

## 运行说明

项目骨架由`gradle init --type java-application`命令生成，可通过`build.gradle`文件查看具体构建目标。下面列出一些基本的命令：

- `gradle build`：进行构建任务
- `gradle clean`：清理构建目录
- `gradle test`：进行单元测试
- `gradle run`：运行工程（不适合本项目）
- `gradle installDist`：生成安装目录

若想查看效果，可运行`gradle installDist`后运行`.\build\install\bulls_and_cows\bin\bulls_and_cows.bat`

加上`-h`或`--help`参数可查看帮助：

``` powershell
PS D:\Documents\java\bulls_and_cows> gradle installDist

> Configure project :
Repository https://jcenter.bintray.com/ replaced by https://maven.aliyun.com/repository/jcenter/.

BUILD SUCCESSFUL in 3s
4 actionable tasks: 4 executed
PS D:\Documents\java\bulls_and_cows> .\build\install\bulls_and_cows\bin\bulls_and_cows.bat --help
bulls_and_cows: 猜数字游戏
  usage: java <CLASS_FILE> [ARGUMENTS]
         java App.java [ARGUMENTS] *仅限于使用JDK 11及以上
  ARGUMENTS:
    -h, --help: 打印此帮助文本
    -g, --game: <LENGTH_OF_SECRET> <MODE>:
      LENGTH_OF_SECRET: 从 4 到 9 的数字
        指定希望猜测的密文长度。
      MODE: 以下中的一项 [d|i|u] 或 [distinct|indistinct|userinput]
        指定用于生成密文的模式。
          [d, distinct]: 密文中数字不重复
          i, indistinct: 密文中数字允许重复
          u, userinput : 密文由用户输入
PS D:\Documents\java\bulls_and_cows>
```

__帮助中：__
- "< >"为必填占位符，
- "[ ]"为可选占位符，
- 用"|"分隔的为选项，
- 选项中用","分隔的为别名，
- 用"[ ]"框起来的为默认值

## 代码说明

似乎...没啥好说明的。核心代码就几行：

``` java
private static String getHint(final String secret, final String guess) {
    int bulls = 0;
    int cows = 0;
    final int[] numbers = new int[10];
    for (int i = 0; i < secret.length(); i++) {
        if (secret.charAt(i) == guess.charAt(i)) bulls++;
        else {
            if (numbers[secret.charAt(i) - '0'] ++ < 0) cows++;
            if (numbers[guess.charAt(i) - '0'] -- > 0) cows++;
        }
    }
    return String.format("%sA%sB", bulls, cows);
}
```
所有方法均为静态私有方法，因为想不出来有什么创建类的必要

单元测试用了反射和谷歌的字符集库，但工程代码保持了零依赖

集成测试大概比较难做，因为需要真实的没有经过重定向的终端