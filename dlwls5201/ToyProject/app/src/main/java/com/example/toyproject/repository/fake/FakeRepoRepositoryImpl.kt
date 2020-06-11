package com.example.toyproject.repository.fake

import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetailModel
import com.example.toyproject.data.model.RepoModel
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.repository.RepoRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*
import java.util.concurrent.TimeUnit

class FakeRepoRepositoryImpl : RepoRepository {

    private val DELAY_TIME = 5000L

    override fun searchRepositories(
        query: String, callback: BaseResponse<RepoSearchResponse>
    ): Disposable {
        return Single.just(
            RepoSearchResponse(
                totalCount = 10,
                items = listOf(
                    RepoModel(
                        name = "name",
                        fullName = "fullName",
                        owner = RepoModel.OwnerModel(
                            login = "login",
                            avatarUrl = ""
                        ),
                        description = null,
                        language = null,
                        updatedAt = Date(),
                        stars = 1000
                    )
                )
            )
        ).delay(DELAY_TIME, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }.doOnTerminate {
                callback.onLoaded()
            }.subscribe({
                callback.onSuccess(it)
            }) {
                callback.onError(it)
            }
    }

    override fun getDetailRepository(
        user: String, repo: String, callback: BaseResponse<RepoDetailModel>
    ): Disposable {
        return Single.just(
            RepoDetailModel(
                title = "title",
                repoName = "repoName",
                ownerName = "ownerName",
                ownerUrl = "",
                followers = 1000,
                following = 1000,
                description = "description",
                language = null,
                updatedAt = Date(),
                stars = 1000
            )
        ).delay(DELAY_TIME, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                callback.onLoading()
            }.doOnTerminate {
                callback.onLoaded()
            }.subscribe({
                callback.onSuccess(it)
            }) {
                callback.onError(it)
            }
    }
}