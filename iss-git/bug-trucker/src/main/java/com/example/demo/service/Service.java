package com.example.demo.service;

import com.example.demo.model.Bug;
import com.example.demo.model.BugStatus;
import com.example.demo.model.Programmer;
import com.example.demo.model.Tester;
import com.example.demo.repo.bugs.BugsRepo;
import com.example.demo.repo.bugs.BugsRepoDB;
import com.example.demo.repo.programmers.ProgrammersRepo;
import com.example.demo.repo.programmers.ProgrammersRepoDB;
import com.example.demo.repo.Tester.TesterRepositoryI;
import com.example.demo.repo.Tester.TesterRepository;
import com.example.demo.utils.Response;
import com.example.demo.utils.LoginType;
import com.example.demo.utils.event.BugEntityChangeEvent;
import com.example.demo.utils.event.ChangeEventType;
import com.example.demo.utils.observer.Observable;
import com.example.demo.utils.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Service implements Observable<BugEntityChangeEvent> {
    private BugsRepo bugsRepo;
    private ProgrammersRepo programmersRepo;
    private TesterRepositoryI testerRepositoryI;
    private List<Observer<BugEntityChangeEvent>> observers = new ArrayList<>();

    public Service(BugsRepo bugsRepo, ProgrammersRepo programmersRepo, TesterRepositoryI testerRepositoryI) {
        this.bugsRepo = bugsRepo;
        this.programmersRepo = programmersRepo;
        this.testerRepositoryI = testerRepositoryI;
    }

    public List<Bug> getAllBugs() {
        BugsRepoDB.initialize();
        List<Bug> bugs = bugsRepo.getAll();
        BugsRepoDB.close();
        return bugs;
    }

    public Programmer findProgrammerByUsernamePwd(String username, String pwd) {
        ProgrammersRepoDB.initialize();
        Programmer prog = programmersRepo.findByUsernamePwd(username, pwd);
        ProgrammersRepoDB.close();
        return prog;
    }

    public Tester findQAByUsernamePwd(String username, String pwd) {
        TesterRepository.initialize();
        Tester tester = testerRepositoryI.findByUsernamePwd(username, pwd);
        TesterRepository.close();
        return tester;
    }

    public Response login(String username, String password) {
        Response response = new Response(LoginType.ERROR);
        Tester loggedInTester = findQAByUsernamePwd(username, password);
        if (loggedInTester != null) {
            response.setLoginType(LoginType.TESTER);
            response.setTester(loggedInTester);
        } else {
            Programmer loggedInProgrammer = findProgrammerByUsernamePwd(username, password);
            response.setLoginType(LoginType.PROGRAMMER);
            response.setProg(loggedInProgrammer);
        }
        return response;
    }

    public void save(String name, String description) throws IOException {
        Bug bug = new Bug(name, description, BugStatus.UNSOLVED);
        bugsRepo.save(bug);
        notifyObservers(new BugEntityChangeEvent(ChangeEventType.SAVE, bug));
    }

    public void solveBug(int id){
        bugsRepo.solveBug(id, BugStatus.SOLVED);
        notifyObservers(new BugEntityChangeEvent(ChangeEventType.SOLVE, new Bug(id, "random", "random", BugStatus.SOLVED)));
    }

    @Override
    public void addObserver(Observer<BugEntityChangeEvent> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<BugEntityChangeEvent> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(BugEntityChangeEvent t) {
        observers.forEach(x -> x.update(t));
    }
}
