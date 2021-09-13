package com.heima.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_WorkQueues {
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

        //接收消息

        //String var1, boolean var2,, CancelCallback var3)
        Consumer c = new DefaultConsumer(channel){
            //回调方法，接收到消息自动执行
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                /*System.out.println("consumerTag"+ consumerTag);
                System.out.println("Exchange"+ envelope.getExchange());
                System.out.println("RoutingKey"+ envelope.getRoutingKey());

                System.out.println("properties"+ properties);*/
                System.out.println("body"+ new String(body));

            }
        };
        channel.basicConsume("WorkQueues",true,c);

        //关闭资源？不需要
    }
}
