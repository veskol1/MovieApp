
# Movie App

An application that displays the latest movies.

## General architecture of the application.
Used clean architecture combaining MVI and MVVM.

(UI Layer) holds elements(compose screens) and state holders:

- #### MovieViewModel: (MainScreen and MovieScreen)
    Geting movies data from MovieRepository(Data layer) depending on filter.

- #### FavoriteMovieViewModel (FavoritesScreen):
    Saving data for (Data layer) LocalMovieRepository using Room database.


## Features I didn't implement.
- Preview for composables (if I had more time)
- Domain Layer - According to android documentation: "The domain layer is responsible for encapsulating complex business logic, or simple business logic that is reused by multiple ViewModels", I realy didn't found it relevant in this project (maybe I'm missing some details that is real world won't happen :) . 
- Some UI Fixes and additions, like the dropdown(filter movie)..
