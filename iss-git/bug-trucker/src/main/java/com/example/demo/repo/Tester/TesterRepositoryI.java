package com.example.demo.repo.Tester;

import com.example.demo.model.Tester;
import com.example.demo.repo.IRepo;

public interface TesterRepositoryI extends IRepo<Integer, Tester> {
    Tester findByUsernamePwd(String username, String password);
}
