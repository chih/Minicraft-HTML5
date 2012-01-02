package com.mojang.ld22.gwt;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

public class GwtMinicraftEntryPoint implements EntryPoint {
    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(Throwable e) {
                e.printStackTrace();
                Window.alert("Uncaught Exception: \n" + e.getClass().getName() + "\n" + e.getMessage());
            }
        });

        Scheduler.get().scheduleDeferred(new Command() {
            @Override
            public void execute() {
                new GwtMinicraft().go();
            }
        });
    }
}
