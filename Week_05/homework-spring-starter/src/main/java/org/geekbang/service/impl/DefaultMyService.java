package org.geekbang.service.impl;

import org.geekbang.service.MyService;

public class DefaultMyService implements MyService {
    @Override
    public String showMe() {
        return "I'm ok";
    }
}
