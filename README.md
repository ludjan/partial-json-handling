# partial-json-handling

In this project, I made an attempt to write some Kotlin handling partial json responses.

Imagine you want a user's username from an endpoint in order to render a greeting message.
What if you didn't have to wait for the whole json to be available before you updated your UI.
(Most servers give send complete responses though - but implementing some kind of protocol for sending quick data first could be done)

```json
{
  "user": {
    "username": "ExampleUser",
    "activityLog": [
      {
        "id": "123123123",
        "message": "It did a thing",
      },
      ...
    ]
  }
}
```
The idea here was just to create a JsonResponseStreamHandler, which knows what functions to trigger when different parts of a json object becomes available. I probably won't write a protocol for actually streaming in this way, since one tricky problem would be for the client to know if the server is done sending its response.

I also wrote a drafty DSL for using the JsonResponseStreamHandler, which I think might have some potential:
```kotlin

@Serializable
data class User(val username: String, val activityLog: List<ActivityLogEntry>)

val someUi = SomeUi()
val handler = JsonResponseStreamHandler
handler.registerHandlers {
    // when we successfully parse a String on this json path, trigger this function with the string as parameter
    on<String>("user.username") { someUi.updateUi(it) }
}
```

But I guess it's tricky to know when the handler should die.