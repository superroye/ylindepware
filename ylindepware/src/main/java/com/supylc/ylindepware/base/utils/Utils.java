package com.supylc.ylindepware.base.utils;

import android.os.Process;

import com.supylc.ylindepware.internal.IndepWareContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Supylc on 2020/10/12.
 */
public class Utils {

    public static String getProcessName() {
        BufferedReader bufferedReader = null;

        String processName;
        try {
            File file = new File("/proc/" + Process.myPid() + "/" + "cmdline");
            bufferedReader = new BufferedReader(new FileReader(file));
            processName = bufferedReader.readLine().trim();
            String var3 = processName;
            return var3;
        } catch (Exception var13) {
            var13.printStackTrace();
            processName = "";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }
            }
        }

        return processName;
    }

    public static boolean isMainProcess() {
        String processName = getProcessName();
        return IndepWareContext.INSTANCE.getApp().getPackageName().equals(processName);
    }
}
