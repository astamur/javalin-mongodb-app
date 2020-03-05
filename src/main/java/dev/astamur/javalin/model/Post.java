package dev.astamur.javalin.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String subject;
    private String text;
    private List<String> categories;
}