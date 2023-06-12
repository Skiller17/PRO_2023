package com.example.BackEnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.BackEnd.models.Country;
public interface CountryRepository extends JpaRepository<Country, Long>{


}
