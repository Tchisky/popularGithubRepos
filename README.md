Overiew:

	Android app that list all popular GitHub repositories, lists name, description, stars, owner name and owner avatar related to each repository.


Technologies/Libraries used and why:

	- Android Architecture Component MVVM:		more rebust code, and better structure
	- Paging Library:							to load data small chunks of data a time, rather than load all data at once
	- Glide:									to load the repository owner avatar
	- Volley:									to make network requests to GitHub API, since the response has load of fields and we only interested at some of them(name,description,stars,owner name,owner avatar) its better to use Volley StringRequest then parse the request to json object then extract only the fields needed, better than using Retrofit with JsonConverter which forces you to create class that contains all fields of the response, means more memory allocated.
	- Material Design: 							to use some components such as BottomNavigationView
	- ViewModel:								as part of Android Architecture Component, will contains all app background process, retrieve data, loading data, caching data, and also handle changes of activity/fragment lifecycle
	- Room Database								part of Android Architecture Component as well, to cache retrieved data from the API
	- CirculeImageView:							To display the repository owner avatar
	- ButterKnife								To simplify binding and unbinding views
	- LiveData									part of Android Architecture Component as well, to continuesly watch data in cache database and instantly load it if any changes happened
	- Fragment 									To make UI components independent of each other(mainly for trending repos page and settings page)

How it works:
	
	first at start of the app, it checks whether there is cached data in the local room database, if there is some, the it loads it N item per page, if no data is cached in the database then, it checks if there is an active internet connection to retrieve data from the GitHub API, if so them it makes network requests using Volley to get data, but before each request the page number that will be passed to the API is inceremented, then each request retrieves 30 item per page, each request is delayed by 1000 ms(1 second) to avoid getting blocked by the API, the data received is immediately inserted into the database, since we have liveData watching and listening on any changes on the database, it immediately loads it as PagedList into PagedListAdapter which handle displaying data in RecyclerView.
	Trending repos page is a fragment as well as the settings.


Features:

	- the more you scroll the more data is loaded
	- swipe each item left or right to delete
	- settings page which allower you to:
		- clear all cached data
		- change how many items to load per page from the local cache

Project Structure:

	- Model:								// contains the repository class and its builder
		- Repo.java 						// repository class
		- RepoBuilder.java 					// builder for the repository class, to simplify creating object when the constructor is too long
	- repository:							// contains classes that will be responsible of retrieving and caching data
		- Dao:								// contains the database access object(dao) which directly query the database
			- RepoDao.java 					// dao
		- Database:							// database class
			- RepoDatabase.java 
		- service:							// github service, that retrieves data from github API
			- GitHubService.java
		Repository.java 					// this is not Github Repository class xD, its the class responsible of retrieving data whether from cache or API
	- UI:
		- Activities:
			- Main.java 					// home activity that will display the 2 fragments below 				
		- Fragments:
			- Settings:
				- SettingsFragment.java 	// settings fragment
			- TrendingRepositories:
				- TrendingReposFragment.java // trending repositories fragment
		- Recycler:
			- RepoPagedAdapter.java 		//  adapter for the recyclerView
		- ViewHolder:
			- RepoHolder.java 				// of each item in the adapter
	- Utiliy:
		- Utility.java 						// contains static methods and constants that will be used by other classes
	- ViewModel:
		- RepoViewModel.java 				// the class responsible from watch data and handle activity/fragment lifecycle