package com.example.studentbenchmark.controllers;


import com.example.studentbenchmark.controllers.TestController;
import com.example.studentbenchmark.entity.AppUser;
import com.example.studentbenchmark.entity.AppUserEntityDetails;
import com.example.studentbenchmark.entity.testsEntities.*;
import com.example.studentbenchmark.repository.testsRepositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.studentbenchmark.entity.AppUser.Role.USER;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @MockBean
    SequenceTestRepo sequenceTestRepo;
    @MockBean
    VerbalTestRepo verbalTestRepo;
    @MockBean
    VisualTestRepo visualTestRepo;
    @MockBean
    NumberTestRepo numberTestRepo;
    @Autowired
    MockMvc mockMvc;

    Authentication authentication;

    public void shouldSendAllScores(List<AppTest> appTests, TestRepo testRepo, String path, Integer size) throws Exception {
        appTests = appTests.stream().sorted(Comparator.comparingInt(AppTest::getScore)).toList();
        when(testRepo.getAllScores()).thenReturn(appTests);
        Map<Integer, Long> scoresMap = appTests.stream().collect(Collectors.groupingBy(p -> p.getScore(), Collectors.counting()));
        String json = mockMvc.perform(get("/tests/".concat(path)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(size)))
                .andReturn().getResponse().getContentAsString();

        List<Integer> response= new ObjectMapper().readValue(json, List.class);
        for(int i=0; i< response.size(); i++)
        {
            if(scoresMap.containsKey(i))
            {
                Assertions.assertTrue((long)scoresMap.get(i) == response.get(i));

            }
            else
                Assertions.assertTrue(response.get(i)==0L);
        }
    }

    @Test
    public void shouldSendAllSequenceTestScores() throws Exception {

        List<AppTest> sequenceTests = new ArrayList<>();
        sequenceTests.add(new SequenceTest(10, (long) 1, (long) 0, new Date()));
        sequenceTests.add(new SequenceTest(15, (long) 2, (long) 1, new Date()));
        sequenceTests.add(new SequenceTest(13, (long) 3, (long) 1, new Date()));


        shouldSendAllScores(sequenceTests, sequenceTestRepo, "sequence", 101);


    }

    @Test
    public void shouldSendAllVerbalTestScores() throws Exception {

        List<AppTest> verbalTests = new ArrayList<>();
        verbalTests.add(new VerbalTest(10, (long) 1, (long) 0, new Date()));
        verbalTests.add(new VerbalTest(15, (long) 2, (long) 1, new Date()));
        verbalTests.add(new VerbalTest(13, (long) 3, (long) 1, new Date()));

        shouldSendAllScores(verbalTests, verbalTestRepo, "verbal", 1001);


    }

    @Test
    public void shouldSendAllVisualTestScores() throws Exception {

        List<AppTest> visualTests = new ArrayList<>();
        visualTests.add(new VisualTest(5, (long) 1, (long) 0, new Date()));
        visualTests.add(new VisualTest(10, (long) 2, (long) 1, new Date()));
        visualTests.add(new VisualTest(12, (long) 3, (long) 1, new Date()));

        shouldSendAllScores(visualTests, visualTestRepo, "visual", 101);


    }

    @Test
    public void shouldSendAllNumberTestScores() throws Exception {

        List<AppTest> numberTests = new ArrayList<>();
        numberTests.add(new NumberTest(10, (long) 1, (long) 0, new Date()));
        numberTests.add(new NumberTest(15, (long) 2, (long) 1, new Date()));
        numberTests.add(new NumberTest(13, (long) 3, (long) 1, new Date()));

        shouldSendAllScores(numberTests, numberTestRepo, "number", 101);


    }

    public void checkAddResultsTestsMethods(AppTest test, TestRepo testRepo, ResultMatcher result, String path, String response) throws Exception {

        when(testRepo.save(Mockito.any(AppTest.class))).thenReturn(null);
        Gson gson = new Gson();
        String json = gson.toJson(test);

        mockMvc
                .perform(post("/result/".concat(path))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(result)
                .andExpect(content().string(containsString(response)));

    }

    @Test
    public void shouldAddResultFromSequenceTest() throws Exception {

        SequenceTest sequenceTest = new SequenceTest(10);
        checkAddResultsTestsMethods(sequenceTest, sequenceTestRepo, status().isOk(),
                "sequence", "Result added successfully");


    }

    @Test
    public void shouldAddResultFromVisualTest() throws Exception {

        VisualTest visualTest = new VisualTest(10);
        checkAddResultsTestsMethods(visualTest, visualTestRepo, status().isOk(),
                "visual", "Result added successfully");


    }

    @Test
    public void shouldAddResultFromVerbalTest() throws Exception {

        VerbalTest verbalTest = new VerbalTest(400);
        checkAddResultsTestsMethods(verbalTest, verbalTestRepo, status().isOk(),
                "verbal", "Result added successfully");


    }

    @Test
    public void shouldAddResultFromNumberTest() throws Exception {

        NumberTest numberTest = new NumberTest(10);
        checkAddResultsTestsMethods(numberTest, numberTestRepo, status().isOk(),
                "number", "Result added successfully");


    }

    @Test
    public void shouldNotAddResultFromSequenceTest() throws Exception {

        SequenceTest sequenceTest = new SequenceTest(-1);
        checkAddResultsTestsMethods(sequenceTest, sequenceTestRepo, status().isBadRequest(),
                "sequence", "Incorrect data");

        sequenceTest = new SequenceTest(200);
        checkAddResultsTestsMethods(sequenceTest, sequenceTestRepo, status().isBadRequest(),
                "sequence", "Incorrect data");

    }

    @Test
    public void shouldNotAddResultFromVisualTest() throws Exception {

        VerbalTest verbalTest = new VerbalTest(-1);
        checkAddResultsTestsMethods(verbalTest, verbalTestRepo, status().isBadRequest(),
                "visual", "Incorrect data");

        verbalTest = new VerbalTest(200);
        checkAddResultsTestsMethods(verbalTest, verbalTestRepo, status().isBadRequest(),
                "visual", "Incorrect data");

    }

    @Test
    public void shouldNotAddResultFromVerbalTest() throws Exception {

        SequenceTest sequenceTest = new SequenceTest(-1);
        checkAddResultsTestsMethods(sequenceTest, sequenceTestRepo, status().isBadRequest(),
                "verbal", "Incorrect data");

        sequenceTest = new SequenceTest(1200);
        checkAddResultsTestsMethods(sequenceTest, sequenceTestRepo, status().isBadRequest(),
                "verbal", "Incorrect data");

    }

    @Test
    public void shouldNotAddResultFromNumberTest() throws Exception {

        NumberTest numberTest = new NumberTest(-1);
        checkAddResultsTestsMethods(numberTest, numberTestRepo, status().isBadRequest(),
                "number", "Incorrect data");

        numberTest = new NumberTest(200);
        checkAddResultsTestsMethods(numberTest, numberTestRepo, status().isBadRequest(),
                "number", "Incorrect data");

    }

    @Test
    public void shouldNotAddResultFromTest() throws Exception {

        SequenceTest sequenceTest = new SequenceTest(-1);
        checkAddResultsTestsMethods(sequenceTest, sequenceTestRepo, status().isBadRequest(),
                "sequence", "Incorrect data");

        sequenceTest = new SequenceTest(200);
        checkAddResultsTestsMethods(sequenceTest, sequenceTestRepo, status().isBadRequest(),
                "sequence", "Incorrect data");

    }


    @Test
    public void shouldSetIdUserAndDate() {
        authentication = Mockito.mock(Authentication.class);
        AppUser user = new AppUser("nick2", "user@kot.pl", "aA@asapiski2", USER);
        user.setIdUser(10L);
        AppUserEntityDetails appUserEntityDetails = new AppUserEntityDetails(user);
        when(authentication.getPrincipal()).thenReturn(appUserEntityDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        VerbalTest test = new VerbalTest(10);
        TestController controller = new TestController();
        AppTest appTest = controller.modifyTest(test);
        Assertions.assertTrue(appTest.getIdUser() == user.getIdUser());
        Assertions.assertTrue(appTest.getDateOfSubmission() != null);
        Assertions.assertTrue(test.getScore() == appTest.getScore());


    }

    @Test
    @WithAnonymousUser
    public void shouldNotSetIdUserAndSetDate() {
        VerbalTest test = new VerbalTest(10);
        TestController controller = new TestController();
        AppTest appTest = controller.modifyTest(test);
        Assertions.assertTrue(appTest.getIdUser() == 0);
        Assertions.assertTrue(appTest.getDateOfSubmission() != null);
        Assertions.assertTrue(test.getScore() == appTest.getScore());
    }
}


