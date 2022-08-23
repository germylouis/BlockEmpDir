# Block Interview - Employee Directory Mobile Application

## Build tools & versions used

## Steps to run the app
1. Ensure a connection to the internet.
2. Download application on Android device.
3. Run application on Android versions 8+ or API level 26+.

## What areas of the app did you focus on?
I focused a lot on architecture for this application more so than the user interface.

I did so by utilizing the repository pattern by creating a retrofit interface as the DataSource for the list of employees, creating a Repository specifically for that DataSource with its own interface and implementation. I also created a Network Helper class to handle errors upon the response from the retrofit call. My ViewModel calls on the Repository method that returns a converted DTO object to a list of a model class. That list is used to gather the complete list of employees using MutableStateFlow. The flow object from the ViewModel is then consumed by observers within the MainActivity where the employees areas displayed using a recycler view.

The user interface is a simple recycler view, with each element of the ListView being contained in a CardView. This CardView displays the employees image, name, email, team and their biography.

## What was the reason for your focus? What problems were you trying to solve?
I focused a lot on the architecture so that if I were to iterate upon it, so that I could deal with a larger variety of scenarios, then the application would  be able to scale. This was the idea.

I was trying to solve the issue of decoupling my network calls with the domain specific use cases. For the most part, I  think that goal was achieved.

## How long did you spend on this project?
I spent about 12-15, spread over 3-4 days.


## Did you make any trade-offs for this project? What would you have done differently with more time?
I could not complete the implementation of error handling, swipe refresh or image cacheing. I have little to no experience with either. This was the main reason why I spent a great deal of time on this project. I had to learn how to best implement these features with my specific use case.

With more time I would first tackle the error handling and how the UI responds to different states. Then implement the refreshing of the employee list. Then focus on cacheing to optimize the application. Focusing on the user experience first is most important in my opinion.

## What do you think is the weakest part of your project?
Testing. This was the first time I created unit tests for my personal apps. I learned a lot.

## Did you copy any code or dependencies? Please make sure to attribute them here!
NetworkHelper.kt, ResultWrapper.kt:  https://medium.com/@douglas.iacovelli/how-to-handle-errors-with-retrofit-and-coroutines-33e7492a912

LatestEmployeesUiState.kt, Parts of EmployeeViewModel.kt, and EmployeeDirectoryActivity.kt: https://developer.android.com/kotlin/flow/stateflow-and-sharedflow

employee_list_view.xml: https://www.ronnystudio.com/article-screen-using-cardview-list-with-free-android-xml-layout/

Most other features if not all were custom made by myself or small code snippets of my previous work that can be found on my GitHub account.
## Is there any other information you’d like us to know?
I learned a lot creating this project. Maybe my most involved personal mobile application. I learned about unit testing with Kotlin, using different coroutines for testing and network calls, learned more about different flows like MutableStateFlow, StateFlow, and Flows, learned about network error handling, the difference between DTOs and model classes along with the repository pattern in general. I grew a lot as an Android Developer and am excited to discuss that with you!
