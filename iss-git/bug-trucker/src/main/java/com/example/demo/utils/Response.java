package com.example.demo.utils;

import com.example.demo.model.Programmer;
import com.example.demo.model.Tester;

public class Response {
    private LoginType loginType;
    private Programmer prog;
    private Tester tester;

    public Response(LoginType loginType) {
        this.loginType = loginType;
    }

    public Response(LoginType loginType, Tester tester) {
        this.loginType = loginType;
        this.tester = tester;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public Programmer getProg() {
        return prog;
    }

    public void setProg(Programmer prog) {
        this.prog = prog;
    }

    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }
}
