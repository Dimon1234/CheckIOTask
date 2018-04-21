package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("courses")
    @Expose
    private List<Course> courses = null;
    @SerializedName("enrollments")
    @Expose
    private List<Object> enrollments = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Object> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Object> enrollments) {
        this.enrollments = enrollments;
    }

}