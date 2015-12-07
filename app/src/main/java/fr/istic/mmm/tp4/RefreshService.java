package fr.istic.mmm.tp4;


import android.app.IntentService;
import android.content.Intent;

public class RefreshService extends IntentService {

    public RefreshService(){super(RefreshService.class.getName());}

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent i = new Intent();
        i.setAction(Main3Activity.Reciever.PROCESS_RESPONSE);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        // send BroadCast every 2 s
        while (true){
            try {
                Thread.sleep(2000);
                sendBroadcast(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
