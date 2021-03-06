# Project 2 - *News Search*

**News Search** is an android app that allows a user to search for articles on web using simple filters. The app utilizes [New York Times Search API](http://developer.nytimes.com/docs/read/article_search_api_v2).

Time spent: **28** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for news article** by specifying a query and launching a search. Search displays a grid of image results from the New York Times Search API.
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
* [x] User can configure advanced search filters such as:
  * [x] Begin Date (using a date picker)
  * [x] News desk values (Arts, Fashion & Style, Sports)
  * [x] Sort order (oldest or newest)
* [x] Subsequent searches have any filters applied to the search results
* [x] User can tap on any article in results to view the contents in an embedded browser.
* [x] User can **scroll down to see more articles**. The maximum number of articles is limited by the API search.

The following **optional** features are implemented:

* [x] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
* [x] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
* [x] User can **share an article link** to their friends or email it to themselves
* [x] Replaced Filter Settings Activity with a lightweight modal overlay

The following **bonus** features are implemented:

* [x] Use the [RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) with the `StaggeredGridLayoutManager` to display improve the grid of image results
* [x] For different news articles that only have text or only have images, use [Heterogenous Layouts](http://guides.codepath.com/android/Heterogenous-Layouts-inside-RecyclerView) with RecyclerView
* [x] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [x] Leverages the [data binding support module](http://guides.codepath.com/android/Applying-Data-Binding-for-Views) to bind data into layout templates.
* [x] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.com/android/Drawables#vector-drawables) where appropriate.
* [x] Replace Picasso with [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
* [x] Uses [retrolambda expressions](http://guides.codepath.com/android/Lambda-Expressions) to
cleanup event handling blocks.
* [x] Leverages the popular [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.
* [x] Leverages the [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit) to access the New York Times API.
* [x] Replace the embedded `WebView` with [Chrome Custom Tabs](http://guides.codepath.com/android/Chrome-Custom-Tabs) using a custom action button for sharing. (_**2 points**_)

The following **additional** features are implemented:

* [x] Added CardView for each article
* [x] Better request handling: prevented the state in which a request is made with both a query and a news desk value (the Article Search API returns nothing if both are added to the request)
* [x] Added RequestInterceptor to automatically add API key to every request
* [x] Added GSON for every part of the Article Search API (Article class is a wrapper for the relevant information, but each part of the API has a corresponding Retrofit class)
* [x] Used Butterknife for view binding
* [x] Added top news for the day upon load for better UX
* [x] Added ProgressBar to indicate to the user that the app is loading a new article list for
better UX
* [x] Used BuildConfig to store API key instead of storing in GitHub for good best practices
* [x] Added Snackbar to provide user feedback upon loading during endless scroll for better UX
* [x] Added an empty view to display when the articles list is empty/no articles are found or when the network is down for
better UX
* [x] Added swipe to refresh for reloading article query
* [x] Used BroadcastReceiver to monitor network connectivity
* [x] Added custom fonts to for better UI

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/KyF4J94.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

- Endless scrolling was painful when introducing pagination. It wasn’t clear to me at the beginning that I needed to create an instance variable to track the pagination, because I thought I could rely on the ‘page’ parameter passed into the onLoadMore() callback function to increment with each load/call.

- I would like to experiment with an alternate architecture (as opposed to MVC). When working on the data binding part of the spec, it was painful to introduce binding to the majority of the codebase (exception: my ArticlesAdapter), because it wasn’t organized using MVVM.

- In general, I had some fairly ambitious ideas for how I wanted to design the UX of the application, and ended up having to do away with the more time-consuming ones as the deadline approached.

- Monitoring the network and using network state to provide feedback to the user about whether or not the app could retrieve articles was easy. The APIs that Android provides for developers to interact with networking concerns is simple to follow.

- I had to deal with a lot of Gradle bugs, and ended up downgrading from 2.3 -> 2.2.3 as a
solution.

- Retrofit combined with OkHttp: where have you been all my life? The unobtrusive way Retrofit handles optional query params saved me a lot of pain (I imagined I’d have to write tons of methods to make my GET requests vs being able to use just one).

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- Glide
- Butterknife
- OkHttp
- Retrofit

## License

    Copyright [2017] [Deonna Hodges]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
