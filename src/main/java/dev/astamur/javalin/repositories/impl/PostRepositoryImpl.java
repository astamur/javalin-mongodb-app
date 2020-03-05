package dev.astamur.javalin.repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import dev.astamur.javalin.model.Post;
import dev.astamur.javalin.repositories.PostRepository;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class PostRepositoryImpl implements PostRepository {
    private static final String COLLECTION_NAME = "posts";
    private static final FindOneAndReplaceOptions UPDATE_OPTIONS = new FindOneAndReplaceOptions()
            .returnDocument(ReturnDocument.AFTER);

    private final MongoCollection<Post> posts;

    public PostRepositoryImpl(MongoDatabase database) {
        this.posts = database.getCollection(COLLECTION_NAME, Post.class);
    }

    @Override
    public void create(Post post) {
        post.setId(new ObjectId());
        posts.insertOne(post);
    }

    @Override
    public Post find(String id) {
        return posts.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public List<Post> findAll() {
        List<Post> result = new LinkedList<>();

        for (Post post : posts.find()) {
            result.add(post);
        }

        return result;
    }

    @Override
    public Post update(Post post, String id) {
        return posts.findOneAndReplace(new Document("_id", new ObjectId(id)), post, UPDATE_OPTIONS);
    }

    @Override
    public void delete(String id) {
        posts.deleteOne(new Document("_id", new ObjectId(id)));
    }
}
