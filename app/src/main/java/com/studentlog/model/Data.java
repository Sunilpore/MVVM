package com.studentlog.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Data<T> {

    @Nullable
    public String message;

    @Nullable
    public Status status;

    @Nullable
    public final T data;


    public Data(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Data<T> success (@Nullable T data) {
        return new Data<>(Status.SUCCESS, data, null);
    }

    public static <T> Data<T> error(@NonNull String msg, @Nullable T data) {
        return new Data<>(Status.ERROR, data, msg);
    }

    public enum Status { SUCCESS, ERROR}

}
