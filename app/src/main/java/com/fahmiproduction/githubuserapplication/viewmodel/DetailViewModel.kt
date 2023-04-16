package com.fahmiproduction.githubuserapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fahmiproduction.githubuserapplication.api.ApiConfig
import com.fahmiproduction.githubuserapplication.datamodel.UserDetailsResponse
import com.fahmiproduction.githubuserapplication.datamodel.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: MainViewModel() {

    private val _followerList = MutableLiveData<List<UserResponse>>()
    private val _followingList = MutableLiveData<List<UserResponse>>()
    private val _userDetail = MutableLiveData<UserDetailsResponse>()

    fun getUserDetails(username: String): LiveData<UserDetailsResponse> {
        if (_userDetail.value == null) {
            setOnLoad()
            ApiConfig.getGitHubApiService().getUserDetail(username)
                .enqueue(object : Callback<UserDetailsResponse> {
                    override fun onResponse(
                        call: Call<UserDetailsResponse>,
                        response: Response<UserDetailsResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null)
                            _userDetail.value = response.body()
                        else setFail()
                        setEndLoad()
                    }

                    override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                        setFail()
                        setEndLoad()
                    }
                })
        }

        return _userDetail
    }

    fun getFollowers(username: String): LiveData<List<UserResponse>> {
        if (_followerList.value == null) {
            setOnLoad()
            ApiConfig.getGitHubApiService().getFollowers(username)
                .enqueue(object : Callback<List<UserResponse>> {
                    override fun onResponse(
                        call: Call<List<UserResponse>>,
                        response: Response<List<UserResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null)
                            _followerList.value = response.body()
                        else setFail()
                        setEndLoad()
                    }

                    override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                        setFail()
                        setEndLoad()
                    }
                })
        }

        return _followerList
    }

    fun getFollowings(username: String): LiveData<List<UserResponse>> {
        if (_followingList.value == null) {
            setOnLoad()
            ApiConfig.getGitHubApiService().getFollowings(username)
                .enqueue(object : Callback<List<UserResponse>> {
                    override fun onResponse(
                        call: Call<List<UserResponse>>,
                        response: Response<List<UserResponse>>
                    ) {
                        if (response.isSuccessful && response.body() != null)
                            _followingList.value = response.body()
                        else setFail()
                        setEndLoad()
                    }

                    override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                        setFail()
                        setEndLoad()
                    }
                })
        }

        return _followingList
    }

}