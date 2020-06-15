package com.example.toyproject.repository

import com.example.toyproject.data.api.RepoApi
import com.example.toyproject.data.api.UserApi
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetailModel
import com.example.toyproject.data.model.RepoModel
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.data.model.UserModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import retrofit2.HttpException

class RepoRepositoryImpl(
    private val repoApi: RepoApi,
    private val userApi: UserApi
) : RepoRepository {

    override fun searchRepositories(
        query: String,
        callback: BaseResponse<RepoSearchResponse>
    ): Disposable {
        return repoApi.searchRepository(query)
            //이 이후에 수행되는 코드는 메인 쓰레드에서 실행됩니다.
            .observeOn(AndroidSchedulers.mainThread())
            //구독할 때 수행할 작업을 구현합니다.
            .doOnSubscribe {
                callback.onLoading()
            }
            //스트림이 종료될 때 수행할 작업을 구현합니다.
            //Single에서 스트림이 종료되는 시점은 성공(Success)과 에러(Error) 입니다.
            .doOnTerminate {
                callback.onLoaded()
            }
            //옵서버블을 구독합니다.
            .subscribe({
                callback.onSuccess(it)
            }) {
                // 에러 처리 작업을 구현합니다.
                if (it is HttpException) {
                    callback.onFail(it.message())
                } else {
                    callback.onError(it)
                }
            }
    }

    override fun getDetailRepository(
        user: String, repo: String, callback: BaseResponse<RepoDetailModel>
    ): Disposable {
        return Single.zip(
            repoApi.getRepository(user, repo), userApi.getUser(user),
            BiFunction<RepoModel, UserModel, RepoDetailModel> { repoModel, userModel ->
                RepoDetailModel(
                    title = repoModel.fullName,
                    repoName = repoModel.name,
                    ownerName = userModel.name,
                    ownerUrl = userModel.profileImgUrl,
                    followers = userModel.followers,
                    following = userModel.following,
                    description = repoModel.description,
                    language = repoModel.language,
                    updatedAt = repoModel.updatedAt,
                    stars = repoModel.stars
                )
            }
        ).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }
            .doOnTerminate {
                callback.onLoaded()
            }
            .subscribe({
                callback.onSuccess(it)
            }) {
                if (it is HttpException) {
                    callback.onFail(it.message())
                } else {
                    callback.onError(it)
                }
            }
    }
}