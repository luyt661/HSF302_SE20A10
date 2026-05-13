package com.example.jpa_demo.service;

import com.example.jpa_demo.pojo.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @PersistenceContext
    private EntityManager em;

    @Test
    void testCreate() {
        // 1. Chạy hàm create
        studentService.create("Phan Văn Luýt", "luyt@fpt.edu.vn", "20");

        // 2. Ép xuống DB và xóa cache (giống ảnh 2.17.46 PM)
        em.flush();
        em.clear();

        // 3. Kiểm tra bằng query
        Student result = em.createQuery("select s from Student s where s.email = :e", Student.class)
                .setParameter("e", "luyt@fpt.edu.vn")
                .getSingleResult();

        assertNotNull(result, "Sinh viên phải tồn tại trong DB");
        assertEquals("Phan Văn Luýt", result.getFullName());
    }

    @Test
    void testUpdate() {
        // 1. Tạo mẫu
        studentService.create("Tên Cũ", "old@gmail.com", "18");
        em.flush();
        em.clear();

        Student s = em.createQuery("select s from Student s where s.email = 'old@gmail.com'", Student.class)
                .getSingleResult();

        // 2. Thực hiện update
        studentService.update(s.getId(), "Tên Mới", "new@gmail.com", "21");
        em.flush();
        em.clear();

        // 3. Kiểm tra lại bằng find (buộc phải load từ DB do đã clear cache)
        Student updated = em.find(Student.class, s.getId());
        assertNotNull(updated);
        assertEquals("Tên Mới", updated.getFullName());
        assertEquals("new@gmail.com", updated.getEmail());
    }

    @Test
    void testDelete() {
        // 1. Tạo mẫu để xóa
        studentService.create("Xóa", "delete@gmail.com", "22");
        em.flush();
        em.clear();

        Student s = em.createQuery("select s from Student s where s.email = 'delete@gmail.com'", Student.class)
                .getSingleResult();

        // 2. Thực hiện xóa
        studentService.delete(s.getId());
        em.flush();
        em.clear();

        // 3. Kiểm tra xem còn không
        Student result = em.find(Student.class, s.getId());
        assertNull(result, "Sinh viên phải bị xóa khỏi DB");
    }
}