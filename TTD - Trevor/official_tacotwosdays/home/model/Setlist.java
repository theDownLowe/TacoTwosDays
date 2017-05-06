package groupn.tcss450.uw.edu.officialtaco.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by John on 4/23/2017.
 */

public final class Setlist implements Parcelable {

    /**
     * An array of sample (dummy) items.
     */
    public static final ArrayList<RestaurantItem> ITEMS = new ArrayList<RestaurantItem>();


    private static String mName;

    private static String mAddress;

    private static String mPhone;

    private static String mHours;

    private static String mRating;

    private static String mTypes;

    private static String mUrl;

    private static String mWebsite;


    private static String mId;


    public static Setlist getSetListData(JSONObject object) throws JSONException {
        Setlist current = null;

        current.mName = object.getString("name");

        current.mAddress = object.getString("formatted_address");

        current.mPhone = object.getString("formatted_phone_number");

        // starts with monday
        current.mHours = convert(listHelp(object.getJSONObject("opening_hours"), "weekday_text"));

        current.mRating = Integer.toString(object.getInt("rating"));

        current.mTypes = convert(listHelp(object, "types"));

        current.mUrl = object.getString("url");

        current.mWebsite = object.getString("website");


        current.mId = object.getString("place_id");

        return current;
    }

    public static Setlist getList(JSONArray arr) throws JSONException {
        Setlist curr = null;
        for(int i = 0; i<arr.length();i++) {
            JSONObject ob = arr.getJSONObject(i);
            ITEMS.add(new RestaurantItem(ob.getString("place_id"), ob.getString("name"),
                    Integer.toString(ob.getInt("rating")), ob.getString("formatted_address"),
                    convert(listHelp(ob, "types"))));

        }
        return curr;

    }

    private static String[] listHelp(JSONObject obj, String str) {
        String[] tem = new String[30];
        JSONArray ar = null;
        JSONObject object = null;
        int i = 0;
        try {
            ar = obj.getJSONArray(str);

            while(i<ar.length()) {
                tem[i] = ar.getString(i);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tem;
    }

    public static String getId() {return mId;}
    public static String getTitle() { return mName; }
    public static String getAddress() { return mAddress; }
    public static String getPhone() { return mPhone; }
    public static String getUrl() { return mUrl; }
    public static String getWebsite() { return mWebsite; }
    public static String getRating() { return mRating; }
    public static String getHours() { return mHours; }
    public static String getTypes() { return mTypes; }

    public static ArrayList<RestaurantItem> getItems() {  return ITEMS;  }

    public Setlist(Parcel in) {
        String[] data = new String[9];

        in.readStringArray(data);
        this.mName = data[0];

        this.mAddress = data[1];

        this.mPhone = data[2];

        this.mHours = data[3];

        this.mRating = data[4];

        this.mTypes = data[5];

        this.mUrl = data[6];

        this.mWebsite = data[7];

        this.mId = data[8];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.mName, this.mPhone, this.mHours, this.mRating,
                this.mTypes, this.mUrl, this.mWebsite, this.mId});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Setlist createFromParcel(Parcel in) {
            return new Setlist(in);
        }

        public Setlist[] newArray(int size) {
            return new Setlist[size];
        }
    };


    public static class RestaurantItem implements Parcelable {
        public final String id;
        public final String mtitle;
        public final String mrating;
        public final String maddress;
        public final String mtype;

        public RestaurantItem(CharSequence id, CharSequence title, CharSequence rating, CharSequence address,
                              CharSequence type) {
            this.id = id.toString();
            this.mtitle = title.toString();
            this.mrating = rating.toString();
            this.maddress = address.toString();
            this.mtype = type.toString();
        }

        protected RestaurantItem(Parcel in) {
            String[] array = new String[5];
            in.writeStringArray(array);
            id = array[0];
            mtitle = array[1];
            mrating = array[2];
            maddress = array[3];
            mtype = array[4];
        }

        public static final Creator<RestaurantItem> CREATOR = new Creator<RestaurantItem>() {
            @Override
            public RestaurantItem createFromParcel(Parcel in) {
                return new RestaurantItem(in);
            }

            @Override
            public RestaurantItem[] newArray(int size) {
                return new RestaurantItem[size];
            }
        };

        @Override
        public String toString() {
            return mtitle.toString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringArray(new String[] {id, mtitle,
                mrating, maddress, mtype});
        }
    }


    public static String convert(String[] list) {
        String res = "";
        for(int i=0;i<list.length;i++) {
            if(list[i] != null)
                res = res + "\n" + list[i];
        }
        return res;
    }
}
