package com.example.toyproject.repository.fake

import android.os.Handler
import com.example.toyproject.data.base.BaseResponse
import com.example.toyproject.data.model.RepoDetailModel
import com.example.toyproject.data.model.RepoModel
import com.example.toyproject.data.model.RepoSearchResponse
import com.example.toyproject.repository.RepoRepository
import java.util.*

class FakeRepoRepositoryImpl : RepoRepository {

    override fun searchRepositories(query: String, callback: BaseResponse<RepoSearchResponse>) {
        callback.onLoading()

        Handler().postDelayed({
            callback.onSuccess(
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
            )
            callback.onLoaded()
        }, 1000)
    }

    override fun getDetailRepository(
        user: String, repo: String, callback: BaseResponse<RepoDetailModel>
    ) {
        callback.onLoading()

        Handler().postDelayed({
            callback.onSuccess(
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
            )
            callback.onLoaded()
        }, 1000)
    }
}