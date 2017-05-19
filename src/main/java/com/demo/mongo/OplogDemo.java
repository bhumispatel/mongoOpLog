package com.demo.mongo;

import com.mongodb.Block;
import com.mongodb.CursorType;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.ne;

public class OplogDemo {
    public static void main(String[] args) {

        try(MongoClient mc = new MongoClient()) {

            final FindIterable<Document> documents = mc.getDatabase("local")
                    .getCollection("oplog.rs").find().filter(and(ne("op","n"),ne("op", "c"))).
                            sort(new Document("$natural", 1)).cursorType(CursorType.TailableAwait);

            documents.forEach((Block<Document>) document -> {
                System.out.println(document);
            });
        }
    }
}

//filter(and(ne("op", "n"),eq("o.name","Fred")))
