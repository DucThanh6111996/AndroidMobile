package com.mta.studyenglish.app;

import android.app.Application;
import android.content.Context;

import com.mta.studyenglish.helper.MySqliteHelper;
import com.mta.studyenglish.helper.PrefManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 */

public class AppController extends Application {

    private static AppController mInstance;
    private static MySqliteHelper sqlitHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        new PrefManager(this);

        initDB();
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static synchronized MySqliteHelper getSqlitHelper() {
        return sqlitHelper;
    }

    private void initDB() {
        sqlitHelper = new MySqliteHelper(this);
        File database = getApplicationContext().getDatabasePath(MySqliteHelper.DATABASE_NAME);
        if (!database.exists()) {
            sqlitHelper.getReadableDatabase();
            copyDatabase(this);
        }
    }

    private void copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(MySqliteHelper.DATABASE_NAME);
            String outFileName = MySqliteHelper.DBLOCATION + MySqliteHelper.DATABASE_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
