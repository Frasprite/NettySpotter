### Netty
It is a colloquial term which is used mainly in the north east of England.

It has been suggested that it is a shortened form of Italian _gabbinetti_ ‘toilets’, but no evidence has been found to support this.

Another theory suggests that the word is an alteration of necessary, after or directly from French _nettoyer_ ([source](https://blog.oxforddictionaries.com/2015/11/12/words-for-toilet/)).

### Spotter
A synonyms of _finder_ .

### Intro

The goal of this test is to build a small app which lets people know where are the public toilets in Ile de France.

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

Feel free to adopt the navigation you want, your favorite frameworks to make it the app **"I’ll keep on my Android"** phone :) .

### API web service
Refer to [RATP open API](https://data.ratp.fr/api/v1/documentation).

A sample of data can be found at this [link](https://data.ratp.fr/api/records/1.0/search/?dataset=sanisettesparis2011&rows=1000).

### Report

As introduced above, the goal of the app is to lists all public toilets near user position (restricted to the region of Ile de France).

I've decided to create this app, adding basic features step by step, in order of importance:

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

### Framework and library used so far
- Room, LiveData, ViewModel, Paging from [Android Architecture](https://developer.android.com/topic/libraries/architecture/)
- HTTP client for network operation [Retrofit](http://square.github.io/retrofit/)