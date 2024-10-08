package com.avshek.vast_parser.repository;

import com.avshek.vast_parser.model.VastData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VastTagRepository extends JpaRepository<VastData, Long> {

}