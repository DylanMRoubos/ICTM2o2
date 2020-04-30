package site.nerdygadgets.scraper;

import com.mongodb.client.*;
import org.bson.Document;

import java.time.Instant;

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
        if (lastdoc == null) {
            id = 1;
        } else {
            id = lastdoc.getInteger("_id") + 1;
        }
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
