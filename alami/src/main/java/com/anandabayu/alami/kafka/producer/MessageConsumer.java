package com.anandabayu.alami.kafka.producer;

import com.anandabayu.alami.dao.HistoryRepository;
import com.anandabayu.alami.entity.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class MessageConsumer {

    @Autowired
    private HistoryRepository historyRepository;

    public CountDownLatch latch = new CountDownLatch(3);

    @KafkaListener(topics = "historyTopic", groupId = "group_id", containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {

        historyRepository.save(new History(message, new Date()));

        System.out.println("Received Message in group 'group_id': " + message);
        latch.countDown();
    }
}
