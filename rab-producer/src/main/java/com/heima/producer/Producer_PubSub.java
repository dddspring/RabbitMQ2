package com.heima.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_PubSub {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2设置参数
        connectionFactory.setHost("47.96.178.51");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/testRabbit");
        connectionFactory.setUsername("mona");
        connectionFactory.setPassword("mona");
        //3创建连接connection
        Connection connection = connectionFactory.newConnection();
        //4创建channel
        Channel channel = connection.createChannel();


        //5创建交换机
        String exchangeName = "test_fanout";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT,true,false,false,null);

        //6创建队列
        String queneName1 = "test_fanout_queue1";
        String queneName2 = "test_fanout_queue2";

        channel.queueDeclare(queneName1,true,false,false,null);

        channel.queueDeclare(queneName2,true,false,false,null);


        //7绑定交换机和队列
        channel.queueBind(queneName1,exchangeName,"");
        channel.queueBind(queneName2,exchangeName,"");

        //8发送消息
        String mes = "这是日志消息";
        channel.basicPublish(exchangeName,"",false,null,mes.getBytes());

        //9释放资源
        channel.close();
        connection.close();


    }
}
