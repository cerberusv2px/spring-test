package com.example.learningtest.student;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.learningtest.student.exception.BadRequestException;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    //private AutoCloseable autoCloseable;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new StudentService(studentRepository);
    }

    /*@AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }*/

    @Test
    void getAllStudents() {
        // when
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddStudent() {
        // given
        Student student = new Student("Sujin", "sujin@gmail.com", Gender.MALE);

        // when
        underTest.addStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
        // given
        Student student = new Student("Sujin", "sujin@gmail.com", Gender.MALE);

        given(studentRepository.selectExistsEmail(student.getEmail()))
            .willReturn(true);
        // when
        // then
        assertThatThrownBy(() -> underTest.addStudent(student))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("Email " + student.getEmail() + " taken");

        verify(studentRepository, never()).save(any());

    }

    @Test
    void deleteStudent() {
        // given
        Student student = new Student("Sujin", "sujin@gmail.com", Gender.MALE);
        Long id = 10L;
        given(studentRepository.existsById(id)).willReturn(true);
        // when
        underTest.deleteStudent(id);

        ArgumentCaptor<Long> studentArgumentCaptor = ArgumentCaptor.forClass(Long.class);

        verify(studentRepository).deleteById(studentArgumentCaptor.capture());

        Long capturedStudentId = studentArgumentCaptor.getValue();
        verify(studentRepository, Mockito.atLeastOnce()).deleteById(any());


    }
}
