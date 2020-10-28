# 学习笔记

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

