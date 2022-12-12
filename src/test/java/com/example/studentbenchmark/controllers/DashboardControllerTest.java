package com.example.studentbenchmark.controllers;

import static java.awt.geom.Path2D.contains;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.studentbenchmark.entity.testsEntities.NumberTest;
import com.example.studentbenchmark.entity.testsEntities.SequenceTest;
import com.example.studentbenchmark.entity.testsEntities.VerbalTest;
import com.example.studentbenchmark.entity.testsEntities.VisualTest;
import com.example.studentbenchmark.repository.testsRepositories.NumberTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.SequenceTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VerbalTestRepo;
import com.example.studentbenchmark.repository.testsRepositories.VisualTestRepo;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DashboardControllerTest {

}
