package com.scriptfloor.hda.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.scriptfloor.hda.R;
import com.scriptfloor.hda.models.BatchModel;
import com.scriptfloor.hda.models.DrugModel;
import com.scriptfloor.hda.models.FacilityModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Lincoln on 8/3/2018.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hda_db";

    // Login table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_DRUGS = "drugs";
    private static final String TABLE_BATCH="batch";
    private static final String TABLE_FACILITIES = "facilities";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
    // Drugs Table Columns names
    private static final String KEY_DRUG_BRAND = "brand";
    private static final String KEY_DRUG_GENERIC = "generic";
    private static final String KEY_DRUG_CLASS = "class";
    private static final String KEY_DRUG_COMPANY = "company";
    private static final String KEY_DRUG_COUNTRY = "country";
    private static final String KEY_DRUG_TYPE = "type";
    // Facility Table Columns names
    private static final String KEY_FACILITY_NAME = "name";
    private static final String KEY_FACILITY_ADDRESS= "address";
    private static final String KEY_FACILITY_SECTOR = "sector";
    private static final String KEY_FACILITY_CATEGORY = "category";

    // Facility Table Columns names
    private static final String KEY_BATCH_NO = "batchno";
    private static final String KEY_DRUG_ID= "drug_id";
    private static final String KEY_DATE_MADE = "made";
    private static final String KEY_DATE_EXPIRY= "expiry";

    private String CREATE_FACILITIES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FACILITIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FACILITY_NAME + " TEXT,"
            + KEY_FACILITY_ADDRESS + " TEXT," + KEY_FACILITY_SECTOR + " TEXT,"
            + KEY_FACILITY_CATEGORY + " TEXT,"+ KEY_CREATED_AT + " TEXT" + ")";

    private String CREATE_DRUGS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_DRUGS + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DRUG_BRAND + " TEXT,"
            + KEY_DRUG_GENERIC + " TEXT," + KEY_DRUG_CLASS + " TEXT,"
            + KEY_DRUG_COMPANY + " TEXT," + KEY_DRUG_COUNTRY + " TEXT,"
            + KEY_DRUG_TYPE+ " TEXT,"
            + KEY_CREATED_AT + " TEXT" + ")";

    private String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
            + KEY_CREATED_AT + " TEXT" + ")";

    private String CREATE_BATCH_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_BATCH + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_BATCH_NO + " TEXT,"
            + KEY_DRUG_ID + " TEXT," + KEY_DATE_MADE + " TEXT,"
            + KEY_DATE_EXPIRY + " TEXT,"
            + KEY_CREATED_AT + " TEXT" + ")";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_DRUGS_TABLE);
        db.execSQL(CREATE_FACILITIES_TABLE);
        db.execSQL(CREATE_BATCH_TABLE);
        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRUGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACILITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATCH);
        // Create tables again
        onCreate(db);

    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void dropTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BATCH);
        onCreate(db);
        db.close();
    }
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Storing drug details in database
     * */
    public void addDrug(String brand, String generic, String class_name,String company, String country,String type, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DRUG_BRAND, brand); // brand
        values.put(KEY_DRUG_GENERIC, generic); // generic
        values.put(KEY_DRUG_CLASS, class_name); // class
        values.put(KEY_DRUG_COMPANY, company); // class
        values.put(KEY_DRUG_COUNTRY, country); // class
        values.put(KEY_DRUG_TYPE,type);//type
        values.put(KEY_CREATED_AT, created_at); // Created At
        // Inserting Row
        long id = db.insert(TABLE_DRUGS, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New drug inserted into sqlite: " + id);
    }
    /**
     * Storing Facility details in database
     * */
    public void addFacility(String name, String address, String sector,String category,String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FACILITY_NAME, name); // brand
        values.put(KEY_FACILITY_ADDRESS, address); // generic
        values.put(KEY_FACILITY_SECTOR, sector); // class
        values.put(KEY_FACILITY_CATEGORY, category); // class
        values.put(KEY_CREATED_AT, created_at); // Created At
        // Inserting Facility
        long id = db.insert(TABLE_FACILITIES, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New Facility inserted into DB: " + id);
    }

    public void addBatch(String BatchNo, String DrugId, String DateMade,String DateExpiry,String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BATCH_NO, BatchNo); // brand
        values.put(KEY_DRUG_ID, DrugId); // generic
        values.put(KEY_DATE_MADE, DateMade); // class
        values.put(KEY_DATE_EXPIRY, DateExpiry); // class
        values.put(KEY_CREATED_AT, created_at); // Created At
        // Inserting Facility
        long id = db.insert(TABLE_BATCH, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New Batch inserted into DB: " + id);
    }
    public void createFacilityTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FACILITIES);
        db.execSQL(CREATE_FACILITIES_TABLE);
        db.close();
    }

    public void createDrugTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_DRUGS_TABLE);
        db.close();
    }
    /**
     * Getting drug data from database
     * */
    public HashMap<String, String> getDrugDetails(String search) {
        HashMap<String, String> drug = new HashMap<String, String>();
        String selectQuery = "select * from " + TABLE_DRUGS +" where "+KEY_DRUG_BRAND +" like '%"+search+"%'"
                +" ORDER BY "+KEY_DRUG_BRAND+" ASC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            drug.put("brand", cursor.getString(1));
            drug.put("generic", cursor.getString(2));
            drug.put("class", cursor.getString(3));
        }
        cursor.close();
        db.close();

        return drug;
    }

    public HashMap<String, String> getBatch(String search) {
        HashMap<String, String> drug = new HashMap<>();
        String selectQuery = "select * from " + TABLE_BATCH +" where batchno='"+search+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_BATCH_TABLE);
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            drug.put("batchno", cursor.getString(1));
            drug.put("drug_id", cursor.getString(2));
            drug.put("made", cursor.getString(3));
            drug.put("expiry", cursor.getString(4));
        }
        cursor.close();
        db.close();

        return drug;
    }

    public List<DrugModel> getDrugList(String searchString){
        List<DrugModel> DrugList = null;
        Log.d(TAG, searchString);
        try {
            DrugList = new ArrayList<>();
            String query =  "select * from " + TABLE_DRUGS+ " ORDER BY "+KEY_DRUG_BRAND+" ASC";
            if(searchString!=""){query =  "select * from " + TABLE_DRUGS +" where "+KEY_DRUG_BRAND +" like '%"+searchString+"%'"
                    +" or "+KEY_DRUG_GENERIC+" like '%"+searchString+"%'" +" or "+KEY_DRUG_COMPANY+" like '%"+searchString+"%'"
                    +" or "+KEY_DRUG_COUNTRY+" like '%"+searchString+"%' ORDER BY "+KEY_DRUG_BRAND+" ASC LIMIT 100";}
            Cursor cursor;
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery(query, null);

            if (cursor != null) {
                //if cursor contains results
                if (cursor.moveToFirst()) {
                    do {
                        DrugModel drug = new DrugModel();
                        drug.setDrugID(cursor.getString(0));
                        drug.setDrugBrand(cursor.getString(1));
                        drug.setDrugGeneric(cursor.getString(2));
                        drug.setDrugClass(cursor.getString(3));
                        drug.setDrugCompany(cursor.getString(4));
                        drug.setDrugCountry(cursor.getString(5));
                        drug.setDrugType(cursor.getString(6));
                        DrugList.add(drug);
                    } while (cursor.moveToNext());
                }
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DrugList;
    }
    public List<BatchModel> getBatchNo(String searchString){
        List<BatchModel> BatchNo = null;
        try {
            BatchNo = new ArrayList<>();
            String query = "select * from " + TABLE_BATCH +" where batchno='"+searchString+"'";
            Cursor cursor;
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery(query, null);

            if (cursor != null) {
                //if cursor contains results
                if (cursor.moveToFirst()) {
                    BatchModel batch = new BatchModel();
                    batch.setBatchNo(cursor.getString(1));
                    batch.setBatchId(cursor.getString(2));
                    batch.setBatchMade(cursor.getString(3));
                    batch.setBatchExpiry(cursor.getString(4));
                    BatchNo.add(batch);
                }
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BatchNo;
    }
    public List<FacilityModel> getFacilities(String searchString){
        List<FacilityModel> facilities=null;
        Log.d(TAG, searchString);
        try {
            facilities = new ArrayList<>();
            String query =  "select * from " + TABLE_FACILITIES+ " ORDER BY "+KEY_FACILITY_NAME+" ASC"; ;
            if(searchString!=""){query =  "select * from " + TABLE_FACILITIES +" where "+KEY_FACILITY_NAME +" like '%"+searchString+"%'"
                    +" or "+KEY_FACILITY_ADDRESS+" like '%"+searchString+"%'" +" or "+KEY_FACILITY_SECTOR+" like '%"+searchString+"%'"
                    +" or "+KEY_FACILITY_CATEGORY+" like '%"+searchString+"%' ORDER BY "+KEY_FACILITY_NAME+" ASC LIMIT 100";}
            Cursor cursor;
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery(query, null);

            if (cursor != null) {
                //if cursor contains results
                if (cursor.moveToFirst()) {
                    do {
                        FacilityModel facility = new FacilityModel();
                        facility.setFacilityID(cursor.getString(0));
                        facility.setFacilityName(cursor.getString(1));
                        facility.setFacilityAddress(cursor.getString(2));
                        facility.setFacilitySector(cursor.getString(3));
                        facility.setFacilityCategory(cursor.getString(4));
                        
                        facilities.add(facility);
                    } while (cursor.moveToNext());
                }
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return facilities;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from Database");
    }
}
