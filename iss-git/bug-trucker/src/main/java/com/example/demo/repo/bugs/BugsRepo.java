package com.example.demo.repo.bugs;

import com.example.demo.model.Bug;
import com.example.demo.model.BugStatus;
import com.example.demo.repo.IRepo;

public interface BugsRepo extends IRepo<Integer, Bug> {
    Bug findByID(int id);
    boolean updateExperimental(int id, String name, String description);
    boolean solveBug(int id, BugStatus status);
}
