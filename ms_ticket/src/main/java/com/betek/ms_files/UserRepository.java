package com.betek.ms_files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Método para encontrar un usuario por su idPassenger
    User findByIdPassenger(int idPassenger);
}

