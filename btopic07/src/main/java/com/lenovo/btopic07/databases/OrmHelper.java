package com.lenovo.btopic07.databases;

import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.lenovo.basic.BasicApplication;
import com.lenovo.btopic07.bean.HistoryBean;

import java.sql.SQLException;

/**
 * @author ayuan
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper {
    private static OrmHelper ormHelper;

    public OrmHelper() {
        super(BasicApplication.getmContext(), "simple", null, 2);
    }

    public static OrmHelper getInstance() {
        if (ormHelper == null) {
            synchronized (OrmHelper.class) {
                if (ormHelper == null) {
                    ormHelper = new OrmHelper();
                }
            }
        }
        return ormHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, HistoryBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        onCreate(sqLiteDatabase, connectionSource);
    }
}
