package com.decagon.karrigobe.repositories;

import com.decagon.karrigobe.entities.model.DriverTaskEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverTaskRepository extends JpaRepository<DriverTaskEntity, Long> {
    Slice<DriverTaskEntity> findByDriverEntityId(Long driverId, Pageable pageable);
    Slice<DriverTaskEntity> findByDriverEntityEmail(String email, Pageable pageable);
}
