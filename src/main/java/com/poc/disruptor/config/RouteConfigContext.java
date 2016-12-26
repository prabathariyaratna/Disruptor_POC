package com.poc.disruptor.config;

import com.poc.disruptor.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabath on 12/27/16.
 */
public class RouteConfigContext {

    private int totalCount;
    private volatile int missCount = 0;
    private List<Route> finalizeRoutes;

    public int getMissCount() {
        return missCount;
    }

    public synchronized void increase() {
        ++this.missCount;
    }

    public List<Route> getFinalizeRoutes() {
        return finalizeRoutes;
    }

    public void setFinalizeRoutes(Route route) {

        if(finalizeRoutes == null) {
            this.finalizeRoutes = new ArrayList<>();
        }
        this.finalizeRoutes.add(route);
    }

    public boolean isComplete() {
        if(totalCount == missCount + (finalizeRoutes == null?0 : finalizeRoutes.size())) {
            return true;
        }

        return false;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
