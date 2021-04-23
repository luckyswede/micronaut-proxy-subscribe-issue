Start server
```
./gradlew run
```
Run
```
curl localhost:8080/proxy -H "content-type: application/json" -d '["sdfsdf"]' -XGET
```
which works

Now do a POST, PUT, or DELETE, eg 
```
curl localhost:8080/proxy -H "content-type: application/json" -d '["sdfsdf"]' -XPOST
```
which fails