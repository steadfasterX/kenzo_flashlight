package com.umang96.flashlight;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;


public class TorchUtils {

    private static void flash_on(Context context){
        String c1 = Executor("echo 100 > ~/sys/class/leds/led:torch_0/brightness");
        String c2 = Executor("echo 100 > ~/sys/class/leds/led:torch_1/brightness");
    }

    private static void flash_off(Context context){
        String c1 = Executor("echo 0 > ~/sys/class/leds/led:torch_0/brightness");
        String c2 = Executor("echo 0 > ~/sys/class/leds/led:torch_1/brightness");
    }

    public static boolean check(Context context){
        String outp = Executor("cat ~/sys/class/leds/led:torch_0/brightness");
            try {
            if (outp.length()==1) {
                flash_on(context);
                return true;
            }
            if (!(outp.length()==1)) {
                flash_off(context);
            }
        }
        catch(Exception e)
        {
            Toast.makeText(context, "Error, torch file permissions correct?",
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public static String Executor(String command) {
        StringBuffer output = new StringBuffer();
        Process p=null;
        try {
            p = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();
        return response;

    }

    /* possible other and hackish way
    public static void RunSH(String[] cmds, Context context){
        try{
            process = Runtime.getRuntime().exec(String.format("sh -c %s", cmds));
        }
        catch(Exception e)
        {
            Toast.makeText(context, "RunSH failed !",
                    Toast.LENGTH_SHORT).show();
        }
    }
    */
}
