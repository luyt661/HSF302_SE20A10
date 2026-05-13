package com.example.jpa_demo.service;

import com.example.jpa_demo.pojo.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void create(String name, String email, String age) {
        Student student = new Student();
        student.setFullName(name);
        student.setEmail(email);
        student.setAge(age);
        em.persist(student);
        System.out.println("save with id " + student.getId());
    }

    @Transactional
    public void update(Integer id, String newName, String newEmail, String newAge) {
        Student student = em.find(Student.class, id);

        if (student != null) {

            student.setFullName(newName);
            student.setEmail(newEmail);
            student.setAge(newAge);

            em.merge(student);
            System.out.println("Updated student with ID: " + id);
        } else {
            System.out.println("Student with ID " + id + " not found!");
        }
    }
    @Transactional
    public void delete(Integer id) {
        Student student = em.find(Student.class, id);

        if (student != null) {
            em.remove(student);
            System.out.println("Deleted student with ID: " + id);
        } else {
            System.out.println("Student with ID " + id + " not found!");
        }
    }

    @Transactional
    public void printAll() {
        em.createQuery("select s from Student s", Student.class).getResultList()
                .forEach(System.out::println);

    }
}
