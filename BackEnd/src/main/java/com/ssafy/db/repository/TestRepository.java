package com.ssafy.db.repository;


import com.ssafy.api.service.TestService;
import com.ssafy.db.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {

}
