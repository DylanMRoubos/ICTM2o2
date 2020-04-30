package site.nerdygadgets.scraper;

import com.mongodb.client.*;
import org.bson.Document;

import java.time.Instant;

/**
 * Database class
 * connects to MongoDB database.
 * has a function to write server metrics to database.
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 30-04-2020
 */
public class Database {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public Database(String connectionString) {
        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase("nerdyGadgets");
        collection = database.getCollection("serverStatus");
    }

    public void createDocument(int serverId, ServerType serverType, String ip, boolean online, String cpu, String memory, String disk, String uptime) {
        Document lastdoc = getLastDocument();
        int id;
        // check if collection is empty. if so, set id to 1. otherwise, get last id and +1
        if (lastdoc == null) {
            id = 1;
        } else {
            id = lastdoc.getInteger("_id") + 1;
        }
        // build document for insert.
        Document doc = new Document("_id", id)
                .append("serverId", serverId)
                .append("serverType", serverType.name())
                .append("ip", ip)
                .append("timestamp", String.valueOf(Instant.now().getEpochSecond()))
                .append("status", new Document("online", online)
                    .append("cpu", cpu)
                    .append("memory", memory)
                    .append("disk", disk)
                    .append("uptime", uptime));
        collection.insertOne(doc);
    }

    public Document getLastDocument() {
        // get latest document
        return collection.find().sort(new Document("_id", -1)).first();
    }
}
