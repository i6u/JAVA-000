package org.geekbang.work4;

public class SubThread implements Runnable {

    private String result;

    public SubThread(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    @Override
    public void run() {
        result += "!";
    }
}