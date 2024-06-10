package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PostTest {

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
    }

    @Test
    void testAddPostValid() {
        // Set 1
        post.setPostTitle("Valid Title Here");
        post.setPostBody("This is a valid post body with sufficient length for a difficult post type. ".repeat(10));
        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("tag1");
        tags1.add("tag2");
        post.setPostTags(tags1);
        post.setPostType("Difficult");
        post.setPostEmergency("Highly Needed");
        assertTrue(post.addPost(), "Post should be valid and added successfully");

        // Set 2
        post = new Post(); // Reset post object
        post.setPostTitle("Another Valid Title Here");
        post.setPostBody("This is another valid post body with enough content for a very difficult post. ".repeat(10));
        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add("tag1");
        tags2.add("tag2");
        tags2.add("tag3");
        post.setPostTags(tags2);
        post.setPostType("Very Difficult");
        post.setPostEmergency("Immediately Needed");
        assertTrue(post.addPost(), "Post should be valid and added successfully");
    }

    @Test
    void testAddPostTitleTooShort() {
        // Set 1
        post.setPostTitle("Short");
        post.setPostBody("This is a valid post body with sufficient length for a difficult post type. ".repeat(10));
        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("tag1");
        tags1.add("tag2");
        post.setPostTags(tags1);
        post.setPostType("Difficult");
        post.setPostEmergency("Highly Needed");
        assertFalse(post.addPost(), "Post should be invalid due to short title");

        // Set 2
        post = new Post();
        post.setPostTitle("Tiny");
        post.setPostBody("This is another valid post body with enough content for a very difficult post. ".repeat(10));
        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add("tag1");
        tags2.add("tag2");
        post.setPostTags(tags2);
        post.setPostType("Very Difficult");
        post.setPostEmergency("Immediately Needed");
        assertFalse(post.addPost(), "Post should be invalid due to short title");
    }

    @Test
    void testAddPostTitleTooLong() {
        // Set 1
        post.setPostTitle("A".repeat(251));
        post.setPostBody("This is a valid post body with sufficient length for a difficult post type. ".repeat(10));
        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("tag1");
        tags1.add("tag2");
        post.setPostTags(tags1);
        post.setPostType("Difficult");
        post.setPostEmergency("Highly Needed");
        assertFalse(post.addPost(), "Post should be invalid due to long title");

        // Set 2
        post = new Post();
        post.setPostTitle("B".repeat(251));
        post.setPostBody("This is another valid post body with enough content for a very difficult post. ".repeat(10));
        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add("tag1");
        tags2.add("tag2");
        post.setPostTags(tags2);
        post.setPostType("Very Difficult");
        post.setPostEmergency("Immediately Needed");
        assertFalse(post.addPost(), "Post should be invalid due to long title");
    }

    @Test
    void testAddPostInvalidTagsTooFew() {
        // Set 1
        post.setPostTitle("Valid Title Here");
        post.setPostBody("This is a valid post body with sufficient length for a difficult post type. ".repeat(10));
        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("tag1");
        post.setPostTags(tags1);
        post.setPostType("Difficult");
        post.setPostEmergency("Highly Needed");
        assertFalse(post.addPost(), "Post should be invalid due to too few tags");

        // Set 2
        post = new Post();
        post.setPostTitle("Another Valid Title Here");
        post.setPostBody("This is another valid post body with enough content for a very difficult post. ".repeat(10));
        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add("tag1");
        post.setPostTags(tags2);
        post.setPostType("Very Difficult");
        post.setPostEmergency("Immediately Needed");
        assertFalse(post.addPost(), "Post should be invalid due to too few tags");
    }

    @Test
    void testAddPostInvalidTagsTooMany() {
        // Set 1
        post.setPostTitle("Valid Title Here");
        post.setPostBody("This is a valid post body with sufficient length for a difficult post type. ".repeat(10));
        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("tag1");
        tags1.add("tag2");
        tags1.add("tag3");
        tags1.add("tag4");
        tags1.add("tag5");
        tags1.add("tag6");
        post.setPostTags(tags1);
        post.setPostType("Difficult");
        post.setPostEmergency("Highly Needed");
        assertFalse(post.addPost(), "Post should be invalid due to too many tags");

        // Set 2
        post = new Post();
        post.setPostTitle("Another Valid Title Here");
        post.setPostBody("This is another valid post body with enough content for a very difficult post. ".repeat(10));
        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add("tag1");
        tags2.add("tag2");
        tags2.add("tag3");
        tags2.add("tag4");
        tags2.add("tag5");
        tags2.add("tag6");
        post.setPostTags(tags2);
        post.setPostType("Very Difficult");
        post.setPostEmergency("Immediately Needed");
        assertFalse(post.addPost(), "Post should be invalid due to too many tags");
    }

    @Test
    void testAddPostTypeEmergencyMismatch() {
        // Set 1
        post.setPostTitle("Valid Title Here");
        post.setPostBody("This is a valid post body with sufficient length for a difficult post type. ".repeat(10));
        ArrayList<String> tags1 = new ArrayList<>();
        tags1.add("tag1");
        tags1.add("tag2");
        post.setPostTags(tags1);
        post.setPostType("Easy");
        post.setPostEmergency("Highly Needed");
        assertFalse(post.addPost(), "Post should be invalid due to type and emergency mismatch");

        // Set 2
        post = new Post();
        post.setPostTitle("Another Valid Title Here");
        post.setPostBody("This is another valid post body with enough content for a very difficult post. ".repeat(10));
        ArrayList<String> tags2 = new ArrayList<>();
        tags2.add("tag1");
        tags2.add("tag2");
        post.setPostTags(tags2);
        post.setPostType("Easy");
        post.setPostEmergency("Immediately Needed");
        assertFalse(post.addPost(), "Post should be invalid due to type and emergency mismatch");
    }
}




