package com.example.learningtest.student;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfStudentEmailExists() {
        // given
        String email = "sujin@gmail.com";
        Student student = new Student("Sujin", email, Gender.MALE);
        underTest.save(student);
        // when
        boolean exists = underTest.selectExistsEmail(email);
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void itShouldCheckIfStudentEmailDoesNotExists() {
        // given
        String email = "sujin@gmail.com";

        // when
        boolean exists = underTest.selectExistsEmail(email);
        // then
        assertThat(exists).isFalse();
    }
}
