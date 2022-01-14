package com.linkedin.api.Twitter;

import java.util.ArrayList;
import java.util.List;

public class StreamRuleRequest {

    private List<StreamRule> add = new ArrayList<>();

    public List<StreamRule> getAdd() {
        return add;
    }

    public void setAdd(List<StreamRule> add) {
        this.add = add;
    }

    public void addRule(String value, String tag) {
        this.add.add(new StreamRule(value,tag));
    }

}
