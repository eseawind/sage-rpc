# sage-rpc
Sage-Rpc 是一个开源的RPC框架,提供一个简单的SOA模型来供参考

## sage-rpc-core
Sage-Rpc-Core是整个RPC框架的核心工程包

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

##sage-rpc-invoke
Sage-Rpc-Invoke是传输服务,提供消费者和服务者之间的消息传输.