# 学习笔记

## 交作业

1. GC 总结
   1.使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。
   2.使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。
   3.根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 的总结，提交到 Github。

### 命令
   
```shell
# GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseSerialGC -Xloggc:seria.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseParallelGC -Xloggc:paralle.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseConcMarkSweepGC -Xloggc:cms.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseG1GC -Xloggc:g1.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
# GCLogAnalysis
java -Xms1g -Xmx1g -XX:+UseSerialGC -Xloggc:seria.gateway.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gateway-server-0.0.1-SNAPSHOT.jar
java -Xms1g -Xmx1g -XX:+UseParallelGC -Xloggc:paralle.gateway.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gateway-server-0.0.1-SNAPSHOT.jar
java -Xms1g -Xmx1g -XX:+UseConcMarkSweepGC -Xloggc:cms.gateway.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gateway-server-0.0.1-SNAPSHOT.jar
java -Xms1g -Xmx1g -XX:+UseG1GC -Xloggc:g1.gateway.gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -jar gateway-server-0.0.1-SNAPSHOT.jar
```

### 结论

1. 目前任何时候都不推荐使用串行 GC
2. 并行 GC 每次 GC 时间相比于 CMS 可能略大（平均中断时间大于 CMS），但 GC 效率总体高于 CMS，适合对延迟要求不高，但对并发要求高的场景
3. CMS GC 策略复杂，平均每次 GC 时间小于并行 GC ，但 GC 次数大于并行 GC，适合对延迟要求高，但对并发要求不高的场景
4. G1 GC 配置参数多，GC 响应时间可控，鉴于并行 GC 和 CMS 之间，属于折中的选择

2. 使用 OkHttp 访问 http://localhost:8881/



## JVM 核心技术（3）调优分析与面试经验

### GC 日志解讀與分析

```shell script
# 打印 GC 詳情
java -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

# 嘗試不同 GC
# -XX:+UseSerialGC
# -XX:+UseParallelGC
# -XX:+UseConcMarkSweepGC
# -XX:+UseG1GC
```

### JVM 线程堆栈数据分析

* JVM 内部线程分类
* 安全点？
* 并发安全！
* 线程死锁（线程分析 fastthread）
  
### 内存分析与相关工具

* 对象结构！
* 对象结构分析（JOL）
* 对象头和对象引用
* 包装类型，多维数组，String 内存占用

浪费空间的因素

1. 对象结构
2. 内部补齐

* OutOfMemoryError 问题分析
* 内存分析工具

1. Eclipse MAT
2. jhat

* G1大对象

### JVM 问题分析与调优经验

1. 分配速率
2. 过早提升

### 异常 GC 真实问题分析

pass

### JVM 常见面试问题

pass

### 课后小练习

pass

### 问题

pass

### 作业

1. 使用 GCLogAnalysis.java 自己演练一边串行/并行/CMS/G1的案例
2. 使用压测演练 gateway-server.jar 案例

## NIO 模型与 Netty 入门

## Java Socket 编程：如何基于 Socket 实现 Server

* 同步、異步
* 阻塞，非阻塞

### 五種 IO 模型

1. 阻塞
2. 非阻塞
3. IO 復用
4. 信號驅動的IO模型
5. 異步IO模型

### Netty Http 例子

pass

### 作業

1. （可選）運行課上的例子，以及 Netty 的例子，分析相關現象
2. 寫一段代碼，使用 HttpClient 或者 OkHttp 訪問 localhost:8801,提交代碼到 GitHub

## 參考

[G1 GC 參考](https://tech.meituan.com/2016/09/23/g1.html)
