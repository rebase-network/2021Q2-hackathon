package greendao.gen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by DELL on 2020/7/29.
 */

public class GreenDaoHelper extends DaoMaster.OpenHelper {
    private static final String DB_NAME = "wallet.db";      //数据库名字

    public GreenDaoHelper(Context context) {
        super(context, DB_NAME, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //升级数据库的操作，需要确保数据不会被清除
        super.onUpgrade(db, oldVersion, newVersion);


        if (oldVersion != newVersion) {
            //TODO：升级软件时，将数据保留。后面做
            //升级数据库,修改build.gradle中的versioncode 和 schemaVersion , 统一
            Log.i("test", "onUpgrade: 测试升级 " + "old " + oldVersion + " " + newVersion);
            try {
                DaoMaster.dropAllTables((new DaoMaster(db)).getDatabase(), true);
            }catch (Exception e) {
                e.printStackTrace();
            }
            DaoMaster.createAllTables((new DaoMaster(db)).getDatabase(), true);
        }
    }
}
