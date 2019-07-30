# 项目简介
此项目是根据rabbitMq官网java[RabbitMQ Tutorials Java版](https://www.rabbitmq.com/tutorials/tutorial-one-java.html)指导使用myeclipse构建的学习项目。
1. 主要通过Hello Word对RabbitMQ有初步认识
2. 工作队列，即一个生产者对多个消费者，循环分发、消息确认、消息持久、公平分发
3. 如何同一个消息同时发给多个消费者
   开始引入RabbitMQ消息模型中的重要概念路由器Exchange以及绑定等
   使用了fanout类型的路由器
4. 如何选择性地接收消息；使用了direct路由器
5. 如何通过多重标准接收消息；使用了topic路由器，可通过灵活的路由键和绑定键的设置，进一步增强消息选择的灵活性
6. 如何使用RabbitMQ实现一个简单的RPC系统；回调队列callback queue和关联标识correlation id

