package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Post {
    private int postID;
    private String postTitle;
    private String postBody;
    private ArrayList<String> postTags = new ArrayList<>();
    private String postType;  // "Very Difficult", "Difficult", or "Easy"
    private String postEmergency;  // "Immediately Needed", "Highly Needed", or "Ordinary"
    private ArrayList<String> postComments = new ArrayList<>();
    private boolean isPostValid = false;
    
    // Getter and Setter methods for postID
    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
    
    // Getter and Setter methods for postTitle
    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    // Getter and Setter methods for postBody
    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    // Getter and Setter methods for postTags
    public ArrayList<String> getPostTags() {
        return postTags;
    }

    public void setPostTags(ArrayList<String> postTags) {
        this.postTags = postTags;
    }

    // Getter and Setter methods for postType
    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    // Getter and Setter methods for postEmergency
    public String getPostEmergency() {
        return postEmergency;
    }

    public void setPostEmergency(String postEmergency) {
        this.postEmergency = postEmergency;
    }

    // Function that calls validation functions and writes post to text file if all inputs are valid
    public boolean addPost() {
        // Calls other boolean functions to check validity of inputs
        if (isValidTitle(postTitle) && isValidBody(postBody) && isValidTags(postTags) && isValidTypeAndEmergency()) {
            // Attempts to write new post to text file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("post.txt", true))) {
                writer.write("Post ID: " + postID + "\n");
                writer.write("Title: " + postTitle + "\n");
                writer.write("Body: " + postBody + "\n");
                writer.write("Tags: " + String.join(", ", postTags) + "\n");
                writer.write("Type: " + postType + "\n");
                writer.write("Emergency: " + postEmergency + "\n");
                writer.write("\n");
                isPostValid = true;
                // Returns true if post is successfully written to text file
                return true;
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Function that checks the validity of the input title field
    private boolean isValidTitle(String title) {
        // Checks if title length matches expected length
        if (title.length() < 10 || title.length() > 250) {
            System.out.println("Post title must contain between 10 - 250 total characters");
            return false;
        }
        // Checks first 5 characters for any numbers or special characters
        if (Pattern.matches("^[^a-zA-Z]{5}.*", title)) {
            System.out.println("Post title must not contain numbers or special characters within the first 5 characters");
            return false;
        }
        // Returns true if input is valid
        return true;
    }

    // Function that checks description validity based on input post type
    private boolean isValidBody(String body) {
        // Checks description length for 'Difficult' and 'Very Difficult' posts
        if ((postType.equals("Very Difficult") || postType.equals("Difficult")) && body.length() < 300) {
            System.out.println("Post description for 'Difficult' or 'Very Difficult' posts must have at least 300 characters");
            return false;
        }
        // Checks description length for 'Easy' posts
        if (postType.equals("Easy") && body.length() < 250) {
            System.out.println("Post description must have at least 250 characters");
            return false;
        }
        // Returns true if input is valid
        return true;
    }

    // Function that checks tags validity 
    private boolean isValidTags(ArrayList<String> tags) {
        // Checks if total number of tags matches expected number
        if (tags.size() < 2 || tags.size() > 5) {
            System.out.println("Post must have 2 - 5 total tags");
            return false;
        }
        // Checks if 'Easy' posts have the expected number of tags (3 or less)
        if (postType.equals("Easy") && tags.size() > 3) {
            System.out.println("Posts marked as 'Easy' should not have more than 3 tags");
            return false;
        }
        // Checks through each tag to see if they contain the correct number of characters and no upper-case letters
        for (String tag : tags) {
            if (tag.length() < 2 || tag.length() > 10 || !tag.equals(tag.toLowerCase())) {
                System.out.println("Post tags must be 2 - 10 total characters and contain no upper-case letters");
                return false;
            }
        }
        // Returns true if input is valid
        return true;
    }

    // Function that checks input type and emergency and the pairings between them
    private boolean isValidTypeAndEmergency() {
        // Checks if input difficulty matches an expected difficulty type
        if (!postType.equals("Very Difficult") && !postType.equals("Difficult") && !postType.equals("Easy")) {
            System.out.println("Post difficulty type must be input as 'Very Difficult', 'Difficult' or 'Easy'.");
            return false;
        }
        // Checks if input emergency matches an expected emergency type
        if (!postEmergency.equals("Immediately Needed") && !postEmergency.equals("Highly Needed") && !postEmergency.equals("Ordinary")) {
            System.out.println("Post emergency type must be input as 'Immediately Needed', 'Highly Needed' or 'Ordinary'.");
            return false;
        }
        // Checks if 'Easy' difficulty post is paired with an invalid emergency type
        if (postType.equals("Easy") && (postEmergency.equals("Immediately Needed") || postEmergency.equals("Highly Needed"))) {
            System.out.println("Posts with 'Easy' difficulty should have an 'Ordinary' emergency status");
            return false;
        }
        // Checks if 'Ordinary' emergency post is paired with an invalid difficulty type
        if ((postType.equals("Very Difficult") || postType.equals("Difficult")) && postEmergency.equals("Ordinary")) {
            System.out.println("Posts with 'Very Difficult' or 'Difficult' should not have an 'Ordinary' emergency status");
            return false;
        }
        // Returns true if input is valid
        return true;
    }

    // Function that calls other functions to check the validity of input comment and attempts to write new comment to file
    public boolean addComment(String comment) {
        // Calls validation functions for the user input
        if (isValidComment(comment) && canAddComment()) {
            // Attempts to write new comment to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("comment.txt", true))) {
                writer.write("Post ID: " + postID + "\n");
                writer.write("Comment: " + comment + "\n\n");
                postComments.add(comment);
                // Returns true if comment is successfully written to text file
                return true;
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // Function that checks the validity of input comment
    private boolean isValidComment(String comment) {
        // Splits the input comment into an array for each word
        String[] words = comment.split("\\s+");
        // Checks if the length of the comment matches expected length
        if (words.length < 4 || words.length > 10) {
            System.out.println("Comment must contain 4 - 10 total words");
            return false;
        }
        // Checks if the first character in the comment is uppercase
        if (!Character.isUpperCase(words[0].charAt(0))) {
            System.out.println("Comment first character must be an uppercase letter");
            return false;
        }
        // Returns true if input is valid
        return true;
    }

    // Function that checks if a comment can be added to the post
    private boolean canAddComment() {
        // Checks if 'Easy' difficulty or 'Ordinary' emergency posts already have max comments
        if ((postType.equals("Easy") || postEmergency.equals("Ordinary")) && postComments.size() >= 3) {
            System.out.println("Post already has the max number of comments posted");
            return false;
        }
        // Checks if other post types already have max comments
        if (postComments.size() >= 5) {
            System.out.println("Post already has the max number of comments posted");
            return false;
        }
        // Returns true if input is valid
        return true;
    }


    // Function that retrieves a post based on the input ID
    public boolean retrievePostByID(int id) {
        // Reads in the post file
        try (BufferedReader reader = new BufferedReader(new FileReader("post.txt"))) {
            String line;
            boolean postFound = false;
            // Loops through until it finds a post with the matching ID
            while ((line = reader.readLine()) != null) {
                // If a post with the matching ID is found the data is read in to the program
                if (line.contains("Post ID: " + id)) {
                    postFound = true;
                    postID = id;
                    postTitle = reader.readLine().split(": ")[1];
                    postBody = reader.readLine().split(": ")[1];
                    postTags.clear();
                    String[] tags = reader.readLine().split(": ")[1].split(", ");
                    for (String tag : tags) {
                        postTags.add(tag);
                    }
                    postType = reader.readLine().split(": ")[1];
                    postEmergency = reader.readLine().split(": ")[1];
                    reader.readLine(); // Read the empty line separating posts
                    break;
                }
            }
            // Informs user on if the input ID matches an existing post
            if (postFound) {
                isPostValid = true;
                System.out.println("Post retrieved successfully.");
                loadComments();
                return true;
            } else {
                System.out.println("Post ID not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Function that loads the comments from the current post
    private void loadComments() {
    	// Clears any listed comments
        postComments.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("comment.txt"))) {
            String line;
            // Reads through the entire comment file to find comments with matching ID
            while ((line = reader.readLine()) != null) {
            	// Looks for any comment with matching listed ID
                if (line.contains("Post ID: " + postID)) {
                	// Adds the comment 
                    String comment = reader.readLine().split(": ")[1];
                    postComments.add(comment);
                    reader.readLine();
                }
            }
            // Prints out all the posts details
            System.out.println("Post Title: " + postTitle);
        	System.out.println("Post Description: " + postBody);
        	System.out.println("Post Tags: " + postTags);
        	System.out.println("Post Type: " + postType);
        	System.out.println("Post Status: " + postEmergency);
            System.out.println("Current number of comments: " + postComments.size());
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // Creates scanner and new post
        Scanner scanner = new Scanner(System.in);
        Post myPost = new Post();

        while (true) {
            // Sets up a main menu that continues to run program until user exits
            System.out.println("Main Menu:");
            System.out.println("1. Add a Post");
            System.out.println("2. Add a Comment");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            // Gets user input
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                // Runs cases for each valid user input
                // Also catches invalid inputs
                switch (choice) {
                    // Handles case for creating a new post
                    case 1:
                        try {
                            // Takes user input for post ID
                            System.out.print("Enter post ID: ");
                            myPost.postID = scanner.nextInt();
                            scanner.nextLine();
                        } 
                        // Catches error if user inputs a non-numerical input
                        catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a numerical value for the post ID.");
                            scanner.nextLine();
                            continue;
                        }
                        // Takes user inputs for each post field
                        System.out.print("Enter post title: ");
                        myPost.postTitle = scanner.nextLine();

                        System.out.print("Enter post description: ");
                        myPost.postBody = scanner.nextLine();

                        System.out.print("Enter post tags (comma separated): ");
                        // Splits tag input on ','
                        String[] tags = scanner.nextLine().split(",");
                        myPost.postTags.clear();
                        for (String tag : tags) {
                            myPost.postTags.add(tag.trim());
                        }

                        System.out.print("Enter post type (Very Difficult, Difficult, Easy): ");
                        myPost.postType = scanner.nextLine();

                        System.out.print("Enter post emergency (Immediately Needed, Highly Needed, Ordinary): ");
                        myPost.postEmergency = scanner.nextLine();
                        
                        // Calls function that attempts to add post to file
                        if (myPost.addPost()) {
                            System.out.println("Post added successfully.");
                        } 
                        else {
                            System.out.println("Failed to add post.");
                        }
                        break;

                    // Handles case for adding a comment to an existing post
                    case 2:
                        // Takes user input for post ID
                        System.out.print("Enter post ID: ");
                        int postID = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                        
                        // Retrieves the post by ID
                        if (!myPost.retrievePostByID(postID)) {
                            System.out.println("Post not found. Please create a post before adding a comment.");
                            break;
                        }

                        // Takes user input for comment
                        System.out.print("Enter comment: ");
                        String comment = scanner.nextLine();
                        // Calls function that attempts to post comment to file
                        if (myPost.addComment(comment)) {
                            System.out.println("Comment added successfully.");
                        } else {
                            System.out.println("Failed to add comment.");
                        }
                        break;

                    // Handles case for exiting program
                    case 3:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } 
            // Catches error if user inputs a non-numerical input
            catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter a numerical value.");
                scanner.nextLine();
            }
        }
    }
}

