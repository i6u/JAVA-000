# 学习笔记

* ClassLoader

HelloClassLoader.java

* JVM 参数

Xmx、Xms、Xmn、Meta、DirectMemory、Xss 分别代表什么

Xmx（-XX:MaxHeapSize）   设置堆最大值,必须为 1024 的整数倍，最小 2mb

Xms   设置堆的初始/最小值，必须为 1024 的整数倍，最小 1mb

Xmn（-XX:NewSize=size）   设置年轻代堆大小（建议整个堆大小的一半到四分之一）

> -XX:MaxNewSize=size  设置年轻代最大堆大小

Meta  元数据区

DirectMemory    直接内存

Xss    设置线程栈大小

![momory model](https://github.com/i6u/JAVA-000/blob/main/Week_01/attach/memory_model.jpg)

## 参考

[Java 参数文档](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html)
[默认堆大小](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/parallel.html#sthref31)
