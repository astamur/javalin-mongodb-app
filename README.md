# An example of web-application using [Javalin](https://javalin.io/) and [MongoDB](https://www.mongodb.com/)

### Build
To compile and build artifacts run a following gradle command _(Docker should be installed on your machine)_:
```
./gradlew build buildDockerImage
```

### Run and test
To see the results you may start application and database by using `docker-compose`:
```
docker-compose up -d
``` 
To stop and delete this stack execute:
```
docker-compose down
```

### Requests examples
Create new post:
```
curl -v localhost:8080/posts -d '{"subject": "Greeting post", "text": "Hello! This is a greeting post.", "categories": ["other"]}'
```

Read existing post:
```
curl localhost:8080/posts/{ID}
```

Update existing post:
```
curl -X PUT -v localhost:8080/posts/{ID} -d '{"subject": "Greeting post. UPDATED", "text": "Hello! This is a greeting post. UPDATED", "categories": ["new_one"]}'
```

Delete existing post:
```
curl -X DELETE -v localhost:8080/posts/{ID}
```