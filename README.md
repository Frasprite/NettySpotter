## Intro and goal

### Netty
It is a colloquial term which is used mainly in the north east of England.

It has been suggested that it is a shortened form of Italian _gabbinetti_ ‘toilets’, but no evidence has been found to support this.

Another theory suggests that the word is an alteration of necessary, after or directly from French _nettoyer_ ([source](https://blog.oxforddictionaries.com/2015/11/12/words-for-toilet/)).

### Spotter
A synonyms of _finder_ .

The goal of this project is to build a small app which lets people know where are the public toilets in Ile de France.

Basic features :

- A map showing all public toilets through a pin display and my location
- Pins show more details when the user clicks on it
- A view with the list of all public toilets
- A refresh mechanism to update the data
- Have access to the list of toilets without network

Bonus :

- Add as favorite some toilets and have access to this list
- Have a nice and funny design (like ​[SitOrSquat](https://play.google.com/store/apps/details?id=com.charmin.sitorsquat)​ for example)
- Present in a view the detail of the toilet and let the possibility to share it per sms
- Sort the list of toilets depending on my distance to it or the time it’s open
- Add tests (UI, unit, full coverage not necessary)
- Show us your git skills

### API web service
Refer to [RATP open API](https://data.ratp.fr/api/v1/documentation).

A sample of data can be found at this [link](https://data.ratp.fr/api/records/1.0/search/?dataset=sanisettesparis2011&rows=1000).

### Framework and library used so far
- Kotlin, Kotlin everywhere [Official site](https://kotlinlang.org/docs/reference/android-overview.html)
- Room, LiveData, ViewModel, Paging from [Android Architecture](https://developer.android.com/topic/libraries/architecture/)
- HTTP client for network operation [Retrofit](http://square.github.io/retrofit/)
- GSon for serialize / deserialize data from API [GSon](https://github.com/google/gson)

## Report

As introduced above, the goal of the app is to locate all public toilets (restricted to the region of Ile de France) and show them
near user position.

I've decided to create this app, dividing features in order of importance and based on their difficulty:

1. Create a view with a list of all public toilets
    1. The list takes data from DB, which is populated by network call from the given API
    2. Data loading will be done gradually
    3. Application should be responsive and show data even if there is no connection
    4. Data must be refreshed / updated at any time
2. Add a view showing all public toilets on maps
    1. Show with pins all toilets near users (based on user position)
    2. Open a view with contains more details on pin clicked
3. Create a view of details about the toilet
    1. Show only relevant data
    2. Add _add to favorite_ action
    3. Add _share with sms_ action
4. Other bonus point

Target SDK: 27; min SDK 21 (covering ~80% of Android devices).

### Project structure

- **api** : contains API calls, using Retrofit
- **data** : contains the repository class, responsible for triggering API requests and saving the response in the database
- **db** : contains database cache for network data
- **model** : contains the data model, which is also a table in the Room database; and NettySearchResult, a class that is used by the UI to observe both search results data and network errors 
- **rawmodel** : contains the raw data model used by Retrofit in order to deserialize JSON data returned by API
- **ui** : contains all classes concerning UI
- **viewmodel** : contains all classes designed to store and manage UI-related data

### Points analysis

#### Point 1

The main activity is the core of the application: the list of 'netties' will be loaded little by little, in a endless scroll list.
I've decided to test _Paging_ framework, which is a new library made by Google in order to load data gradually.
Data is stored on Room, which is an abstraction layer over SQLite.
The data is searched thanks to Retrofit library.
Honorable mention: **Mode View ViewModel** is the pattern chosen to bake the GUI. This is done thanks to all components of Android Architecture.
The hardest part on this point was implementing the paging logic, especially with the new Google library.
(It's a huge risk to implement an _unknown_ library, but it was worth it!)

#### Point 2

Maps UI was a new challenge for me, as I never worked with Google Maps library.
The project use only basic function and surely must be improved.

### TODO

Some features are missing and they will be done in the future:
- the refresh mechanism for data
- details UI about a public toilet (accessible from list and / or from maps) with relative action like share and add to favorite
- filter list based on options: favorite, closer netties, opening hours
- create more unit / instrumented test
- improve UI of the app (launcher icons, design etc, etc)
- search of public toilets could be done also in MapsActivity
- add cluster in order to group public toilets
- enable GPS directly from application
- add a voting system for netties
