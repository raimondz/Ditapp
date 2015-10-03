package cl.fantasticsoft.dita.util.genericAsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * Created by Raimondz on 03-10-15.
 */
public class GenericAsyncTask extends AsyncTask<Void,Void,Void>{

    WeakReference<Context> context;
    ProgressDialog dialog;
    String dialog_title="";
    String dialog_message="";
    Boolean dialog_cancelable=true;

    GenericAsyncTaskDelegate delegate;


    public GenericAsyncTask(Context context){
        this.context=new WeakReference<Context>(context);
        dialog=new ProgressDialog(context);
    }


    @Override
    protected Void doInBackground(Void... voids) {
        if(delegate!=null)
            delegate.onBackground();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();

        if(delegate!=null)
            delegate.onComplete(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle(dialog_title);
        dialog.setMessage(dialog_message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(dialog_cancelable);
        dialog.show();

        if(delegate!=null)
            delegate.onPreExecute();

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        if(delegate!=null)
            delegate.onComplete(true);
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);

        if(delegate!=null)
            delegate.onComplete(true);
    }


    //Getter and setter begins here.

    public ProgressDialog getDialog() {
        return dialog;
    }

    public void setDialog(ProgressDialog dialog) {
        this.dialog = dialog;
    }

    public String getDialog_title() {
        return dialog_title;
    }

    public void setDialog_title(String dialog_title) {
        this.dialog_title = dialog_title;
    }

    public String getDialog_message() {
        return dialog_message;
    }

    public void setDialog_message(String dialog_message) {
        this.dialog_message = dialog_message;
    }

    public Boolean getDialog_cancelable() {
        return dialog_cancelable;
    }

    public void setDialog_cancelable(Boolean dialog_cancelable) {
        this.dialog_cancelable = dialog_cancelable;
    }

    public GenericAsyncTaskDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(GenericAsyncTaskDelegate delegate) {
        this.delegate = delegate;
    }
}
