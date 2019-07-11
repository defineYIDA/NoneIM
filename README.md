## NoneIM


### Dependencies

framework:
```
<groupId>io.netty</groupId>
<artifactId>netty-all</artifactId>
<version>4.1.6.Final</version>
```
serialize:
```
<groupId>com.alibaba</groupId>
<artifactId>fastjson</artifactId>
<version>1.2.29</version>
```
### Solve

[粘包和半包的原因使用WireShark分析](https://github.com/defineYIDA/NoneIM/issues/6)

### future

- [ ] 服务器消息推送,针对在线和离线用户
- [ ] Redis对用户信息的缓存
- [ ] 新增router层实现负载均衡
- [ ] zookeeper 实现服务的注册和发现
- [ ] IM Server的集群（支持横向扩展）
- [ ] rpc 实现跨server的通信
