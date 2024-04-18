package DatabaseOP;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import Models.ExpenseData;

public class DBHelper extends SQLiteOpenHelper {
    static String DBNAME = "expensetracker.db";
    static int VERSION = 1;

    //expense details
    static String FOODITEM_COL1 = "id";
    static String FOODITEM_COL2 = "amount";
    static String FOODITEM_COL3 = "date";
    static String FOODITEM_COL4 = "category";

    // table names
    static String EXPENSES_TABLE_NAME = "Expenses";


    static String CREATE_EXPENSE_TABLE = "CREATE TABLE " + EXPENSES_TABLE_NAME + " ("
            + FOODITEM_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FOODITEM_COL2 + " REAL NOT NULL, "
            + FOODITEM_COL3 + " TEXT NOT NULL, "
            + FOODITEM_COL4 + " TEXT NOT NULL); ";


    static final String DROP_EXPENSE_TABLE = "DROP TABLE IF EXISTS " + EXPENSES_TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_EXPENSE_TABLE);
        onCreate(sqLiteDatabase);
    }


    // insert EXPENSE to to database
    public boolean InsertExpenseData(ExpenseData expenseData) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FOODITEM_COL2, expenseData.getAmount());
        cv.put(FOODITEM_COL3, expenseData.getDate());
        cv.put(FOODITEM_COL4, expenseData.getCategory());

        long result = sqLiteDatabase.insert(EXPENSES_TABLE_NAME, null, cv);
        return (result != -1);
    }

    // get all EXPENSES
    public Cursor getAllExpenses() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor;
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + EXPENSES_TABLE_NAME, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


}
