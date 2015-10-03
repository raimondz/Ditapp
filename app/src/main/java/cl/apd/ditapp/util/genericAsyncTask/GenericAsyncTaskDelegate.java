package cl.apd.ditapp.util.genericAsyncTask;

/**
 * Created by Raimondz on 03-10-15.
 */
public interface GenericAsyncTaskDelegate {
    public void onPreExecute();
    public void onBackground();
    public void onComplete(Boolean is_canceled);
}
