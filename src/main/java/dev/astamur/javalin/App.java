package dev.astamur.javalin;

import com.mongodb.client.MongoClient;
import dev.astamur.javalin.config.Config;
import dev.astamur.javalin.config.Paths;
import dev.astamur.javalin.controllers.PostController;
import dev.astamur.javalin.controllers.impl.PostControllerImpl;
import dev.astamur.javalin.repositories.impl.PostRepositoryImpl;
import io.javalin.Javalin;

import static dev.astamur.javalin.config.Config.getMongoClient;
import static io.javalin.apibuilder.ApiBuilder.*;

public class App {
    private final Config config = new Config();
    private final MongoClient mongoClient;
    private final PostController postController;

    public App() {
        this.mongoClient = getMongoClient(config);
        this.postController = new PostControllerImpl(new PostRepositoryImpl(mongoClient.getDatabase(config.getDbName())));
    }

    public static void main(String[] args) {
        new App().startup();
    }

    public void startup() {
        Javalin app = Javalin.create(Config::configApp);

        app.routes(() -> {
            path(Paths.INDEX, () ->
                    get(ctx -> ctx.result("Hello!")));
            path(Paths.POSTS, () -> {
                get(postController::findAll);
                post(postController::create);
                path(Paths.ID_PARAM, () -> {
                    get(postController::find);
                    put(postController::update);
                    delete(postController::delete);
                });
            });
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            mongoClient.close();
            app.stop();
        }));

        app.start(config.getPort());
    }
}
