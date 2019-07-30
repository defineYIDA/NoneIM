![](https://github.com/defineYIDA/hx/blob/master/none5.png)


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

[集群模式下消息转发的方案如何选择？](https://github.com/defineYIDA/NoneIM/issues/13)

### future

- [ ] 服务器消息推送,针对在线和离线用户

- [x] Redis对用户信息的缓存

- [x] proxy实现内网穿透

- [x] 新增router层实现负载均衡

- [x] zookeeper 实现服务的注册和发现

- [x] IM Server的集群（支持横向扩展）

- [ ] rpc 实现跨server的通信
