package com.drovo.Repositery;

import com.drovo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Person, Long> {
}
