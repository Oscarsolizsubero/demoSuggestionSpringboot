package com.example.demo.controller;

import com.example.demo.config.JwtTokenUtil;
import com.example.demo.model.*;
import com.example.demo.model.DTO.suggestion.SuggestionDTO;
import com.example.demo.service.SuggestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;


class SuggestionControllerTest {

    private static final String USER_NAME = "user1";

    private static final String ADMIN_NAME = "admin";

    private static final String EMPTY_NAME = "";

    private static long ADMIN_ID = 1;

    private static long USER_ID = 2;

    private static final String USER_PASSWORD = "$2y$12$svw6XfWfeK38mB9CVBUYsOWmY.jWP1sXBVIKnIlhVeOKPYGZwSCyq";

    private static final String STATUS_NEW = "New";

    private static final String STATUS_SUGGESTED = "Suggested";

    private static final String STATUS_FULFILLED = "Fulfilled";

    private static final Date DATE = new Date();

    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTU3OTIwMzE2NiwiZXhwIjoxNTc5MjIxMTY2fQ.b7cVJygBufh5fXZATxUI5xjDMIVGMpYhBMigRetcfuvF3QpbVzAYAHOlIlBSyJ11BwzfjjHzvoRD6ZcXwC3JoQ";

    @Mock
    private SuggestionService suggestionService;
    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private SuggestionController suggestionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllSuggestionsWhenTokenNull() {
        when(jwtTokenUtil.getUsernameFromTokenWithBearer(EMPTY_NAME)).thenReturn(EMPTY_NAME);

        List<Status> publicStatuses = new ArrayList<>();
        publicStatuses.add(Status.builder()
                .id(2)
                .name(STATUS_SUGGESTED)
                .build());
        publicStatuses.add(Status.builder()
                .id(4)
                .name(STATUS_FULFILLED)
                .build());

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .id(1)
                .name("ADMIN")
                .description("Admin role")
                .build());
        roles.add(Role.builder()
                .id(3)
                .name("USER")
                .description("User role")
                .build());

        User user = User.builder()
                .id(ADMIN_ID)
                .username(ADMIN_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();

        List<Suggestion> suggestionList = new ArrayList();
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion")
                .user(user)
                .status(publicStatuses.get(0))
                .build());
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion 2")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion 2")
                .user(user)
                .status(publicStatuses.get(1))
                .build());
        when(suggestionService.findAll(EMPTY_NAME)).thenReturn(suggestionList);

        List<Vote> voteduser = new ArrayList<>();
        voteduser.add(Vote.builder()
                .suggestion(suggestionList.get(0))
                .user(user)
                .build());
        voteduser.add(Vote.builder()
                .suggestion(suggestionList.get(1))
                .user(user)
                .build());
        when(suggestionService.findVotes(EMPTY_NAME)).thenReturn(voteduser);


        final ResponseEntity<List<SuggestionDTO>> expectedSuggestions = suggestionController.getAllSuggestions(null);

        assertEquals(expectedSuggestions.getBody().get(0).getId(), suggestionList.get(0).getId());
        assertEquals(expectedSuggestions.getBody().get(0).getUser().getName(), suggestionList.get(0).getUser().getUsername());
        assertEquals(expectedSuggestions.getBody().get(1).getId(), suggestionList.get(1).getId());
        assertEquals(expectedSuggestions.getBody().get(1).getUser().getName(), suggestionList.get(1).getUser().getUsername());
    }

    @Test
    void getAllSuggestionsWhenTokenIsUser() {
        when(jwtTokenUtil.getUsernameFromTokenWithBearer(TOKEN)).thenReturn(USER_NAME);

        List<Status> publicStatuses = new ArrayList<>();
        publicStatuses.add(Status.builder()
                .id(2)
                .name(STATUS_SUGGESTED)
                .build());
        publicStatuses.add(Status.builder()
                .id(4)
                .name(STATUS_FULFILLED)
                .build());

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .id(1)
                .name("ADMIN")
                .description("Admin role")
                .build());
        roles.add(Role.builder()
                .id(3)
                .name("USER")
                .description("User role")
                .build());

        User user = User.builder()
                .id(ADMIN_ID)
                .username(ADMIN_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();

        List<Suggestion> suggestionList = new ArrayList();
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion")
                .user(user)
                .status(publicStatuses.get(0))
                .build());
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion 2")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion 2")
                .user(user)
                .status(publicStatuses.get(1))
                .build());
        when(suggestionService.findAll(USER_NAME)).thenReturn(suggestionList);

        List<Vote> voteduser = new ArrayList<>();
        voteduser.add(Vote.builder()
                .suggestion(suggestionList.get(0))
                .user(user)
                .build());
        voteduser.add(Vote.builder()
                .suggestion(suggestionList.get(1))
                .user(user)
                .build());
        when(suggestionService.findVotes(USER_NAME)).thenReturn(voteduser);


        final ResponseEntity<List<SuggestionDTO>> expectedSuggestions = suggestionController.getAllSuggestions(TOKEN);

        assertEquals(expectedSuggestions.getBody().get(0).getId(), suggestionList.get(0).getId());
        assertEquals(expectedSuggestions.getBody().get(0).getUser().getName(), suggestionList.get(0).getUser().getUsername());
        assertEquals(expectedSuggestions.getBody().get(1).getId(), suggestionList.get(1).getId());
        assertEquals(expectedSuggestions.getBody().get(1).getUser().getName(), suggestionList.get(1).getUser().getUsername());
    }

    @Test
    void getAllSuggestionsWhenTokenIsAdmin() {
        when(jwtTokenUtil.getUsernameFromTokenWithBearer(TOKEN)).thenReturn(ADMIN_NAME);

        List<Status> publicStatuses = new ArrayList<>();
        publicStatuses.add(Status.builder()
                .id(2)
                .name(STATUS_SUGGESTED)
                .build());
        publicStatuses.add(Status.builder()
                .id(4)
                .name(STATUS_FULFILLED)
                .build());

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .id(1)
                .name("ADMIN")
                .description("Admin role")
                .build());
        roles.add(Role.builder()
                .id(3)
                .name("USER")
                .description("User role")
                .build());

        User user = User.builder()
                .id(ADMIN_ID)
                .username(ADMIN_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();

        List<Suggestion> suggestionList = new ArrayList();
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion")
                .user(user)
                .status(publicStatuses.get(0))
                .build());
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion 2")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion 2")
                .user(user)
                .status(publicStatuses.get(1))
                .build());
        when(suggestionService.findAll(ADMIN_NAME)).thenReturn(suggestionList);

        List<Vote> voteduser = new ArrayList<>();
        voteduser.add(Vote.builder()
                .suggestion(suggestionList.get(0))
                .user(user)
                .build());
        voteduser.add(Vote.builder()
                .suggestion(suggestionList.get(1))
                .user(user)
                .build());
        when(suggestionService.findVotes(ADMIN_NAME)).thenReturn(voteduser);


        final ResponseEntity<List<SuggestionDTO>> expectedSuggestions = suggestionController.getAllSuggestions(TOKEN);

        assertEquals(expectedSuggestions.getBody().get(0).getId(), suggestionList.get(0).getId());
        assertEquals(expectedSuggestions.getBody().get(0).getUser().getName(), suggestionList.get(0).getUser().getUsername());
        assertEquals(expectedSuggestions.getBody().get(1).getId(), suggestionList.get(1).getId());
        assertEquals(expectedSuggestions.getBody().get(1).getUser().getName(), suggestionList.get(1).getUser().getUsername());
    }
    @Test
    void createSuggestion() {
    }
}