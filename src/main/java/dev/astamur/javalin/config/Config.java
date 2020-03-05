package dev.astamur.javalin.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.javalin.core.JavalinConfig;
import lombok.Data;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.eclipse.jetty.http.MimeTypes;

import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Data
public class Config {
    private int port;
    private int mongoPort;
    private String mongoHost;
    private String dbName;

    public Config() {
        init();
    }

    public void init() {
        ProcessBuilder processBuilder = new ProcessBuilder();

        port = Integer.parseInt(processBuilder.environment().getOrDefault("APP_PORT", "8080"));
        mongoPort = Integer.parseInt(processBuilder.environment().getOrDefault("MONGO_PORT", "27017"));
        mongoHost = processBuilder.environment().getOrDefault("MONGO_HOST", "localhost");
        dbName = processBuilder.environment().getOrDefault("DB_NAME", "app");
    }

    public static void configApp(JavalinConfig config) {
        config.defaultContentType = MimeTypes.Type.APPLICATION_JSON_UTF_8.asString();
    }

    public static MongoClient getMongoClient(Config config) {
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        return MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(List.of(new ServerAddress(config.getMongoHost(), config.getMongoPort()))))
                .codecRegistry(codecRegistry)
                .build());
    }
}
