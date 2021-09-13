package com.heima.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer_WorkQueues {
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
        //5创建quene
        //String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare("WorkQueues",true,false,false,null);
        //6发送消息
        //String exchange, String routingKey, BasicProperties props, byte[] body
        for (int i = 1; i <= 10 ; i++) {
            String body = i+"hellohello---------";
            channel.basicPublish("","WorkQueues",null,body.getBytes());

        }

        //7释放资源
        /*channel.close();;
        connection.close();*/
    }
}
