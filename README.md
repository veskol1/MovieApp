
# Movie App

An application that displays the latest movies.

## General architecture of the application.
Used clean architecture combaining MVI and MVVM.

(UI Layer) holds elements(compose screens) and state holders:

- #### MovieViewModel: (MainScreen and MovieScreen)
    Geting movies data from MovieRepository(Data layer) depending on filter.

- #### FavoriteMovieViewModel (FavoritesScreen):
    Saving data for (Data layer) LocalMovieRepository using Room database.

