# A JSON PARSER

## 一、主要功能
1. 解析JSON字符串和文件，转换为JSON对象或者JSON数组
2. 序列化JSON对象，JSON数组，JavaBean, List, Map
3. JavaBean的封装与拆箱

## 二、解析流程

### 过滤器

**JsonReader**类主要通过缓存来自String或者是I/O的字符。在缓存的过程中，将双引号之外（非字符串内）的所有无效字符过滤掉。提供<b>read()<b>给收集器由用读取缓存中的字符。其中，当缓存的来源是String时，该类会一次性缓存所有的字符。如果时I/O流时，则会按照指定大小进行缓存。

### 收集器

收集器属于**JsonResolver**类的一部分，主要的作用是缓存除JSON构造字符
外的所有的字符。然后依据JSON六个构造字符进行内容分发。

- '{': beginObject。将开启新一轮的JSON对象解析，进入下一级。

- '}': endObject，结束当前JSON对象解析，并返回上一级。

- '['：beginArray，开启新一轮JSON数组解析

- ']'：endArray，结束当前的JSON数据解析

- ':': key-value标记，将保存key值

- ','：next-item标记，将保存Object对象中value值或者数字的element

    

### 数值解析器

JSON中共有七种数据类型，分别是Object，Array，Number，String，false，true，null

**JsonValueResolver**类主要用来解析Number，String，三个字面量（false，true，null）

使用责任链设计模式，按照一定的顺序进行解析，如果解析成功，返回相应的对象，反之，进入下一个解析器。默认的解析顺序为：String，Boolean，Null，Number。每一个解析器主要由两部分组成，分别是检查、解析。

### 生成器

生成器也属于**JsonResolver**类的一部分，主要是通过设置当前解析环境下的上下文(context)，根据字符分发器，如果出现**'{'**，生成一个**JsonObject**对象，作为下个解析器的context，同时保存到当前的context中。入股出现**'['**字符，生成一个**JsonArray**对象。



