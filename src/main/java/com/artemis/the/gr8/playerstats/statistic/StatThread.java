package com.artemis.the.gr8.playerstats.statistic;

import com.artemis.the.gr8.playerstats.ThreadManager;
import com.artemis.the.gr8.playerstats.msg.OutputManager;
import com.artemis.the.gr8.playerstats.utils.MyLogger;
import com.artemis.the.gr8.playerstats.enums.StandardMessage;
import com.artemis.the.gr8.playerstats.reload.ReloadThread;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * The Thread that is in charge of getting and calculating statistics.
 */
public final class StatThread extends Thread {

    private static OutputManager outputManager;
    private final RequestManager statManager;

    private final ReloadThread reloadThread;
    private final StatRequest<?> statRequest;

    public StatThread(OutputManager m, RequestManager stat, int ID, StatRequest<?> s, @Nullable ReloadThread r) {
        outputManager = m;
        statManager = stat;
        reloadThread = r;
        statRequest = s;

        this.setName("StatThread-" + statRequest.getSettings().getCommandSender().getName() + "-" + ID);
        MyLogger.logHighLevelMsg(this.getName() + " created!");
    }

    @Override
    public void run() throws IllegalStateException {
        MyLogger.logHighLevelMsg(this.getName() + " started!");
        CommandSender statRequester = statRequest.getSettings().getCommandSender();

        if (reloadThread != null && reloadThread.isAlive()) {
            try {
                MyLogger.logLowLevelMsg(this.getName() + ": Waiting for " + reloadThread.getName() + " to finish up...");
                outputManager.sendFeedbackMsg(statRequester, StandardMessage.STILL_RELOADING);
                reloadThread.join();

            } catch (InterruptedException e) {
                MyLogger.logException(e, "StatThread", "Trying to join " + reloadThread.getName());
                throw new RuntimeException(e);
            }
        }

        long lastCalc = ThreadManager.getLastRecordedCalcTime();
        if (lastCalc > 2000) {
            outputManager.sendFeedbackMsgWaitAMoment(statRequester, lastCalc > 20000);
        }

        try {
            StatResult<?> result = statManager.execute(statRequest);
            outputManager.sendToCommandSender(statRequester, result.formattedComponent());
        }
        catch (ConcurrentModificationException e) {
            if (!statRequest.getSettings().isConsoleSender()) {
                outputManager.sendFeedbackMsg(statRequester, StandardMessage.UNKNOWN_ERROR);
            }
        }
    }
}