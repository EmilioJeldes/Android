package cl.ejeldes.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Void, String> {

    private WeakReference<TextView> mTextView;

    public SimpleAsyncTask(TextView mTextView) {
        this.mTextView = new WeakReference<>(mTextView);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random random = new Random();
        int number = random.nextInt(11);

        int seconds = number * 200;

        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Awake at last after sleeping for " + seconds + " milliseconds";
    }


    @Override
    protected void onPostExecute(String s) {
        mTextView.get().setText(s);
    }
}
