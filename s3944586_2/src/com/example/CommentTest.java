package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommentTest {

    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setPostID(1);
    }

    @Test
    // Tests adding a valid input
    void testAddCommentValid() {
        // Set 1
        post.retrievePostByID(4);
        String validComment1 = "This is a valid comment";
        assertTrue(post.addComment(validComment1), "Comment should be valid and added successfully");

        // Set 2
        post.retrievePostByID(4);
        String validComment2 = "Another valid comment";
        assertTrue(post.addComment(validComment2), "Comment should be valid and added successfully");
    }

    @Test
    // Does testing on an existing post with comment length being too short
    void testAddCommentTooShort() {
        // Set 1
        post.retrievePostByID(1);
        String shortComment1 = "Too short.";
        assertFalse(post.addComment(shortComment1), "Comment should be invalid due to being too short");

        // Set 2
        post.retrievePostByID(1);
        String shortComment2 = "No.";
        assertFalse(post.addComment(shortComment2), "Comment should be invalid due to being too short");
    }

    @Test
    // Does testing on an existing post with comment length being too long
    void testAddCommentTooLong() {
        // Set 1
        post.retrievePostByID(1);
        String longComment1 = "This comment is way too long for the test and should not be accepted.";
        assertFalse(post.addComment(longComment1), "Comment should be invalid due to being too long");

        // Set 2
        post.retrievePostByID(1);
        String longComment2 = "Another comment that is too long and will not be accepted by the system.";
        assertFalse(post.addComment(longComment2), "Comment should be invalid due to being too long");
    }

    @Test
    // Does testing on an existing post missing the first uppercase character
    void testAddCommentLowercaseFirstCharacter() {
        // Set 1
        post.retrievePostByID(1);
        String lowerCaseFirstCharComment1 = "invalid start.";
        assertFalse(post.addComment(lowerCaseFirstCharComment1), "Comment should be invalid due to lowercase first character");

        // Set 2
        post.retrievePostByID(1);
        String lowerCaseFirstCharComment2 = "another invalid comment.";
        assertFalse(post.addComment(lowerCaseFirstCharComment2), "Comment should be invalid due to lowercase first character");
    }

    @Test
    // Does testing on post with ID 1 which is of 'Easy' difficulty and has max (3) comments
    void testAddCommentTooManyCommentsEasyPost() {
        // Set 1
        post.retrievePostByID(1);
        String comment = "Valid comment.";
        assertFalse(post.addComment(comment), "Comment should be invalid due to too many comments on Easy post");

        // Set 2
        post.retrievePostByID(1);
        String anotherComment = "Another valid comment.";
        assertFalse(post.addComment(anotherComment), "Comment should be invalid due to too many comments on Easy post");
    }

    @Test
    // Does testing on post with ID 2 which already has max (5) comments
    void testAddCommentTooManyComments() {
        // Set 1
        post.retrievePostByID(2);
        String comment = "Valid comment.";
        assertFalse(post.addComment(comment), "Comment should be invalid due to too many comments on other post types");

        // Set 2
        post.retrievePostByID(2);
        String anotherComment = "Another valid comment.";
        assertFalse(post.addComment(anotherComment), "Comment should be invalid due to too many comments on other post types");
    }
}

