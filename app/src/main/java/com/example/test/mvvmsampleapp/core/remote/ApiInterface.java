package com.example.test.mvvmsampleapp.core.remote;

import com.example.test.mvvmsampleapp.feature.main.projectlist.model.Project;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("users/{user}/repos")
    Observable<List<Project>> getProjectList(@Path("user") String user);

}
