# Bookzie

## User Stories (Unit 12)
The following functionality is completed:

Confirm Transactions Page 
- [X] View Book Cover from Books API
- [X] View information obtained from Books API (title, category) & price from seller
- [X] User can view Seller & Buyer information
- [X] User can select meeting location from a list of buildings on campus & set time of meeting
- [X] User can click the "Submit" Button to confirm transaction and view "Meeting Confirmation Screen"

Meeting Confirmation Page
- [X] User can view Seller & Buyer information
- [X] User can see book info (Title, Price)
- [X] User can see meeting location

Single Transaction Page
- [X] View Book Cover from Books API
- [X] View information obtained from Books API (title, category, description) & price from the seller
- [X] See list of requesters if applicable or waiting for seller response



## Video Walkthrough (Unit 12)

Here's a walkthrough of implemented user stories:

<img src='walkthrough.gif' title='Video Walkthrough' width='350' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).



---


## User Stories (Unit 11)

The following functionality is completed:

All Transaction Page
- [X] Create a recycler view of pending and completed(bought/sold/cancelled) transactions with last five transactions showing at minimum if present
- [X] Show the buyer and seller associated with the transaction
- [X] Show the book title, ISBN and price associated with each transaction

Individual Listing (Book Details) Page
- [X] View Book Cover from Books API
- [X] View information obtained from Books API (title, description, category)
- [X] View information entered from seller (ISBN, condition, images of book & price)
- [X] Button shown to request the book

All Listing Page
- [x] Create a recycler view to show all posts from the posts table
- [x] Post shows important information (Book Title, ISBN, Price, Username of the seller, Books image of the book)
- [x] Posts can be filtered from a search bar that accepts book titles or ISBNs
- [x] Posts can be sorted by price (low to high or high to low)

## Video Walkthrough (Unit 11)

Here's a walkthrough of implemented user stories:

<img src='walkThroughMilestoneTwoDraftOne.gif' title='Video Walkthrough' width='350' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).



---



## User Stories (Unit 10)

The following functionality is completed:

Create Login/Register Page
- [X] User is able to input the username
- [X] User is able to input a password
- [X] User is authenticated through parsler and directed to home page
- [X] User is able to register an account if they are a new user

Configure Back4App
- [X] Create Database
- [X] Create Login Table
- [X] Create Posts Table
- [X] Create Users Table
- [X] Create Transactions Table
- [X] Create Requests Table

Create User Profile
- [X] User can edit profile pic.
- [X] User can edit classOf and major
- [X] Page displays username, major and classOf
- [X] Page displays all recent posts they have created in a recycler view

Create Listing
- [X] User uses camera to take front and back image of book
- [X] Get information entered from seller (ISBN, condition, price)
- [X] User submits post


## Video Walkthrough (Unit 10)

Here's a walkthrough of implemented user stories:

<img src='walthroughTrialFOUR.gif' title='Video Walkthrough' width='350' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).


## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)

## Overview
### Description
An online marketplace for students on campus, that makes it easy, cheap and accessible for students to buy and sell new or used books.

### App Evaluation
- **Category:** Education
- **Mobile:** This app would be primarily developed for mobile.
- **Story:** Allows users to either view all books for sale in there area, or allows them to sell books to users in there area.
- **Market:** Main market is college students.
- **Habit:** This app can be used a lot by students who have finished clases and no longer need specific textbooks, can also just be used to sell items periodically.
- **Scope:** A marketplace where students can sell books, and potentially dorm items or whatever they no longer need that might be of use to another student. The extended long-term plan is to make this app the most populer option for selling items on campus.

## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User logs in to access the account
* User can post a textbook with image, price & details
* User can view book details (price,name etc) and compare prices from seller, Amazon/Google
  
  https://developers.google.com/books
 
  https://openlibrary.org/dev/docs/api/books
* User can view same textbooks from different sellers (RecyclerView)
* User can search for speific textbooks through ISBN/Title
* User can request to purchase textbook
* Seller can select buyer from list (users who requested that textbook)
* Implement Google MAPs API to determine meeting location of buyer and seller 
  
  https://developers.google.com/maps
* Buyer and Seller can reschedule/relocate/cancel transaction 
* User can view seller profiles (show ratings and what they are currently selling)
* User can view pending/completed transactions 

**Optional Nice-to-have Stories**

* Connection to Venmo
 
  https://stripe.com/docs/payments/payment-links/api
* Expand the scope of app beyond textbooks 
* User rates sellers
* Q/A on Seller Posts

### 2. Screen Archetypes

* Login - User logs into their account
* Register - User creates their own account
* Profile Screen 
   * Shows a complete list of past and current listings
   * Optional: shows user's rating from previously completed transactions
* Edit Profile Screen
   * Allows user to upload a photo and fill in information about them (Name, Major, Graduation Year)
* Create Listing Screen.
   * Allows user to be able to upload photos of the item being sold, list important information (ISBN, price, book dimensions, number of pages, class book is relevant to). 
   * Show price of the textbook at online retailers (Amazon/Google)
* Individual Listing Screen
   * Lets user view the item being sold showing the important information (ISBN, price, book dimensions, number of pages, class book is relevant to). 
   * Compares the price listed by the seller with the prices listed on online retailers (Amazon/Google)
   * The user is able to request the book for purchase
   * Optional: The user (buyer) is able to ask questions the seller can respond to about the listing
   * Optional: The user (seller) is able to answer questions the buyer asks about the listing
* All Listing Screen
   * Lets user view the list of items being sold 
   * Each listing shows its name, small image of the item, the seller's username/name, and price of the item being sold
   * User can filter through the list by price (low to high, high to low)
   * User can search through the list by name, or ISBN
   * Optional: User can filter through the list by seller rating (above # stars)
* All Transaction Screen 
   * User can see a list of pending and completed(bought/sold/cancelled) tranactions
   * User can filter through the list by pending or completed (bought/sold/cancelled) tranactions, by name, by price
   * Upon selecting a transaction a detailed view of the transaction page will appear
* Single Transaction Screen
   * User can see the details of the item being sold (price, ISBN, page count)
   * User can see the list of users that requested to purchase the listing item (Buyer name, time requested)
* Confirm Transaction Screen 
   * Set meeting location from a list of buildings on campus (campus center, bookstore, coffee shop, library, etc)
   * Optional: User can select the form of payment for transaction (Cash/Venmo)
* Meeting Confirmation Screen
   * User can see the details of the meet up for listing purchase (time, location, buyer information, seller information, item being sold, price)
   * User should see a map view of the meeting location
   * User should have the option to reschedule the meeting to a different location on campus or a different time
   * User should have the option to cancel the transaction
* Optional: Settings Screen
   * User should be able to change user profile details (Name, Major, Class of, photo)
   * User can change view preferences between dark/light mode
   * Optional: User can change app theme by selecting different colors

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Listing Screen
* New Listing Screen
* Transaction Listing Screen
* Profile Screen

Optional:
* Settings Screen

**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Account creation if no log in is available
* All Listing -> Jumps to Individual Listing
* All Transaction -> Jumps to Single Transaction
* Single Transaction -> Jumps to Confirm Transaction Details
* Confirm Transaction Details -> Jumps to Meeting Confirmation
* Profile (Current User Edits)-> Jumps to Edit profile 
* Profile (Not Current User) -> Jumps to Individual Listing
* Profile (Current User) -> Jumps to Single Transaction
* Optional: Settings -> Toggle settings

## Wireframes
<img src="https://github.com/NSR-Studios/CampusTextbookConnect/blob/main/wireframe3.PNG" width=800><br>

### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/NSR-Studios/CampusTextbookConnect/blob/main/wireframe1.PNG" width=800><br>
<img src="https://github.com/NSR-Studios/CampusTextbookConnect/blob/main/wireframe2.PNG" width=800><br>

### [BONUS] Interactive Prototype
![](https://github.com/NSR-Studios/CampusTextbookConnect/blob/main/GifFinal.gif)

## Schema 
### Models
#### Post
![](https://github.com/NSR-Studios/Bookzie/blob/main/Post.PNG)
![](https://github.com/NSR-Studios/Bookzie/blob/main/User.PNG)
![](https://github.com/NSR-Studios/Bookzie/blob/main/Transactions.PNG)
![](https://github.com/NSR-Studios/Bookzie/blob/main/Requests.PNG)

### Networking
#### List of network requests by screen
   - ***All Listings Screen***
      - (Read/GET) Query all posts
         ```java
         
         protected void queryPosts() {
              ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
              //get all the posts 
              query.include(Post.KEY_USER);
              query.setLimit(20);
          //To Do: Add filters here
              query.addDescendingOrder(Post.KEY_CREATED_KEY);
              query.findInBackground(new FindCallback<Post>() {
                  @Override
                  public void done(List<Post> posts, ParseException e) {
                      if (e != null) {
                          Log.e(TAG,"Issue with getting posts",e);
                      }
                      for (Post post : posts) {
                          //log (post.getUser.getUsername(), post.getISBN(), post.createdAt(), post.getfrontImage(), post.getbackImage etc.)
                      }
                      addAll(posts);
                      //adapter.notifyDataSetChanged();
                  }
              });
          }
                  }
               }
         ```
   - ***Individual Listings Screen***
      - (Read/GET) Query individual listing
      - ```java
             protected void queryPostIndividual() {
            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
            //To do: get individual listing
            //query.include(Post.KEY_USER);
            query.setLimit(20);
        //To Do: Add filters here
            query.addDescendingOrder(Post.KEY_CREATED_KEY);
            query.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> posts, ParseException e) {
                    if (e != null) {
                        Log.e(TAG,"Issue with getting posts",e);
                    }

                    addAll(posts);
                    //adapter.notifyDataSetChanged();
                }
            });
        }
            ```

   - ***All Transactions Screen***
      - (Read/GET) Query all completed transactions
     ```java
        protected void queryTransactionsCompleted() {
        ParseQuery<Transactions> query = ParseQuery.getQuery(Transactions.class);
        //get all the transactions
        query.include(Transactions.KEY_USER);
        query.setLimit(20);
		//To Do: Add filters here for completed ones 
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Transactions> transactions, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting completed transactions",e);
                }
                for (Transactions transaction : transactions) {
                    //To do 
					
                addAllT(transactions);
                //adapter.notifyDataSetChanged();
            }
        });
     ```
	
	
      - (Read/GET) Query all pending transactions
     ```java
        protected void queryTransactionsPending() {
        ParseQuery<Transactions> query = ParseQuery.getQuery(Transactions.class);
        //get all the transactions
        query.include(Transactions.KEY_USER);
        query.setLimit(20);
		//To Do: Add filters here for pending ones 
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Transactions> transactions, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting pending transactions",e);
                }
                for (Transactions transaction : transactions) {
                    //To do 
					
                addAllT(transactions);
                //adapter.notifyDataSetChanged();
            }
        });```
	
	


   - ***Single Transaction Screen***
      - (Read/GET) Query requests table for list of requests for book
      - ```java
           protected void queryRequests() {
          ParseQuery<Requests> query = ParseQuery.getQuery(Requests.class);
          //To do: add query for requests table and add the filters
          //query.include(Requests.KEY_USER);
          query.setLimit(20);
          query.addDescendingOrder(Requests.KEY_CREATED_KEY);
          query.findInBackground(new FindCallback<Requests>() {
              @Override
              public void done(List<Requests> requests, ParseException e) {
                  if (e != null) {
                      Log.e(TAG,"Issue with getting requests",e);
                  }
                  addAll(requests);
                  //adapter.notifyDataSetChanged();
              }
          });
      }```
    
 
   - ***Confirm Transactions Screen***
      - (Update/PUT) Requests
      ```java
          private void saveRequest(//Parameters) {
        Request request = new Request();
        request.setDescription(description);
        request.setImage(new ParseFile(photoFile));
        request.setUser(currentUser);
        request.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Error while saving",e);
                    Toast.makeText(getContext(),"Error while saving!",Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG,"request save was successful!!");
         
            }
        });
    }```
    
    
   
   - ***Create Listing Screen***
      - (Create/POST) Create a new post object
      ```java
          private void savePost(String description, double price, String ISBN, String class, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setDescription(description);
		//To DO Set all Values of new post 
		post.setPrice(price);
		post.setISBN(ISBN);
		post.setClass(class);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Error while saving",e);
                    Toast.makeText(getContext(),"Error while saving!",Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG,"Post save was successful!!");
				//To Do --> set the widgets (editText, image etc here)
                //etDescription.setText("");
                //ivPostImage.setImageResource(0);
                //pb.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }```
   
   - ***Profile Screen***
      - (Read/GET) Query logged in user object
      ```java
      System.out.println("Hello");
      rotected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        //get all the  where logged in user = current user
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Issue with getting posts",e);
                }
                for (Post post : posts) {
                    //TO DO 
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        }); }
      ```
   
      - (Update/PUT) Update user profile image and info
      ```java
      public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                try {
                    //Save to current user the profile pic
                    ParseUser.getCurrentUser().put("profilePic", new ParseFile(photoFile));
                    ParseUser.getCurrentUser().save();
					
		    //To DO: Save the other profile information
                    // Set the profilePic ImageView as this new Image
                    //String url = ParseUser.getCurrentUser().getParseFile("profilePic").getUrl();
                    //Glide.with(getContext()).load(url).circleCrop().into(profilePic);
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Error saving image", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return;
            }
            Toast.makeText(getContext(), "Error taking image", Toast.LENGTH_SHORT).show();
        }
    }```
      
   -  ***Meeting Confirmation Screen***
      - (Update/PUT) Change time, location or cancel transaction
      ```java
          public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                try {
                    // To DO: Save the new time or location or cancel transaction
                    ParseUser.getCurrentUser().put("newTime", new ParseFile(        ));
                    ParseUser.getCurrentUser().save();

         
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Error saving", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                return;
            }
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }```

#### [OPTIONAL:] Existing API Endpoints
![](https://github.com/NSR-Studios/Bookzie/blob/main/books.PNG)
![](https://github.com/NSR-Studios/Bookzie/blob/main/maps.PNG)
![](https://github.com/NSR-Studios/Bookzie/blob/main/Venmo%20API.PNG)


