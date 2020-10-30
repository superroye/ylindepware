package com.supylc.ylindepware;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.supylc.ylindepware.base.MethodInvokeParam;

import java.util.ArrayList;

/**
 * Created by Supylc on 2020/10/29.
 */
public class MethodInvoker implements Parcelable {

    private String mMethodName = "";
    private ArrayList<String> mParamList;

    public MethodInvoker() {
        mParamList = new ArrayList();
    }

    public MethodInvoker(Parcel in) {
        mMethodName = in.readString();
        mParamList = in.readArrayList(String.class.getClassLoader());
    }

    public void addMethodParam(MethodInvokeParam param) {
        mParamList.add(param);
    }

    public String getMethodName() {
        return mMethodName;
    }

    public void setMethodName(String methodName) {
        this.mMethodName = methodName;
    }

    public ArrayList<MethodInvokeParam> getParamList() {
        return mParamList;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMethodName);
        parcel.writeList(mParamList);
    }

    public static final Creator<MethodInvoker> CREATOR = new Creator<MethodInvoker>() {
        @Override
        public MethodInvoker createFromParcel(Parcel in) {
            return new MethodInvoker(in);
        }

        @Override
        public MethodInvoker[] newArray(int size) {
            return new MethodInvoker[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
