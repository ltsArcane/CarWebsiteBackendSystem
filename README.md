I didn’t end up doing the “Make Your Project Stand Out” section — mostly because I spent more time than expected untangling some issues with WebClient. Since we hadn’t covered it before, I ran into a confusing problem where Spring Boot seemed to keep killing the client unexpectedly.

Turns out the issue was with how WebClient was being stored. If you store an actual WebClient instance in a bean, it breaks silently — no connections form. The fix was to inject the WebClient.Builder and only call .build() when needed. Took some time to track that down. 
(Reference: \vehicles-api\src\main\java\com\udacity\vehicles_api\VehiclesApiApplication.java)

Added some additional tests for the aforementioned web clients within the Vehicles API testing area.

PS: for the service discovery setup, I chose not to load balance or Eureka-discover the public API (localhost:9191 — the one hosting the Boogle Maps service). Instead, I only registered the pricing-service for load balancing. That said, I didn’t configure any actual load balancing, so it’s technically registered but not distributed across instances. Wasn't sure whether you'd like me to ensure boogle maps was discoverable or to be kept as just a public API.

PPS: I'm not 100% sure if the maven plugin will be able to package most of these (if that's how you test them), since Lombok oftentimes causes trouble if you don't configure the maven plugin to exclude it.

Other than that, when testing the vehicle API CRUD functionality, you "should" be able to run the POST via SwaggerUI as-is; I did some @Schema examples.

Let me know if you’d like me to revisit anything.

Cheers
