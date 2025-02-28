package com.example.E_care.media.dao;

import com.example.E_care.media.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MediaDao extends JpaRepository<Media, Long>{

    @Transactional
    void deleteByCoursId(Long coursId);

}
