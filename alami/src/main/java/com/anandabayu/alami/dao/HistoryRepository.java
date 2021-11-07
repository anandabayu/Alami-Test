package com.anandabayu.alami.dao;

import com.anandabayu.alami.entity.History;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<History, String> {
}
