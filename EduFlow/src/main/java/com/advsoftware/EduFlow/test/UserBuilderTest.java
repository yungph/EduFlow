package com.advsoftware.EduFlow.test;

import com.advsoftware.EduFlow.models.Role;
import com.advsoftware.EduFlow.models.User;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*; // Correct assertions import

public class UserBuilderTest { // Better test class name

    @Test // Capital 'T' annotation
    public void testUserBuilder() {
        // Arrange
        Role studentRole = new Role("ROLE_STUDENT"); // Added ROLE_ prefix if that's your convention

        // Act
        User user = User.builder()
                .name("Alice")
                .email("alice@school.com")
                .password("secure123")
                .role(studentRole)
                .build();

        // Assert
        assertNotNull(user.getUser_id(), "User ID should not be null");
        assertEquals("Alice", user.getName(), "Name should match");
        assertEquals(1, user.getRoles().size(), "Should have exactly 1 role");
        assertTrue(user.getRoles().contains(studentRole), "Should contain the student role");
    }
}