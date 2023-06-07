package com.example.demo.repo.programmers;

import com.example.demo.model.Programmer;
import com.example.demo.repo.IRepo;

public interface ProgrammersRepo extends IRepo<Integer, Programmer> {
    Programmer findByUsernamePwd(String username, String pass);
}
