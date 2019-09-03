# DeliveryApp

This application shows a list retrieved from the API. The list contains the deliveries and each delivery can be clicked to get the details with the location marked on the map

## Implemented things

Repository is written in `Kotlin` and is based on `MVVM(ViewModel, LiveData)`
Dependency Injection is implemented using `Dagger2`
Caching is implemented using `Room Database`
Data can be refreshed using Pull to refresh
In case api fails, user can retry for fetching data again
Data binding using `Data Binding`

## Unit Testing

UI and Unit testing is achieved with `Espresso`, `Mockito` and `MockWebServer`

### Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading
* [Espresso][espresso] for Android UI tests
* [Mockito][mockito] for evaluating app's logic using local unit tests
* [MockWebServer][mockwebserver] for testing HTTP clients


[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[support-lib]: https://developer.android.com/topic/libraries/support-library/index.html
[arch]: https://developer.android.com/arch
[data-binding]: https://developer.android.com/topic/libraries/data-binding/index.html
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[glide]: https://github.com/bumptech/glide
[espresso]: https://developer.android.com/training/testing/espresso
[mockito]: https://site.mockito.org/
[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver

