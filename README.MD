# 关于sage-rpc框架
Sage-Rpc 是一个开源的RPC框架,提供一个简单的SOA模型来供参考.这个开源项目创立的目的,也是希望自己加深对SOA框架的理解,同时提高个人的技术能力.
在框架的设计和功能上,也是参考了Dubbo和Hessian的设计思路,但是个人的能力有限,也只能参考个皮毛.需要在项目开发的过程中不断的深入和大家的指导.

框架希望实现功能也相对简单,希望可以最简单的来解释SOA框架最Base的构成和模式.项目整体模块构成如下:

## sage-rpc-container
Sage-Rpc-Container是容器服务,提供服务方的运行容器.
### sage-rpc-container-http
一个基于Jetty9的Http服务方容器服务.

## sage-rpc-serialize
Sage-Rpc-Serialize是消息序列化服务,提供服务方和消费方的消息序列化.
### sage-rpc-serialize-json
基于Alibaba Fastjson的消息序列化服务.

## sage-rpc-registry
Sage-Rpc-Registry是服务注册与发现服务,提供服务方的服务注册和消费方的服务发现

## sage-rpc-invoke
Sage-Rpc-Invoke是传输服务,提供消费者和服务者之间的消息传输.

## sage-rpc-framework
Sage-Rpc-Framework是框架主服务,不同类型实现的框架都在这里做最后的集成.

# 版本规划
## 第一版
基于Jetty9作为服务端容器,搭建一个简单的SOA框架,基于Http协议调用,消息序列化基于JSON,同时具备简单的服务注册与发现机制.

## 短期规划
1.服务注册与发现基于Zookeeper实现
2.更加完善的DEMO和Unit Test Case

## 中期规划
1.通讯方式增加基于Netty,序列化方式增加基于Thrift
2.规划admin管理平台/监控平台

## 长期规划
