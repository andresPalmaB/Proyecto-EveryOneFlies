package com.ms_reserve.reserve.repository;

import com.ms_reserve.reserve.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<Reserve, Long> { }
