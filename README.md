# GreedyGameAssignment
The MusicWiki app contains information about music, including information of genres, artists, and albums, etc.

The app fulfills all the requirements specified in the assignment.

The application is based on MVVM (Model-View-ViewModel) architecture, which is suitable for such type of app and it is also recommended by Google.
Seperate viewmodels and repositories have been created for every activity for better seperation of concerns.

The RecyclerView has been used to display different lists in the application, because of the efficiency of the recycler view in reusing the views.

Glide library is used to load the images from the url because Glide provides the caching feature which saves time when repititive requests are done on the same url.

Hilt is used for the dependancy injection because it is built on top of Dagger and provide better functionality.

Data Binding has been implemented in the app which is helful in avoiding the use of findViewById() method thereby reducing the chances of null pointer exceptions.

Retrofit API library is used for handling the APIs because it also recommended by Google.
The responses for the apis are transformed into specific objects such as Success, Error, Loading using the Sealed class.

Many generic classes have been used in the application to avoid repititive code and make the code more maintainable.

Kotlin coroutine have been implemented in the application to handle background tasks of handling the api requests, because of its many features such as easy implementation, fewer memory leaks, etc. It is also recommended by Google.

One object class have been created named GenreNameProvider which is used to provide the genre name to thr fragments, setFragmentResultListener API could have been used but it facilitates communication between only one fragment and activity.
So, to achieve this, we set the value in the GenreNameProvider in activity and access it in the fragments.

The User Interface of the app has been majorly developed using the Constraint Layout bacause it the most appropriate layout for responsive UI and efficient in performance due to its flat hierarchy.
Tried to implement the test cases for the application components.
