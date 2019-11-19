<div align="center">
    <a href=""> <img src="https://i.loli.net/2019/08/03/AO8mcDn63NZtFsf.png" ></a>
</div>

<div align="center">
    <a href=""> <img alt="GitHub stars" src="https://img.shields.io/github/stars/defineYIDA/NoneIM?style=social"></a>
    <a href=""> <img alt="GitHub forks" src="https://img.shields.io/github/forks/defineYIDA/NoneIM?style=social"></a>
    <a href=""> <img alt="GitHub issues" src="https://img.shields.io/github/issues-raw/defineYIDA/NoneIM?style=social"></a>
</div>


## ![](https://img.shields.io/badge/NONE--IM-%E4%BB%8B%E7%BB%8D-9cf)

`None-IM`是一款分布式可横向扩展的IM系统。

目前，`None-IM`处于开发阶段，已经实现以下功能：

- 登陆，注册，私聊，群聊等IM基础功能
- `IM`服务的集群--注册与发现
- 中间`porxy`层负责`Cilent`的接入和`IM`服务的负载均衡
- `porxy`层提供内网穿透能力


## ![](https://img.shields.io/badge/NONE--IM-%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84-9cf)
**文件目录：**
~~~
None-IM  目录
├─none-client         客户端
├─none-conmmon        公共代码
├─none-proxy          代理层
├─none-server         服务端
~~~

**架构示意图：**

![none_jg.png](https://i.loli.net/2019/08/03/1P4pbcszrEyqYu5.png)


## ![](https://img.shields.io/badge/NONE--IM-%E6%A0%B8%E5%BF%83%E4%BE%9D%E8%B5%96-9cf)

网络框架：
```
<groupId>io.netty</groupId>
<artifactId>netty-all</artifactId>
<version>4.1.6.Final</version>
```

自定义协议的序列化(serialize):
```
<groupId>com.alibaba</groupId>
<artifactId>fastjson</artifactId>
<version>1.2.29</version>
```

## ![](https://img.shields.io/badge/NONE--IM-%E9%97%AE%E9%A2%98-9cf)

[粘包和半包的原因使用WireShark分析](https://github.com/defineYIDA/NoneIM/issues/6)

[集群模式下消息转发的方案如何选择？](https://github.com/defineYIDA/NoneIM/issues/13)


## ![](https://img.shields.io/badge/NONE--IM-TODO-9cf)

- [ ] 服务器消息推送,针对在线和离线用户

- [x] Redis对用户信息的缓存

- [x] proxy实现内网穿透

- [x] zookeeper 实现服务的注册和发现

- [x] IM Server的集群（支持横向扩展）

- [ ] rpc 实现跨server的通信
