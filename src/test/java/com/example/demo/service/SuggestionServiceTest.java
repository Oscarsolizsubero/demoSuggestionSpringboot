package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SuggestionServiceTest {

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

    @Mock
    private UserRepository userRepository;
    @Mock
    private SuggestionRepository suggestionRepository;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private SuggestionService suggestionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAllWhenUserNull() {
        List<Status> publicStatuses = new ArrayList<>();
        publicStatuses.add(Status.builder()
                .id(2)
                .name(STATUS_SUGGESTED)
                .build());
        publicStatuses.add(Status.builder()
                .id(4)
                .name(STATUS_FULFILLED)
                .build());
        when(statusRepository.findByNameIn(Arrays.asList(STATUS_SUGGESTED, STATUS_FULFILLED))).thenReturn(publicStatuses);

        when(userRepository.findByUsername(EMPTY_NAME)).thenReturn(null);

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .name("USER")
                .id(3)
                .description("User role")
                .build());
        User userToBuild = User.builder()
                .id(USER_ID)
                .username(USER_NAME)
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
                .user(userToBuild)
                .status(publicStatuses.get(0))
                .build());
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion 2")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion 2")
                .user(userToBuild)
                .status(publicStatuses.get(1))
                .build());
        when(suggestionRepository.findByStatusIdByOrderByQuantityVote(publicStatuses)).thenReturn(suggestionList);

        List<Suggestion> expectedSuggestionList = suggestionService.findAll(EMPTY_NAME);

        assertEquals(expectedSuggestionList, suggestionList);


    }

    @Test
    void findAllWhenUserIsAdmin() {
        List<Status> publicStatuses = new ArrayList<>();
        publicStatuses.add(Status.builder()
                .id(2)
                .name(STATUS_SUGGESTED)
                .build());
        publicStatuses.add(Status.builder()
                .id(4)
                .name(STATUS_FULFILLED)
                .build());
        when(statusRepository.findByNameIn(Arrays.asList(STATUS_SUGGESTED, STATUS_FULFILLED))).thenReturn(publicStatuses);

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

        User expectedUser = User.builder()
                .id(ADMIN_ID)
                .username(ADMIN_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();
        when(userRepository.findByUsername(ADMIN_NAME)).thenReturn(expectedUser);

        User userAdminToBuild = User.builder()
                .id(ADMIN_ID)
                .username(ADMIN_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();
        User userToBuild = User
                .builder()
                .id(USER_ID)
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();
        List<Suggestion> suggestionList = new ArrayList();
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion")
                .createdDate(new Date()).quantityVote(1)
                .description("this is new suggestion")
                .user(userAdminToBuild)
                .status(publicStatuses.get(0))
                .build());
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion 2")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion 2")
                .user(userToBuild)
                .status(publicStatuses.get(1))
                .build());
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion 3")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion 2")
                .user(userToBuild)
                .status(Status.builder().id(1).name(STATUS_NEW).build())
                .build());
        when(suggestionRepository.findAllByOrderByQuantityVoteDesc()).thenReturn(suggestionList);

        List<Suggestion> expectedSuggestionList = suggestionService.findAll(ADMIN_NAME);

        assertEquals(expectedSuggestionList, suggestionList);
    }

    @Test
    void findAllWhenUserIsNotAdmin() {
        List<Status> publicStatuses = new ArrayList<>();
        publicStatuses.add(Status.builder()
                .id(2)
                .name(STATUS_SUGGESTED)
                .build());
        publicStatuses.add(Status.builder()
                .id(4)
                .name(STATUS_FULFILLED)
                .build());
        when(statusRepository.findByNameIn(Arrays.asList(STATUS_SUGGESTED, STATUS_FULFILLED))).thenReturn(publicStatuses);

        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .name("USER")
                .id(3).description("User role")
                .build());
        User expectedUser = User.builder()
                .id(USER_ID)
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();
        when(userRepository.findByUsername(USER_NAME)).thenReturn(expectedUser);

        List<Suggestion> suggestionList = new ArrayList();
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion")
                .user(expectedUser)
                .status(publicStatuses.get(0))
                .build());
        suggestionList.add(Suggestion.builder()
                .title("new Suggestion 2")
                .createdDate(new Date())
                .quantityVote(1)
                .description("this is new suggestion 2")
                .user(expectedUser)
                .status(publicStatuses.get(1))
                .build());
        when(suggestionRepository.findByStatusIdByOrderByQuantityVote(publicStatuses)).thenReturn(suggestionList);

        List<Suggestion> expectedSuggestionList = suggestionService.findAll(USER_NAME);
        assertEquals(expectedSuggestionList, suggestionList);
    }


    @Test
    void saveifAdmin() {
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

        User expectedUser = User.builder()
                .id(ADMIN_ID)
                .username(ADMIN_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();
        when(userRepository.findByUsername(ADMIN_NAME)).thenReturn(expectedUser);

        Status expectedStatus = Status.builder()
                .id(2)
                .name(STATUS_SUGGESTED)
                .build();
        when(statusRepository.findByName(STATUS_SUGGESTED)).thenReturn(expectedStatus);

        Suggestion suggestion = Suggestion.builder()
                .title("new Suggestion")
                .createdDate(new Date())
                .description("this is new suggestion")
                .user(expectedUser)
                .status(expectedStatus)
                .build();
        Suggestion suggestionPlusVote = suggestion;
        suggestionPlusVote.setQuantityVote(suggestion.getQuantityVote()+1);
        suggestionPlusVote.setId(100);

        when(suggestionRepository.save(any(Suggestion.class))).thenReturn(suggestionPlusVote);

        Vote vote = Vote.builder().user(expectedUser).suggestion(suggestionPlusVote).build();
        when(voteRepository.save(vote)).thenReturn(vote);

        Suggestion suggestionAsParameter = Suggestion.builder()
                .title("new Suggestion")
                .description("this is new suggestion")
                .user(User.builder().username(ADMIN_NAME).build())
                .build();
        Suggestion expectedSuggestion = suggestionService.save(suggestionAsParameter);
        assertEquals(expectedSuggestion, suggestion);
    }

    @Test
    void saveifUser() {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.builder()
                .id(3)
                .name("USER")
                .description("User role")
                .build());

        User expectedUser = User.builder()
                .id(USER_ID)
                .username(USER_NAME)
                .password(USER_PASSWORD)
                .roles(roles)
                .enabled(true)
                .tokenExpired(false)
                .build();
        when(userRepository.findByUsername(USER_NAME)).thenReturn(expectedUser);

        Status expectedStatus = Status.builder()
                .id(2)
                .name(STATUS_NEW)
                .build();
        when(statusRepository.findByName(STATUS_NEW)).thenReturn(expectedStatus);

        Suggestion suggestion = Suggestion.builder()
                .title("new Suggestion")
                .createdDate(DATE)
                .description("this is new suggestion")
                .user(expectedUser)
                .status(expectedStatus)
                .build();
        Suggestion suggestionPlusVote = suggestion;
        suggestionPlusVote.setQuantityVote(suggestion.getQuantityVote()+1);

        when(suggestionRepository.save(any(Suggestion.class))).thenReturn(suggestionPlusVote);

        Vote vote = Vote.builder().user(expectedUser).suggestion(suggestionPlusVote).build();
        when(voteRepository.save(vote)).thenReturn(vote);

        Suggestion suggestionAsParameter = Suggestion.builder()
                .title("new Suggestion")
                .description("this is new suggestion")
                .user(User.builder().username(USER_NAME).build())
                .build();
        Suggestion expectedSuggestion = suggestionService.save(suggestionAsParameter);
        assertEquals(expectedSuggestion, suggestion);
    }

    @Test
    void findVotes() {
    }

    @Test
    void vote() {
    }

    @Test
    void editSuggestionStatus() {
    }
}