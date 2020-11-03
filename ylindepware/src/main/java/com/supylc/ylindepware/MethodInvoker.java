package com.supylc.ylindepware;

import android.os.Parcel;
import android.os.Parcelable;

import com.supylc.ylindepware.base.serialize.SerializeConverter;
import com.supylc.ylindepware.base.serialize.SerializeInvokeParam;

import java.util.ArrayList;
import java.util.List;

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

    public void addMethodParam(SerializeInvokeParam param) {
        mParamList.add(SerializeConverter.INSTANCE.methodInvokeToJson(param));
    }

    public String getMethodName() {
        return mMethodName;
    }

    public void setMethodName(String methodName) {
        this.mMethodName = methodName;
    }

    public List<SerializeInvokeParam> getParamList() {
        return SerializeConverter.INSTANCE.getMethodInvokeList(mParamList);
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

    @Override
    public String toString() {
        return "MethodInvoker{" +
                "mMethodName='" + mMethodName + '\'' +
                ", mParamList=" + mParamList +
                '}';
    }
}
