package com.poc.disruptor;

import com.poc.disruptor.config.RouteConfigContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by prabath on 12/26/16.
 */
public class Route {

    private String name;
    private int age;
    private boolean filterOneExecutes;
    private RouteConfigContext configContext;
    private boolean valid;
    private List<String> executedFilters;
    private int numberOfFilters;

    public Route(String name, int age, RouteConfigContext configContext, int numberOfFilters) {
        this.name = name;
        this.age = age;
        this.configContext = configContext;
        this.numberOfFilters = numberOfFilters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean canExecute() {
        return filterOneExecutes;
    }

    public void setFilterOneExecutes(boolean filterOneExecutes) {
        this.filterOneExecutes = filterOneExecutes;
    }

    public boolean isFilterOneExecutes() {
        return filterOneExecutes;
    }

    public RouteConfigContext getConfigContext() {
        return configContext;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void visited(String name) {
        if(executedFilters == null) {
            this.executedFilters = new ArrayList<>();
        }

        this.executedFilters.add(name);
    }

    public boolean isFinalized() {
        if(executedFilters == null || executedFilters.size() != numberOfFilters) {
            return false;
        }

        return true;
    }
}
